package de.chrissx.CLibrary.stats;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.file.FileLoader;

public class StatsLoader {

	public static PlayerStats load(Path path, Plugin plugin) throws IOException {
		PlayerStats output = new PlayerStats(plugin);
		List<String> read = FileLoader.getText(path);
		output.setKills(Long.parseLong(read.get(0)));
		output.setDeaths(Long.parseLong(read.get(1)));
		output.setPlayed(Long.parseLong(read.get(2)));
		output.setWon(Long.parseLong(read.get(3)));
		return output;
	}
}