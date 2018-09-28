package de.chrissx.CLibrary.world;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.util.Util;

public class WorldChecker {

	private Plugin plugin;
	
	public WorldChecker(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void checkWorlds() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable(){
		@Override
		public void run() {
			File[] files = Util.SERVER_FOLDER.toFile().listFiles();
			
				for(File f : files) {
					if(f.isDirectory()) {
						File levelDat;
						levelDat = new File(f.getPath() + File.separator + "level.dat");
						if(levelDat.exists()) {
							boolean b = false;
							String name = f.getName();
							for(World w : plugin.getServer().getWorlds()) {
								if(w.getName().equalsIgnoreCase(name)) {
									b = true;
								}
							}
							if(b == false) {
								WorldCreator create;
								create = new WorldCreator(name);
								World worldtoadd;
								worldtoadd = create.createWorld();
								plugin.getServer().getWorlds().add(worldtoadd);
							}
						}
					}
				}
			}
		});
	}
}