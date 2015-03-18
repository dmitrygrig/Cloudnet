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
package cloudnet.examples.bn;

import cloudnet.examples.locations.Oslo;
import cloudnet.examples.locations.RioDeJaneiro;
import cloudnet.examples.locations.Tokyo;
import cloudnet.examples.locations.Toronto;
import cloudnet.examples.locations.Vienna;

/**
 *
 * @author Dmytro Grygorenko <dmitrygrig(at)gmail.com>
 */
public final class CountryLevel {

    private CountryLevel() {
    }

    private static final String[] All;

    static {
        // ToDo replace with lambda from Locations
        All = new String[]{
            new Oslo().getCountry(),
            new RioDeJaneiro().getCountry(),
            new Tokyo().getCountry(),
            new Toronto().getCountry(),
            new Vienna().getCountry()
        };
    }

    public static final String[] All() {
        return All;
    }

}
