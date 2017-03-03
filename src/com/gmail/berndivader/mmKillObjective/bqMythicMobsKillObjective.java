package com.gmail.berndivader.mmKillObjective;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.betoncraft.betonquest.BetonQuest;

public class bqMythicMobsKillObjective extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs")==null 
				|| Bukkit.getServer().getPluginManager().getPlugin("BetonQuest")==null) {
			Bukkit.getLogger().warning("MythicMobs or BetonQuest is missing");
			getPluginLoader().disablePlugin(this);
			return;
		}
    	String strMMVer = Bukkit.getServer().getPluginManager().getPlugin("MythicMobs").getDescription().getVersion();
		int mmVer = Integer.valueOf(strMMVer.replaceAll("\\.", ""));
		if (mmVer < 400) {
			Bukkit.getLogger().warning("Only for MythicMobs 4.0.0 or higher!");
			getPluginLoader().disablePlugin(this);
			return;
		}
		BetonQuest.getInstance().registerObjectives("mmMythicMobsKillObjective", mmMythicMobsKillObjective.class);
		BetonQuest.getInstance().registerEvents("mmMythicMobsSpawnEvent", mmMythicMobsSpawnEvent.class);
	}
	
	@Override
	public void onDisable() {
		
	}

}
