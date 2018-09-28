package de.chrissx.CLibrary.logging;

import java.nio.file.Path;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandSpyLogger extends Logger{

	public CommandSpyLogger(Path folder, String extention, Plugin plugin) {
		super(folder, extention, "CMDSPY_", plugin);
	}
	
	public void log(Player p, String cmd) {
		Location l = p.getLocation();
		super.log(p.getName() + "(" + p.getUniqueId() + ") {\nType: COMMAND\nTime: " + new Date().toString().replaceAll(":", "-").replaceAll(" ", "_").replaceAll("CEST_", "").substring(4) + "\nCommand: " + cmd + "\nX: " + l.getBlockX() + "\nY: " + l.getBlockY() + "\nZ: " + l.getBlockZ() + "\n}\n\n");
	}
}