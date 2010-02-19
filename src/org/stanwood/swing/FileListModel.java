package org.stanwood.swing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

public class FileListModel extends AbstractListModel {

	private List<File>files = new ArrayList<File>();
	
	@Override
	public Object getElementAt(int index) {
		return files.get(index);
	}

	@Override
	public int getSize() {
		return files.size();
	}
	
	public void addFile(File file) {
		int index = files.size();
		files.add(file);
		fireIntervalAdded(this, index, index);
	}

	public List<File>getFiles() {
		return files;
	}
	
	public void removeFile(int index) {
		files.remove(index);
		fireIntervalRemoved(this, index, index);
	}
}
