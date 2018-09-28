package de.chrissx.CLibrary;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import de.chrissx.CLibrary.command.CommandManager;
import de.chrissx.CLibrary.file.FolderChecker;
import de.chrissx.CLibrary.util.Util;

public class main extends JavaPlugin{
	
	private CommandManager cmdmgr = new CommandManager();
	
	public void onEnable() {
		FolderChecker check = new FolderChecker(new File(Util.CUSTOM_PLUGINS_FOLDER));
		check.checkFolders();
		for(String cmd : C.MANAGER_COMMANDS) {
			Util.registerCommand(this, cmd, cmdmgr);
		}
	}
	
	public void onDisable() {
		
	}
}
