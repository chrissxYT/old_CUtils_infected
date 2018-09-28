package de.chrissx.CLibrary.players;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.file.FileWriter;

public class PlayerListSaver {

	private Path folder;
	private List<Player> players;
	private String extention = ".txt";
	
	public PlayerListSaver(Path folder, String extention, List<Player> players) {
		this.folder = folder;
		this.extention = extention;
		this.players = players;
	}
	
	public void savePlayers(Plugin plugin) throws IOException {
		for(Player p : players) {
			Path path = Paths.get(folder.toString(), p.getName() + extention);
			if(!path.toFile().exists()) {
				try {
					Files.createFile(path);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			FileWriter.write(path.toFile(), "true", plugin);
		}
	}
}