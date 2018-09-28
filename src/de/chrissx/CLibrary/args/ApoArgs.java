package de.chrissx.CLibrary.args;

import org.bukkit.Material;

public class ApoArgs {

	public int x = 0, y = 0, z = 0;
	public Material m = Material.AIR;
	
	public ApoArgs(int x, int y, int z, Material m) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.m = m;
	}
}