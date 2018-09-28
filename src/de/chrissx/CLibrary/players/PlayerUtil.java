package de.chrissx.CLibrary.players;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.chat.CC;
import de.chrissx.CLibrary.enums.CommandError;
import de.chrissx.CLibrary.util.Util;

public class PlayerUtil {

	public static int tpByWorldManager(String[] args, Player p, Plugin plugin) {
		return Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

			@Override
			public void run() {
				if(args.length == 2) {
					Util.sendError(p, CommandError.WRONG_USAGE);
					return;
				}
				
				if(args.length == 3) {
					int x, y, z;
					try {
						x = Integer.parseInt(args[0]);
						y = Integer.parseInt(args[1]);
						z = Integer.parseInt(args[2]);
						Location newLoc = new Location(p.getWorld(), x, y, z);
						Bukkit.getScheduler().runTask(plugin, new Runnable() {
							@Override
							public void run() {
								p.teleport(newLoc);
							}
						});
					}catch(Exception e) {
						Util.sendMsg(p, CC.RED, "Teleporting error: " + e.getMessage());
					}
					return;
				}
				if(args.length == 1) {
					World worldtotpto = plugin.getServer().getWorld(args[0]);
					if(worldtotpto == null) {
						p.sendMessage("Error: world cannot be found");
						return;
					}
					Location newLoc = worldtotpto.getSpawnLocation();
					Bukkit.getScheduler().runTask(plugin, new Runnable() {
						@Override
						public void run() {
							p.teleport(newLoc);
						}
					});
					return;
				}
				if(args.length == 4) {
					int x, y, z;
					World w = plugin.getServer().getWorld(args[0]);
					if(!(w != null)) {
						Util.sendMsg(p, CC.RED, "Error: world cannot be found");
						return;
					}
					
					try {
						x = Integer.parseInt(args[1]);
						y = Integer.parseInt(args[2]);
						z = Integer.parseInt(args[3]);
					}catch(Exception e) {
						Util.sendMsg(p, CC.RED, "Error: " + e.getMessage());
						return;
					}
					Location newLoc = new Location(w, x, y, z);
					Bukkit.getScheduler().runTask(plugin, new Runnable() {
						@Override
						public void run() {
							p.teleport(newLoc);
						}
					});
					return;
				}
			}
		}).getTaskId();
	}
	
	public static void setMaxHealth(Player p, double health) {
		boolean b = false;
		if(p.getHealth() == p.getMaxHealth()) {
			b = true;
		}
		setMaxHealth(p, health, b);
	}
	
	public static void setMaxHealth(Player p, double hp, boolean b) {
		p.setMaxHealth(hp);
		p.setHealthScaled(false);
		if(b) {
			p.setHealth(hp);
		}
	}
}
