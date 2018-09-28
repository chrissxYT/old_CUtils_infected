package de.chrissx.CLibrary.stats;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PlayerStats {

	private long kills, deaths, played, won;
	private float kd;
	private int ranking, winRate;
	private Plugin plugin;
	
	public PlayerStats(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public PlayerStats(long kills, long deaths, long played, long won, Plugin plugin) {
		this.kills = kills;
		this.deaths = deaths;
		this.played = played;
		this.won = won;
		this.plugin = plugin;
		update();
	}

	public long getKills() {
		update();
		return kills;
	}

	public void setKills(long kills) {
		this.kills = kills;
		update();
	}

	public long getDeaths() {
		update();
		return deaths;
	}

	public void setDeaths(long deaths) {
		this.deaths = deaths;
		update();
	}

	public long getPlayed() {
		update();
		return played;
	}

	public void setPlayed(long played) {
		this.played = played;
		update();
	}

	public long getWon() {
		update();
		return won;
	}

	public void setWon(long won) {
		this.won = won;
		update();
	}

	public float getKd() {
		update();
		return kd;
	}

	public int getRanking() {
		update();
		return ranking;
	}

	public int getWinRate() {
		update();
		return winRate;
	}
	
	private Runnable calcWinRate = new Runnable() {
		@Override
		public void run() {
			winRate = (int) ((won / played) * 100);
		}
	};
	
	private Runnable calcKd = new Runnable() {
		@Override
		public void run() {
			kd = kills / deaths;
		}
	};
	
	private Runnable calcRanking = new Runnable() {
		@Override
		public void run() {
			
		}
	};
	
	public void update() {
		Bukkit.getScheduler().runTask(plugin, calcKd);
		Bukkit.getScheduler().runTask(plugin, calcRanking);
		Bukkit.getScheduler().runTask(plugin, calcWinRate);
	}
}
