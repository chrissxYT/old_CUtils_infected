package de.chrissx.CLibrary.logging;

import java.nio.file.Path;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.enums.AGType;

public class AGLogger extends Logger{

	public AGLogger(Path folder, String extention, Plugin plugin) {
		super(folder, extention, "AntiGrief_", plugin);
	}
	
	public void log(Player p, String b, AGType type, Location loc, Plugin plugin) {
			String f = "";
			switch(type) {
			case PLACE: f = "Block: " + b + "\n"; break;
			case BREAK: f = "Block: " + b + "\n"; break;
			case COMMAND: f = "Command: " + b + "\n"; break;
			case CHAT: f = "Message: " + b + "\n"; break;
			case IGNITION: f = ""; break;
			}
			String towrite = p.getName() + "(" + p.getUniqueId().toString() + ") {\nType: " + type + "\nTime: " + new Date().toString().replaceAll(":", "-").replaceAll(" ", "_").replaceAll("CEST_", "").substring(4) + "\n" + f + "X: " + loc.getBlockX() + "\nY: " + loc.getBlockY() + "\nZ: " + loc.getBlockZ() + "\n}\n\n";
			super.log(towrite);
			String g = "";
			switch(type) {
			case PLACE: g = " placed " + b + " "; break;
			case BREAK: g = " broke " + b + " "; break;
			case COMMAND: g = " executed " + b + " "; break;
			case CHAT: g = " said " + b + " "; break;
			case IGNITION: g = " ignited TNT "; break;
			}
			System.out.println(p.getName() + g + "@ x" + loc.getBlockX() + " y" + loc.getBlockY() + " z" + loc.getBlockZ());
	}
}