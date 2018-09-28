package de.chrissx.CLibrary.logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.file.FileWriter;
import de.chrissx.CLibrary.file.FolderChecker;

public class Logger {

	protected Path folder = null;
	protected String extention = ".log", prefix = "LOG_";
	protected StringBuilder builder = new StringBuilder();
	protected FolderChecker checker;
	protected Plugin plugin;
	
	public Logger(Path folder, String extention, String prefix, Plugin plugin) {
		this.folder = folder;
		this.extention = extention;
		this.prefix = prefix;
		this.plugin = plugin;
		checker = new FolderChecker(folder.toFile());
		checker.checkFolders();
	}
	
	public void writeLog() throws IOException {
			Path file = Paths.get(folder.toString(), prefix + new Date().toString().replaceAll(":", "-").replaceAll(" ", "_").replaceAll("CEST_", "").substring(4) + extention);
			int i = 2;
			while(file.toFile().exists()) {
				file = Paths.get(folder.toString(), prefix + new Date().toString().replaceAll(":", "-").replaceAll(" ", "_").replaceAll("CEST_", "").substring(4) + "_" + Integer.toString(i) + extention);
				i++;
			}
			Files.createFile(file);
			FileWriter.write(file.toFile(), builder.toString(), plugin);
			builder = new StringBuilder();
	}
	
	public void log(String s) {
		builder.append(s);
	}

	public void setFolder(Path folder) {
		this.folder = folder;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}
}
