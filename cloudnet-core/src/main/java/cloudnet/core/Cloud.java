/*
 *
 * Copyright (C) 2015 Dmytro Grygorenko <dmitrygrig@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cloudnet.core;

import cloudnet.messaging.MessageBus;
import cloudnet.sim.SimClock;
import cloudnet.util.Ensure;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Cloud is the hosting environment is shared between many customers possibly
 * reducing the costs for an individual customer. Leveraging economies of scale
 * enables a dynamic use of resources, because workload peaks of some customers
 * occur during times of low workload of other customers.
 *
 * http://www.cloudcomputingpatterns.org/Public_Cloud
 *
 * @author Dmytro Grygorenko <dmitrygrig(at)gmail.com>
 */
public abstract class Cloud extends CloudEntity {

    protected List<Datacenter> datacenters;
    protected double slaPenaltyCosts = 0.0;
    protected long violationCount;
    protected long shortViolationCount;
    protected long vmMigrationCount;

    protected final MessageBus messageBus = new MessageBus();

    public Cloud(int id, SimClock clock) {
        super(id, clock);
    }

    public List<Datacenter> getDatacenters() {
        return datacenters != null ? datacenters : (datacenters = new ArrayList<>());
    }

    public void setDatacenters(List<Datacenter> datacenters) {
        this.datacenters = datacenters;
    }

    public void addDatacenter(Datacenter dc) {
        Ensure.NotNull(dc, "dc");
        dc.setCloud(this);
        getDatacenters().add(dc);
    }

    public MessageBus getMessageBus() {
        return messageBus;
    }

    public double getCosts() {
        return getEnergyCosts() + getSlaPenaltyCosts();
    }

    public double getSlaPenaltyCosts() {
        return slaPenaltyCosts;
    }

    public long getViolationCount() {
        return violationCount;
    }

    public long getShortViolationCount() {
        return shortViolationCount;
    }

    public long getVmMigrationCount() {
        return vmMigrationCount;
    }

    public double getEnergyCosts() {
        return getDatacenters().stream().mapToDouble(x -> x.getEnergyCosts()).sum();
    }

    public double getUtilizedPowerWithPue() {
        return getDatacenters().stream().mapToDouble(x -> x.getUtilizedPowerWithPue()).sum();
    }

    public void computeEnergyCostsForLastStep() {
        for (Datacenter dc : getDatacenters()) {
            dc.computeEnergyCostsForLastStep();
        }
    }

    @Override
    public void simulateExecutionWork() {

        // make changes
        performChanges();

        // simulate execution
        for (Datacenter dc : getDatacenters()) {
            dc.simulateExecution();
            for (Pm pm : dc.getPms()) {
                pm.simulateExecution();
            }
        }

        // simulate custom elements for each type of cloud
        simulateCustomEntities();

        // compute costs
        computeEnergyCostsForLastStep();
        computeOverallSlaCosts();
        computeViolationCount();
    }

    /**
     * Perform changes generated by the elasticity manager assigned to this
     * cloud.
     */
    protected abstract void performChanges();

    protected abstract void simulateCustomEntities();

    /**
     * Returns all vms existed in the cloud (both allocated or not)
     *
     * @return
     */
    public abstract Collection<Vm> getVms();

    /**
     * Returns current sla costs
     */
    protected abstract void computeOverallSlaCosts();

    /**
     * Computes number of cummulative violations count
     */
    protected abstract void computeViolationCount();

    @Override
    public String toString() {
        return String.format("%s [id=%d,dcs=%d, energyCosts=%.4f, slaPenaltyCosts=%.4f]",
                getClass().getSimpleName(),
                getId(),
                getDatacenters().size(),
                getEnergyCosts(), getSlaPenaltyCosts());
    }

}
