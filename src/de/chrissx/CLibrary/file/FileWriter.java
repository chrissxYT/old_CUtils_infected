package de.chrissx.CLibrary.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.plugin.Plugin;

public class FileWriter {
	
	public static void write(File f, String s, Plugin plugin) throws IOException {
		BufferedWriter writer = new BufferedWriter(new PrintWriter(f));
		String[] strs = s.split("\n");
		writer.write(strs[0]);
		for(int i = 1; i < strs.length; i++) {
			writer.newLine();
			writer.flush();
			writer.write(strs[i]);
			writer.flush();
		}
		writer.close();
	}
}