package de.chrissx.CLibrary.logging;

import java.nio.file.Path;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.args.ApoArgs;
import de.chrissx.CLibrary.args.ApoArgsPlusInt;

public class ApoLogger extends Logger{
	
	public ApoLogger(Path folder, String extention, Plugin plugin) {
		super(folder, extention, "APOCALYPTIC_", plugin);
	}
	
	public void log(int x, int y, int z, Material m) {
		super.log("X: " + x + "\nY: " + y + "\nZ: " + z + "\nMaterial: " + m + "\n\n");
	}
	
	public void log(ApoArgs args) {
		if(args == null) {
			return;
		}
		super.log("X: " + args.x + "\nY: " + args.y + "\nZ: " + args.z + "\nMaterial: " + args.m + "\n\n");
	}
	
	public void log(ApoArgsPlusInt args) {
		if(args.apoArgs == null) {
			return;
		}
		log(args.apoArgs);
	}
}