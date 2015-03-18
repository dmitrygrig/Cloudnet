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
package cloudnet.examples.bn.scenarios;

import cloudnet.elasticity.DowntimeVmMigrationPolicy;
import cloudnet.workloads.prediction.SimplePredictionStrategy;

/**
 *
 * @author Dmytro Grygorenko <dmitrygrig@gmail.com>
 */
public class Evaluations3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ScenarioEvaluator evaluator = new ScenarioEvaluator();
        evaluator.evaluate(
                Scenario.createScenario1(ScenarioEvaluator.EM_FFO, new DowntimeVmMigrationPolicy(), new SimplePredictionStrategy())
//                Scenario.createScenario2(ScenarioEvaluator.EM_BN, new DowntimeVmMigrationPolicy(), new SimplePredictionStrategy()),
//                Scenario.createScenario3(ScenarioEvaluator.EM_BN, new DowntimeVmMigrationPolicy(), new SimplePredictionStrategy()),
//                Scenario.createScenario4(ScenarioEvaluator.EM_BN, new DowntimeVmMigrationPolicy(), new SimplePredictionStrategy()),
//                Scenario.createScenario5(ScenarioEvaluator.EM_BN, new DowntimeVmMigrationPolicy(), new SimplePredictionStrategy()),
//                Scenario.createScenario6(ScenarioEvaluator.EM_BN, new DowntimeVmMigrationPolicy(), new SimplePredictionStrategy())
        );

        // exit in order to stop R-Environment if it is used.
        System.exit(0);
    }

}
