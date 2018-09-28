package de.chrissx.CLibrary.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.chrissx.CLibrary.C;
import de.chrissx.CLibrary.chat.CC;
import de.chrissx.CLibrary.util.Util;

public class CommandManager implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command c, String cmd_label_useless_af, String[] args) {
		if(c.getName().equalsIgnoreCase(C.MANAGER_STATS_COMMAND)) {
			if(!(CommandChecker.check(s, true, false, 1, 1, args))) {
				return true;
			}
			boolean isElement = false;
			for(String mode : C.MANAGER_STATS_MODES) {
				if(args[0] == mode) {
					isElement = true;
				}
			}
			if(!isElement) {
				Util.sendMsg(s, CC.RED, "Are you sure, you entered a gamemode?");
				return true;
			}
			if(args[0] == C.MANAGER_STATS_RAGEMODE) {
				Bukkit.dispatchCommand(s, C.MANAGER_STATS_RAGEMODE_LISTENER);
			}
			return true;
		}
		return false;
	}

}