package de.chrissx.CLibrary.world;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.chrissx.CLibrary.args.ApoArgs;
import de.chrissx.CLibrary.args.ApoArgsPlusInt;
import de.chrissx.CLibrary.chat.CC;
import de.chrissx.CLibrary.util.Util;

public class WorldUtil {

	public static boolean deleteWorld(File path) {
		if(path.exists()) {
			File files[] = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteWorld(files[i]);
				}else {
					files[i].delete();
				}
			}
		}
		return(path.delete());
	}
	
	public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2)
    {
        List<Block> blocks = new ArrayList<Block>();
 
        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
 
        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
 
        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
 
        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    blocks.add(getBlock(new Location(loc1.getWorld(), x, y, z)));
                }
            }
        }
       
        return blocks;
    }
	
	public static Block getBlock(Location l) {
		return l.getWorld().getBlockAt(l);
	}
	
	public static List<Block> getWalls(Location center, int radius) {
		List<Block> blocks = new ArrayList<Block>();
		
		int x = center.getBlockX(), y = center.getBlockY(), z = center.getBlockZ();
		World w = center.getWorld();
		
		for(Block b : blocksFromTwoPoints(new Location(w, x - radius, y + radius, z - radius), new Location(w, x + radius, y, z - radius))) {
			blocks.add(b);
		}
		
		for(Block b : blocksFromTwoPoints(new Location(w, x - radius, y + radius, z - radius), new Location(w, x - radius, y, z + radius))) {
			blocks.add(b);
		}
		
		for(Block b : blocksFromTwoPoints(new Location(w, x + radius, y + radius, z + radius), new Location(w, x + radius, y, z - radius))) {
			blocks.add(b);
		}
		
		for(Block b : blocksFromTwoPoints(new Location(w, x + radius, y + radius, z + radius), new Location(w, x - radius, y, z + radius))) {
			blocks.add(b);
		}
		
		return blocks;
	}
	
	public static Location getLocation(String x, String y, String z, String world) {
		World w;
		int x_, y_, z_;
		w = Bukkit.getWorld(world);
		x_ = Integer.parseInt(x);
		y_ = Integer.parseInt(y);
		z_ = Integer.parseInt(z);
		return new Location(w, x_, y_, z_);
	}
	
	public static boolean isDay(World world) {
	    long time = world.getTime();

	    return time < 12001 || time > 23999;
	}
	
	public static boolean isUnderAir(Player p) {
		int x = p.getLocation().getBlockX(), z = p.getLocation().getBlockZ();
		World w = p.getLocation().getWorld();
		boolean ua = true;
		
		for(int y = p.getLocation().getBlockY(); y <= 256; y++) {
			if(getBlock(new Location(w, x, y, z)).getType() != Material.AIR) {
				ua = false;
			}
		}
		
		return ua;
	}
	
	public static List<Block> getBlocks(Material m){
		List<Block> out = new ArrayList<Block>();
		long before = System.currentTimeMillis();
		for(World w : Bukkit.getWorlds()) {
			for(Chunk c : w.getLoadedChunks()) {
				for(int x = c.getX(); x <= c.getX() + 16; x++) {
					for(int y = 1; y <= 256; y++) {
						for(int z = c.getZ(); z <= c.getZ() + 16; z++) {
							if(c.getBlock(x, y, z).getType().equals(m)) {
								out.add(c.getBlock(x, y, z));
							}
						}
					}
				}
			}
		}
		long after = System.currentTimeMillis();
		float time = (after - before) / 1000;
		Util.sendMsg(Bukkit.getConsoleSender(), CC.GOLD, "Looping through blocks took " + Float.toString(time) + " seconds.");
		return out;
	}
	
	public static Runnable changeBlock(long b, Material m, Plugin p) {
		return new Runnable() {
			@Override
			public void run() {
				if(m.equals(Material.LAVA) || m.equals(Material.STATIONARY_LAVA) || m.equals(Material.WATER) || m.equals(Material.STATIONARY_WATER) || m.equals(Material.AIR) || m.equals(Material.FIRE)) {
					return;
				}
				long i = 0;
				for(World w : Bukkit.getWorlds()) {
					for(Chunk c : w.getLoadedChunks()) {
						for(int x = c.getX(); x <= c.getX() + 16; x++) {
							for(int y = 1; y <= 256; y++) {
								for(int z = c.getZ(); z <= c.getZ() + 16; z++) {
									i++;
									if(i == b) {
										int x_ = x;
										int y_ = y;
										int z_ = z;
										if(c.getBlock(x, y, z).getType().equals(Material.AIR) || c.getBlock(x, y, z).getType().equals(Material.WATER) || c.getBlock(x, y, z).getType().equals(Material.STATIONARY_WATER) || c.getBlock(x, y, z).getType().equals(Material.LAVA) || c.getBlock(x, y, z).getType().equals(Material.STATIONARY_LAVA)) {
											return;
										}
										Bukkit.getScheduler().runTask(p, new Runnable() {
											@Override
											public void run() {
												c.getBlock(x_, y_, z_).setType(m);
											}
										});
										System.out.println("Destroyed block @ x" + x + " y" + y + " z" + z);
										return;
									}
								}
							}
						}
					}
				}
			}
		};
	}
	
	public static long blocks() {
		long l = 0;
		for(World w : Bukkit.getWorlds()) {
			for(Chunk c : w.getLoadedChunks()) {
				for(int x = c.getX(); x <= c.getX() + 16; x++) {
					for(int y = 1; y <= 256; y++) {
						for(int z = c.getZ(); z <= c.getZ() + 16; z++) {
							l++;
						}
					}
				}
			}
		}
		return l;
	}
	
	public static ApoArgsPlusInt changeBlock(World w, int x, int y, int z, Material m, Plugin p) {
		int i = Bukkit.getScheduler().runTask(p, new Runnable() {
			@Override
			public void run() {
				w.getBlockAt(x, y, z).setType(m);
				System.out.println("Destroyed block @ x" + x + " y" + y + " z" + z);
			}
		}).getTaskId();
		return new ApoArgsPlusInt(new ApoArgs(x, y, z, m), i);
	}
	
	public static int[] getStartAndEndCoords() {
		int startX = -1, startY = 1, startZ = -1, endX = 1, endY = 100, endZ = 1;
		
		for(World w : Bukkit.getWorlds()) {
			for(Chunk c : w.getLoadedChunks()) {
				for(int x = c.getX(); x <= c.getX() + 16; x++) {
					int oldStartX = startX;
					int oldEndX = endX;
					startX = Math.min(x, oldStartX);
					endX = Math.max(x, oldEndX);
					for(int y = 1; y <= 256; y++) {
						for(int z = c.getZ(); z <= c.getZ() + 16; z++) {
							int oldStartZ = startZ;
							int oldEndZ = endZ;
							endZ = Math.max(z, oldEndZ);
							startZ = Math.min(z, oldStartZ);
						}
					}
				}
			}
		}
		return new int[] {
				startX,
				startY,
				startZ,
				endX,
				endY,
				endZ
		};
	}
	
	public static List<Chunk> getChunks(Location loc1, Location loc2) {
		List<Chunk> out = new ArrayList<Chunk>();
		World w = loc1.getWorld();
		int chunkcount = 0;
		for(int x = loc1.getBlockX(); x <= loc2.getBlockX(); x++) {
			for(int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
				Chunk c = w.getChunkAt(x, z);
				if(!(c == out.get(chunkcount))) {
					out.add(c);
					chunkcount++;
				}
			}
		}
		return out;
	}
	
	public static List<Location> getLocs(Location loc1, Location loc2) {
		List<Location> out = new ArrayList<Location>();
		if(loc1.getBlockX() < loc2.getBlockX()) {
			for(int x = loc1.getBlockX(); x <= loc2.getBlockX(); x++) {
				if(loc1.getBlockZ() < loc2.getBlockZ()) {
					for(int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
						out.add(new Location(loc1.getWorld(), x, loc1.getBlockY(), z));
					}
				}else {
					for(int z = loc1.getBlockZ(); z >= loc2.getBlockZ(); z++) {
						out.add(new Location(loc1.getWorld(), x, loc1.getBlockY(), z));
					}
				}
			}
		}else {
			for(int x = loc1.getBlockX(); x >= loc2.getBlockX(); x++) {
				if(loc1.getBlockZ() < loc2.getBlockZ()) {
					for(int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
						out.add(new Location(loc1.getWorld(), x, loc1.getBlockY(), z));
					}
				}else {
					for(int z = loc1.getBlockZ(); z >= loc2.getBlockZ(); z++) {
						out.add(new Location(loc1.getWorld(), x, loc1.getBlockY(), z));
					}
				}
			}
		}
		return out;
	}
}