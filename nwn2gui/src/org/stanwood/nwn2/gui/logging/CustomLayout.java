/*
 *  Copyright (C) 2008  John-Paul.Stanford <dev@stanwood.org.uk>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.stanwood.nwn2.gui.logging;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * A custom logging layout, used to layout the logging message simalar to System.out.println()
 */
public class CustomLayout extends Layout {

    StringBuffer sbuf = new StringBuffer(128);

    /**
     * Format the message so that it is plain text only with no logging info around it.
     * @param event The logging event
     * @return The formatted message
     */
    @Override
    public String format(LoggingEvent event) {
        sbuf.setLength(0);
        sbuf.append(event.getMessage());
        sbuf.append(LINE_SEP);
        
        return sbuf.toString();
    }
    
    /**
     * Always returns true to tell the logger to logger to ignore throwables
     * @return Always returns true
     */
    @Override
    public boolean ignoresThrowable() {
        return true;
    }

    /**
     * This does nothing
     */
    @Override
    public void activateOptions() {

    }
}
