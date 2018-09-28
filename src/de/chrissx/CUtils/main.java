package de.chrissx.CUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.bukkit.plugin.java.JavaPlugin;

import de.chrissx.CLibrary.file.FolderChecker;

public class main extends JavaPlugin{
	
	CommandHandler ch;
	FolderChecker fc;
	File[] paths;
	Events e;
	
	public void onEnable() {
		paths = new File[] {
				Paths.get(P.BASE_PATH).toFile(),
				P.PLAYER_HOMES.toFile(),
				P.CMDSPY_BASE.toFile(),
				P.CMDSPY.toFile(),
				P.TP_SIGN_LOCATIONS.toFile(),
				P.CMDSPY_LISTENERS.toFile()
		};
		fc = new FolderChecker(paths);
		fc.checkFolders();
		ch = new CommandHandler(this);
		e = new Events(this);
		getServer().getPluginManager().registerEvents(e, this);
	}
	
	public void onDisable() {
		try {
			e.logger.writeLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}