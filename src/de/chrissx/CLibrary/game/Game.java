package de.chrissx.CLibrary.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class Game implements Runnable{

	protected int countdownTime = 60;
	protected int countdown = 60;
	protected boolean countdownRunning = false, gameRunning = false;
	protected Location arena, lobby, back;
	protected String name;
	protected List<CraftPlayer> players = new ArrayList<CraftPlayer>();
	protected Inventory startItems = Bukkit.createInventory(null, InventoryType.PLAYER);
	protected Inventory specItems = Bukkit.createInventory(null, InventoryType.PLAYER);
	protected Plugin plugin;
	
	public Game(String name, Location lobby, Location arena, Location back, Plugin plugin) {
		this.arena = arena;
		this.lobby = lobby;
		this.name = name;
		this.back = back;
		countdown = countdownTime;
		this.plugin = plugin;
	}
	
	protected void start() {
		movePlayers();
		addItems();
	}
	
	protected void startCountdown() {
		try {
			countdownRunning = true;
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					if(countdown <= 0) {
						countdownRunning = false;
					}else {
						countdown = countdown - 1;
						for(CraftPlayer p : players) {
							p.setTotalExperience(countdown);
							p.setExp((1 / countdownTime) * countdown);
						}
					}
				}
			});
			t.start();
			t.join(0);
			gameRunning = true;
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void onJoin(CraftPlayer p) {
		players.add(p);
		if(gameRunning) {
			p.teleport(arena);
			p.setGameMode(GameMode.SPECTATOR);
			p.getInventory().clear();
			p.getInventory().setContents(specItems.getContents());
			p.updateInventory();
		}else {
			p.teleport(lobby);
		}
	}
	
	public void onLeave(CraftPlayer p) {
		if(!players.contains(p)) {
			return;
		}
		players.remove(p);
		p.teleport(back);
	}
	
	@SuppressWarnings("deprecation")
	protected void addItems() {
		for(CraftPlayer p : players) {
			p.getInventory().clear();
			p.getInventory().setContents(startItems.getContents());
			p.updateInventory();
		}
	}
	
	protected void movePlayers() {
		for(CraftPlayer p : players) {
			p.teleport(arena);
		}
	}

	@Override
	public void run() {
		startCountdown();
	}
	
	
	//
	//
	//
	// GETTERS AND SETTERS
	//  AKA. METHOD HELL
	//
	//
	//
	
	
	public int getCountdownTime() {
		return countdownTime;
	}

	public void setCountdownTime(int countdownTime) {
		this.countdownTime = countdownTime;
		setCountdown(countdownTime);
	}

	public int getCountdown() {
		return countdown;
	}

	public void setCountdown(int countdown) {
		this.countdown = countdown;
	}

	public boolean isCountdownRunning() {
		return countdownRunning;
	}

	public void setCountdownRunning(boolean countdownRunning) {
		this.countdownRunning = countdownRunning;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getArena() {
		return arena;
	}

	public Location getLobby() {
		return lobby;
	}

	public List<CraftPlayer> getPlayers() {
		return players;
	}

	public Inventory getStartItems() {
		return startItems;
	}

	public void setStartItems(Inventory startItems) {
		this.startItems = startItems;
	}

	public Location getBack() {
		return back;
	}

	public Inventory getSpecItems() {
		return specItems;
	}

	public void setSpecItems(Inventory specItems) {
		this.specItems = specItems;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public void setArena(Location arena) {
		this.arena = arena;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	public void setBack(Location back) {
		this.back = back;
	}
}
