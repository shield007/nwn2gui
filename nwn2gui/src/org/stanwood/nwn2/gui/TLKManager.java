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
package org.stanwood.nwn2.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stanwood.nwn2.tlk.TLKEntry;
import org.stanwood.nwn2.tlk.TLKParser;

public class TLKManager {

	private final static Log log = LogFactory.getLog(TLKManager.class);
	
	private static TLKManager instance = null;
	private List<TLKParser> parsers = new ArrayList<TLKParser>(); 	
	
	public static TLKManager getInstance() {
		if (instance==null) {
			instance = new TLKManager();
		}
		return instance;
	}
	
	public void addTLKFile(File file) throws IOException {
		TLKParser parser = new TLKParser();
		parser.open(file);		
		parsers.add(parser);
	}	
	
	public String getText(int id) {		
		String text = "Bad strref";
		for (TLKParser parser : parsers) {
			try {
				TLKEntry entry = parser.readEntry(id);
				if (entry!=null) {
					if (entry.getText()!=null) {
						return entry.getText();
					}
				}
			}
			catch (IOException e) {
				log.error("Unable to read tlk entry: "+e.getMessage(),e);
			}
		}
		return text;		
	}
	
	public void close() throws IOException {
		for (TLKParser parser : parsers) {
			parser.close();
		}
	}
}
