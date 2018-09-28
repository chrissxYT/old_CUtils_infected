package de.chrissx.CLibrary.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileLoader {

	public static String getLine(Path path) throws IOException {
		return getText(path).get(0);
	}
	
	public static List<String> getText(Path path) throws IOException {
		return Files.readAllLines(path);
	}
}