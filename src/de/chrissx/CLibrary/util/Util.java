package de.chrissx.CLibrary.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.chrissx.CLibrary.chat.CC;
import de.chrissx.CLibrary.enums.CommandError;
import net.md_5.bungee.api.ChatColor;

public class Util {
	
	public static void registerCommand(Plugin pl, String cmd, CommandExecutor cmdexec) {
		try{
			((JavaPlugin) pl).getCommand(cmd).setExecutor(cmdexec);
		}catch(NullPointerException ex) {
			System.out.println("Error registering Command \"" + cmd + "\"");
			ex.printStackTrace();
		}
	}
	
	public static void sendMsg(CommandSender s, ChatColor color, String msg) {
		s.sendMessage(color + msg);
	}
	
	public static void sendError(CommandSender s, CommandError e) {
		String msg = "Well, something doesn't work, send this message to chrissx! (Debug: ERROR_MSG_NOT_CHANGED_SENDERROR_2)";
		switch(e) {
		case NO_OP: msg = "You must be OP to do this."; break;
		case NOT_ENOUGH_ARGUMENTS: msg = "Not enough arguments."; break;
		case TOO_MANY_ARGUMENTS: msg = "Too many arguments."; break;
		case NO_PLAYER: msg = "You must be a player to execute this command."; break;
		case WRONG_USAGE: msg = "Wrong usage!"; break;
		default: msg = "WTF, CHRISSX ADDED NEW CommandError-Enum-Values and forgot to edit his sendError-method, send this chat-message to him (Debug: " + e + ")"; break;
		}
		sendMsg(s, CC.RED, msg);
	}
	
	public static void sendError(CommandSender s, CommandError e, String ext_msg) {
		String msg = "Well, something doesn't work, send this message to chrissx! (Debug: ERROR_MSG_NOT_CHANGED_SENDERROR_3)";
		switch(e) {
		case NO_OP: msg = "You must be OP to do this"; break;
		case NOT_ENOUGH_ARGUMENTS: msg = "Not enough arguments"; break;
		case TOO_MANY_ARGUMENTS: msg = "Too many arguments"; break;
		case NO_PLAYER: msg = "You must be a player to execute this command"; break;
		case WRONG_USAGE: msg = "Wrong usage"; break;
		default: msg = "WTF, CHRISSX ADDED NEW CommandError-Enum-Values and forgot to edit his sendError-method, send this chat-message to him (Debug: " + e + ")"; break;
		}
		sendMsg(s, CC.RED, msg + ": " + ext_msg);
	}
	
	public static String CUSTOM_PLUGINS_FOLDER = Paths.get(new File("").getAbsoluteFile().getAbsolutePath().toString(), "chrissx-plugins").toString();
	
	public static final Path SERVER_FOLDER = Paths.get(new File("").getAbsoluteFile().getAbsolutePath().toString());
	
	public static int getPing(Player p) {
		return ((CraftPlayer) p).getHandle().ping;
	}
}
