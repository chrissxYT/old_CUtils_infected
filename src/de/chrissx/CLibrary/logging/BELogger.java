package de.chrissx.CLibrary.logging;

import java.nio.file.Path;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.enums.BEType;

public class BELogger extends Logger{

	public BELogger(Path folder, String extention, Plugin plugin) {
		super(folder, extention, "BlockEdit_", plugin);
	}
	
	public void log(BEType type, Material block, Player player, Location loc) {
		String towrite = player.getName() + "(" + player.getUniqueId().toString() + ") {\nType: " + type + "\nTime: " + new Date().toString().replaceAll(":", "-").replaceAll(" ", "_").replaceAll("CEST_", "").substring(4) + "\nBlock: " + block + "\nX: " + loc.getBlockX() + "\nY: " + loc.getBlockY() + "\nZ: " + loc.getBlockZ() + "\n}\n\n";
		super.log(towrite);
	}
}