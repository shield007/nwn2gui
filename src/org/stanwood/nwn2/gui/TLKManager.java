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
