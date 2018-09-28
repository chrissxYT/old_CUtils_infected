package de.chrissx.CLibrary.world;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.args.WorldGeneratorArgs;
import de.chrissx.CLibrary.chat.CC;
import de.chrissx.CLibrary.util.Util;

public class WorldGenerator {
	
	private WorldGeneratorArgs args;
	
	public WorldGenerator(WorldGeneratorArgs args) {
		this.args = args;
	}
	
	public World generateWorld() {
		if(args == null) {
			System.out.println("INTERNAL ERROR GIVING ARGS FROM WORLDUTIL TO WORLDGENERATOR");
		}
		WorldCreator wc = new WorldCreator(args.getWorldName());
		Player p = args.getPlayer();
		if(args.hasEnvironment()) {
			wc.environment(args.getEnv());
		}else {
			wc.environment(Environment.NORMAL);
		}
		
		wc.generateStructures(args.getGenerateStructures());
		
		if(args.hasSeed()) {
			wc.seed(args.getSeed());
		}
		
		World w = wc.createWorld();
		
		if(args.hasPlayer())
		p.sendMessage("Successfully created new world \"" + args.getWorldName() + "\"");
		
		
		return w;
	}
	
	public boolean addWorld(World world, Plugin plugin, Player player) {
		plugin.getServer().getWorlds().add(world);
		Util.sendMsg(player, CC.GREEN, "Added world successfully.");
		return true;
	}
}
