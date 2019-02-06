package com.gmail.berndivader.mythicbetonaddon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import pl.betoncraft.betonquest.BetonQuest;

public
class
MythicBetonAddon 
extends
JavaPlugin 
{
	
	private static Plugin plugin;
	
	@Override
	public void onEnable() {
		
		MythicBetonAddon.plugin = this;
		
		if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs")==null 
				|| Bukkit.getServer().getPluginManager().getPlugin("BetonQuest")==null) {
			Bukkit.getLogger().warning("MythicMobs or BetonQuest is missing");
			getPluginLoader().disablePlugin(this);
			return;
		}
		new MythicMobsBetonQuestListeners();
		BetonQuest.getInstance().registerObjectives("mmMythicMobsKillObjective", MythicMobsKillObjective.class);
		BetonQuest.getInstance().registerEvents("mmMythicMobsSpawnEvent", MythicMobsSpawnEvent.class);
	}
	
	@Override
	public void onDisable() {
		
	}

	public static Plugin inst() {
		return plugin;
	}
}
