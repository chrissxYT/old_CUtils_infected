package de.chrissx.CLibrary.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.chrissx.CLibrary.enums.CommandError;
import de.chrissx.CLibrary.util.Util;

public class CommandChecker {

	public static boolean check(CommandSender s, boolean mustBePlayer, boolean mustHaveOP, int minArgs, int maxArgs, String[] args) {
		if(mustBePlayer == false && mustHaveOP == true) {
			if(!(s instanceof Player)) {
				return true;
			}
		}
		if(mustBePlayer) {
			if(!(s instanceof Player)) {
				Util.sendError(s, CommandError.NO_PLAYER);
				return false;
			}
		}
		if(mustHaveOP) {
			Player p = (Player)s;
			if(!p.isOp()) {
				Util.sendError(s, CommandError.NO_OP);
				return false;
			}
		}
		if(args.length < minArgs) {
			Util.sendError(s, CommandError.NOT_ENOUGH_ARGUMENTS);
			return false;
		}else if(args.length > maxArgs) {
			Util.sendError(s, CommandError.TOO_MANY_ARGUMENTS);
			return false;
		}
		return true;
	}
}
