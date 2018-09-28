package de.chrissx.CUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.chrissx.CLibrary.chat.CC;
import de.chrissx.CLibrary.command.CommandChecker;
import de.chrissx.CLibrary.enums.CommandError;
import de.chrissx.CLibrary.file.FileLoader;
import de.chrissx.CLibrary.file.FileWriter;
import de.chrissx.CLibrary.util.Util;
import de.chrissx.CLibrary.world.WorldUtil;
import net.md_5.bungee.api.ChatColor;

public class CommandHandler implements CommandExecutor{

	String[] cmds;
	Plugin plugin;
	
	public CommandHandler(Plugin plugin) {
		this.plugin = plugin;
		cmds = new String[] {
			"i", "v", "nick", "ip", "sethome", "home", "color", "commandspy", "broadcast", "bc"
		};
		for(String cmd : cmds) {
			Util.registerCommand(plugin, cmd, this);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String name_useless, String[] args) {
		
		
		if(cmd.getName().equalsIgnoreCase("i")) {
			
			
			if(!CommandChecker.check(s, true, true, 1, 2, args)) {
				return true;
			}
			Player p = (Player)s;
			Material i;
			int number;
			try {
				i = Material.getMaterial(Integer.parseInt(args[0]));
			}catch(Exception e) {
				i = Material.getMaterial(args[0].toUpperCase());
			}
			if(args.length > 1) {
				try {
					number = Integer.parseInt(args[1]);
				}catch(Exception e) {
					Util.sendMsg(p, CC.RED, "Error parsing number of items: " + e.toString() + ", continuing with 64");
					number = 64;
				}
			}else {
				number = 64;
			}
			if(i == null) {
				p.sendMessage(CC.RED + "Not an item-ID.");
				return true;
			}
			p.getInventory().addItem(new ItemStack(i, number));
			p.sendMessage("Gave you " + number + " of " + i.name());
			return true;
			
			
	}else if(cmd.getName().equalsIgnoreCase("v")) {
		
		
		if(!CommandChecker.check(s, true, true, 0, 0, args)) {
			return true;
		}
		Player p = (Player)s;
		if(p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			p.sendMessage(ChatColor.GRAY + "You are no longer invisible.");
		}else {
			PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 0, true);
			p.addPotionEffect(invis);
			p.sendMessage(ChatColor.GRAY + "You are now invisible.");
		}
		return true;
		
		
	}else if(cmd.getName().equalsIgnoreCase("nick")) {
		
		
		if(!CommandChecker.check(s, true, false, 1, 2, args)) {
			return true;
		}
		
		Player p = (Player)s;
		if(args.length == 1) {
			p.setDisplayName(args[0].replaceAll("&", "§"));
			p.setPlayerListName(args[0].replaceAll("&", "§"));
			p.setCustomName(args[0].replaceAll("&", "§"));
			p.sendMessage("Nickname changed to \"" + args[0] + "\".");
			return true;
		}else {
			Player target = plugin.getServer().getPlayer(args[0]);
			if(p.isOp()) {
				if(target == null) {
					p.sendMessage(CC.RED + "Player \"" + args[0] + "\" cannot be found.");
					return true;
				}else {
					target.setDisplayName(args[1].replaceAll("&", "§"));
					target.setCustomName(args[0].replaceAll("&", "§"));
					target.setPlayerListName(args[0].replaceAll("&", "§"));
					p.sendMessage("Nickname of player \"" + target.getName() + "\" has been changed to \"" + args[1] + "\".");
					return true;
				}
			}else {
				Util.sendError(s, CommandError.NO_OP);
				return true;
			}
		}
		
		
	}else if(cmd.getName().equalsIgnoreCase("ip")){
		
		
		if(!CommandChecker.check(s, false, true, 1, 1, args)) {
			return true;
		}else {
			Player target = plugin.getServer().getPlayer(args[0]);
			if(target == null) {
				s.sendMessage(CC.RED + "Player \"" + args[0] + "\" could not be found.");
				return true;
			}
			s.sendMessage(target.getDisplayName() + "'s IP-Address is " + target.getAddress().getAddress().toString().substring(1));
			return true;
		}
		
		
	}else if(cmd.getName().equalsIgnoreCase("sethome")) {
		
		
		if(!CommandChecker.check(s, true, false, 0, 4, args)) {
			return true;
		}
		
		Player p = (Player)s;
		File homeFile;
		homeFile = new File(P.PLAYER_HOMES.toString(), p.getName() + P.PLAYER_HOMES_EXTENTION);
		if(!homeFile.exists()) {
			try {
				Files.createFile(homeFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
				p.sendMessage(CC.RED + "Error creating your home-file.");
				return true;
			}
		}
		Location homeLoc;
		if(args.length == 4) {
			try {
				homeLoc = new Location(plugin.getServer().getWorld(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			}catch(Exception e) {
				e.printStackTrace();
				p.sendMessage(CC.RED + "Error parsing command /sethome OR /sethome [world] [x] [y] [z]");
				return true;
			}
		}else {
			homeLoc = p.getLocation();
		}
		String toWrite = Integer.toString(homeLoc.getBlockX()) + "\n" + Integer.toString(homeLoc.getBlockY()) + "\n" + Integer.toString(homeLoc.getBlockZ()) + "\n" + homeLoc.getWorld().getName();
		try {
			FileWriter.write(homeFile, toWrite, plugin);
			p.sendMessage(CC.GREEN + "Saved home.");
		} catch (Exception e) {
			e.printStackTrace();
			Util.sendMsg(p, CC.RED, "Error saving home: " + e.getMessage());
			return true;
		}
		
		
	}else if(cmd.getName().equalsIgnoreCase("home")) {
		
		
		if(!CommandChecker.check(s, true, false, 0, 1, args)) {
			return true;
		}
		Player p = (Player)s;
		File homeFile;
		homeFile = new File(P.PLAYER_HOMES.toString(), p.getName() + P.PLAYER_HOMES_EXTENTION);
		if(!homeFile.exists()) {
			p.sendMessage(CC.RED + "You don't have a home to go to.");
			return true;
		}
		List<String> homeList;
		try {
			homeList = FileLoader.getText(homeFile.toPath());
		} catch (Exception e) {
			e.printStackTrace();
			p.sendMessage(CC.RED + "Internal error(see console for information)");
			return true;
		}
		int x = Integer.parseInt(homeList.get(0)), y = Integer.parseInt(homeList.get(1)), z = Integer.parseInt(homeList.get(2));
		World w = plugin.getServer().getWorld(homeList.get(3));
		Location tploc = new Location(w, x, y, z);
		if(tploc.getBlock().getType().isSolid() && args.length == 0) {
			p.sendMessage("The location is in the ground, if you still want to be teleported, you must enter /home [any letter you want]");
			return true;
		}
		p.teleport(tploc);
		p.sendMessage(CC.GREEN + "You have been teleported.");
		return true;
		
		
	}else if(cmd.getName().equalsIgnoreCase("color")) {
		
		
		if(!CommandChecker.check(s, true, true, 2, 2, args)) {
			return true;
		}
		Player p = (Player)s;
		DyeColor dye;
		if(args[0].length() == 1 || args[0].length() == 2) {
			try {
				dye = DyeColor.getByData((byte)Integer.parseInt(args[0]));
			}catch(Exception e) {
				p.sendMessage(CC.RED + "Cannot parse dye: " + e.toString());
				return true;
			}
		}else {
			dye = DyeColor.valueOf(args[0]);
		}
		if(dye == null) {
			p.sendMessage(CC.RED + "Cannot get color.");
			return true;
		}
		int radius = 69;
		try {
			radius = Integer.parseInt(args[1]);
		}catch(Exception e) {
			p.sendMessage(CC.RED + "Error parsing radius: " + e.toString());
			return true;
		}
		for(Entity e : p.getNearbyEntities(radius, radius, radius)) {
			if(e instanceof Sheep) {
				((Sheep) e).setColor(dye);
			}
		}
		
		Location p_loc = p.getLocation(), sL = new Location(p_loc.getWorld(), p_loc.getBlockX() - radius, 255, p_loc.getBlockZ() - radius), eL = new Location(p_loc.getWorld(), p_loc.getBlockX() + radius, 1, p_loc.getBlockZ() + radius);
		
        for(Block b : WorldUtil.blocksFromTwoPoints(sL, eL)) {
        	if(b.getType().equals(Material.STAINED_CLAY) || b.getType().equals(Material.HARD_CLAY) || b.getType().equals(Material.CLAY) || b.getType().equals(Material.WOOL)) {
        		b.setData(dye.getData());
        	}
        }
		
		Util.sendMsg(s, CC.GREEN, "Colored that sheeps, wools and clays");
		return true;
		
		
	}else if(cmd.getName().equals("commandspy")) {
		
		
		if(!CommandChecker.check(s, false, true, 2, 2, args)) {
			return true;
		}
		Player p = plugin.getServer().getPlayer(args[0]);
		if(!(p != null)) {
			p = (Player) plugin.getServer().getOfflinePlayer(args[0]);
		}
		if(!(p != null)) {
			s.sendMessage("Player cannot be found.");
			return true;
		}
		boolean onoff = false;
		onoff = Boolean.parseBoolean(args[1]);
		File f = new File(P.CMDSPY.toString(), p.getName() + ".cmdspyconfig");
		if(onoff) {
			if(!f.exists()) {
				try {
					Files.createFile(f.toPath());
				} catch (IOException e) {
					e.printStackTrace();
					return true;
				}
			}
			try {
				FileWriter.write(f, "true", plugin);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			if(f.exists()) {
				try {
					Files.delete(f.toPath());
				} catch (IOException e) {
					e.printStackTrace();
					return true;
				}
			}
		}
		Util.sendMsg(s, CC.GREEN, "Set CommandSpy for " + p.getName() + " to " + Boolean.toString(onoff));
		return true;
		
		
	}else if(cmd.getName().equalsIgnoreCase("broadcast")) {
		
		
		if(!CommandChecker.check(s, true, true, 1, Integer.MAX_VALUE, args)) {
			return true;
		}
		StringBuilder builder = new StringBuilder();
		for(String str : args) {
			builder.append(str + " ");
		}
		plugin.getServer().broadcastMessage(builder.toString());
		return true;
		
		
	}else if(cmd.getName().equalsIgnoreCase("bc")) {
		
		
		if(!CommandChecker.check(s, true, true, 1, Integer.MAX_VALUE, args)) {
			return true;
		}
		String msg = "";
		for(String str : args) {
			msg = msg + " " + str;
		}
		plugin.getServer().broadcastMessage(msg);
		return true;
		
		
	}
		return false;
	}
}