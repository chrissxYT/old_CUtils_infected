package de.chrissx.CLibrary.args;

import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

public class WorldGeneratorArgs {

	private String worldName = null;
	private Environment env = null;
	private long seed = 0;
	private boolean generateStructures = true;
	private Player player = null;
	
	public WorldGeneratorArgs() {
		
	}
	
	@SuppressWarnings("deprecation")
	public WorldGeneratorArgs(String worldName, int env, long seed, boolean genStruc, Player player) {
		this.worldName = worldName;
		this.env = Environment.getEnvironment(env);
		this.seed = seed;
		this.generateStructures = genStruc;
		this.player = player;
	}
	
	public boolean hasEnvironment() {
		if(env == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean hasSeed() {
		if(seed == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean hasPlayer() {
		if(player == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Environment getEnv(String id) {
		int intId = Integer.parseInt(id);
		return Environment.getEnvironment(intId);
	}
	
	public String getWorldName() {
		return worldName;
	}
	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	public Environment getEnv() {
		return env;
	}
	public void setEnv(Environment env) {
		this.env = env;
	}
	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public boolean getGenerateStructures() {
		return generateStructures;
	}

	public void setGenerateStructures(boolean generateStructures) {
		this.generateStructures = generateStructures;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
