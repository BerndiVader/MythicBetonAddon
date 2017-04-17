package com.gmail.berndivader.mmKillObjective;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import pl.betoncraft.betonquest.BetonQuest;

public class bqMythicMobsKillObjective extends JavaPlugin {
	
	private static Plugin plugin;
	
	@Override
	public void onEnable() {
		
		bqMythicMobsKillObjective.plugin = this;
		
		if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs")==null 
				|| Bukkit.getServer().getPluginManager().getPlugin("BetonQuest")==null) {
			Bukkit.getLogger().warning("MythicMobs or BetonQuest is missing");
			getPluginLoader().disablePlugin(this);
			return;
		}
    	String strMMVer = Bukkit.getServer().getPluginManager().getPlugin("MythicMobs").getDescription().getVersion().replaceAll("[\\D]", "");
		int mmVer = Integer.valueOf(strMMVer);
		if (mmVer < 400) {
			Bukkit.getLogger().warning("Only for MythicMobs 4.0.0 or higher!");
			getPluginLoader().disablePlugin(this);
			return;
		}

		if (Bukkit.getServer().getPluginManager().getPlugin("BetonQuest").getDescription().getVersion().contains("1.9")) {
			Bukkit.getLogger().info("Found BetonQuest Version 1.9 or higher....");
			new mmMythicMobsBetonQuestListeners();
			BetonQuest.getInstance().registerObjectives("mmMythicMobsKillObjective", mmMythicMobsKillObjective.class);
			BetonQuest.getInstance().registerEvents("mmMythicMobsSpawnEvent", mmMythicMobsSpawnEvent.class);
		} else {
			Bukkit.getLogger().info("Found not compatible BetonQuest Version. Use 1.9 or higher!");
			getPluginLoader().disablePlugin(this);
			return;
		}
	}
	
	@Override
	public void onDisable() {
		
	}

	public static Plugin inst() {
		return plugin;
	}
}
