package de.chrissx.CLibrary.stats;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.file.FileWriter;

public class StatsWriter {

	public static void write(PlayerStats stats, Path file, Plugin plugin) throws IOException {
		String towrite = stats.getKills() + "\n" + stats.getDeaths() + "\n" + stats.getPlayed() + "\n" + stats.getWon();
		File f = file.toFile();
		if(!f.exists()) {
			try {
				Files.createFile(f.toPath());
			} catch (IOException e) {e.printStackTrace();}
		}
		FileWriter.write(f, towrite, plugin);
	}
}