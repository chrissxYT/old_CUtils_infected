package de.chrissx.CLibrary.game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import de.chrissx.CLibrary.file.FileLoader;
import de.chrissx.CLibrary.file.FileUtil;

public class GameLoader {

	public static List<List<String>> getGames(Path path, String ext) throws IOException{
		List<List<String>> strings = new ArrayList<List<String>>();
		for(File f : FileUtil.list(path.toFile(), ext)) {
			strings.add(FileLoader.getText(f.toPath()));
		}
		return strings;
	}
}
