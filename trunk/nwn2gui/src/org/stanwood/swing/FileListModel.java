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
	
	public void setFiles(List<File> files) {
		if (this.files.size()>0) {
			fireIntervalRemoved(this, 0,this.files.size()-1);
		}
		this.files = files;
		int endIndex = files.size()-1; 
		if (endIndex >=0) {			
			fireIntervalAdded(this, 0, endIndex);
		}
	}
}
