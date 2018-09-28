package de.chrissx.CLibrary.file;

import java.io.File;
import java.nio.file.Files;

public class FolderChecker {

	private File[] folders;
	
	public FolderChecker(File[] folders) {
		this.folders = folders;
	}
	
	public FolderChecker(File folder) {
		this.folders = new File[] {
			folder	
		};
	}
	
	public void checkFolders() {
		for(File f : folders) {
			if(!f.exists()) {
				try {
					Files.createDirectory(f.toPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setFolders(File[] folders) {
		this.folders = folders;
	}
	
	public File getFolderAt(int i) {
		return folders[i];
	}
}
