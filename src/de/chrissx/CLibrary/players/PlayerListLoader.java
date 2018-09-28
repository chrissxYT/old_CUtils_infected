package de.chrissx.CLibrary.players;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.file.FileUtil;
import de.chrissx.CLibrary.file.FolderChecker;

public class PlayerListLoader {

	private Path folder = null;
	private List<Player> players = new ArrayList<Player>();
	private Server server = null;
	private FolderChecker fc;
	private String extention;
	
	public PlayerListLoader(Path folder, Server s, String extention, Plugin plugin) {
		this.folder = folder;
		this.server = s;
		this.extention = extention;
		fc = new FolderChecker(folder.toFile());
		fc.checkFolders();
	}
	
	private void loadPlayers(Plugin plugin) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				for(File f : FileUtil.list(folder.toFile(), extention)) {
					try {
						Player pl = server.getPlayer(f.getName().replace(extention, ""));
						players.add(pl);
						break;
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			}
		});
	}

	public List<Player> getPlayers(Plugin plugin) {
		loadPlayers(plugin);
		return players;
	}
}
