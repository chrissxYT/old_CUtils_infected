package de.chrissx.CLibrary.file;

import java.io.File;
import java.nio.file.Files;

import org.bukkit.plugin.Plugin;

public class FileChecker {

	private File[] files;
	private Plugin plugin;

	public FileChecker(File[] files, Plugin plugin) {
		this.files = files;
		this.plugin = plugin;
	}
	
	public FileChecker(File file, Plugin plugin) {
		this.files = new File[] {
				file
		};
		this.plugin = plugin;
	}
	
	public void checkFiles(String default_value) {
		for(File f : files) {
			if(!f.exists()) {
				try {
					Files.createFile(f.toPath());
					FileWriter.write(f, default_value, plugin);
				} catch (Exception e) {
					System.out.println("WHAT THE FUCK, A " + e.toString() + " OCCURED IN THE FILECHECKER OF CLIBRARY IN LINE 32 OR 33??");
				}
			}
		}
	}

	public void setFiles(File[] files) {
		this.files = files;
	}
	
	public File getFileAt(int i) {
		return files[i];
	}
}
