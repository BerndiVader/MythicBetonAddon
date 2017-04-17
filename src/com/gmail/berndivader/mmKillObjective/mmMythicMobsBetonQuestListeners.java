package com.gmail.berndivader.mmKillObjective;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicConditionLoadEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import pl.betoncraft.betonquest.api.PlayerConversationEndEvent;
import pl.betoncraft.betonquest.api.PlayerConversationStartEvent;

public class mmMythicMobsBetonQuestListeners implements Listener {
    private HashMap<ActiveMob, Integer>  convMobs = new HashMap<>();
	
	public mmMythicMobsBetonQuestListeners() {
		Bukkit.getPluginManager().registerEvents(this, bqMythicMobsKillObjective.inst());
	}

	@EventHandler
	public void onMythicMobsMechanicsLoad(MythicMechanicLoadEvent e) {
		if (e.getMechanicName().toLowerCase().equals("betonquest")) {
			SkillMechanic skill = new mmStartBetonQuestConversationMechanic(e.getContainer().getConfigLine(), e.getConfig());
			if (skill!=null) e.register(skill);
//		} else if (e.getMechanicName().toLowerCase().equals("bqpartytotag")) {
//			SkillMechanic skill = new mmBetonQuestPartyToScoreboard(e.getContainer().getConfigLine(), e.getConfig());
//			if (skill!=null) e.register(skill);
 		}
	}
	
	@EventHandler
	public void onMythicMobsConditionLoad(MythicConditionLoadEvent e) {
		if (e.getConditionName().toLowerCase().equals("bqhastag")) {
			SkillCondition condition = new mmBetonHasTag(e.getConfig().getLine(), e.getConfig());
			if (condition!=null) e.register(condition);
		}
	}
	
	@EventHandler
	public void onConversationStart(PlayerConversationStartEvent e) {
		if (!(e.getConversation() instanceof ActiveMobConversation)) return;
		ActiveMobConversation ac = (ActiveMobConversation) e.getConversation();
		ActiveMob am = ac.getActiveMob();
        if (!convMobs.containsKey(am)) {
        	convMobs.put(am, new Integer(1));
        } else {
        	convMobs.put(am, convMobs.get(am) + 1);
        }
        sendConvSignal(ac, e.getPlayer(), true);
	}

	@EventHandler
	public void onConversationEnd(PlayerConversationEndEvent e) {
		if (!(e.getConversation() instanceof ActiveMobConversation)) return;
        new BukkitRunnable() {
            public void run() {
            	ActiveMobConversation ac = (ActiveMobConversation) e.getConversation();
                ActiveMob am = ac.getActiveMob();
                Integer i = convMobs.get(am);
                i--;
                if (i == 0) {
                	convMobs.remove(am);
            		sendConvSignal(ac, e.getPlayer(), false);
                } else {
                	convMobs.put(am, i);
                }
            }
        }.runTask(bqMythicMobsKillObjective.inst());
	}
	
	private void sendConvSignal(ActiveMobConversation ac, Player p, boolean s) {
		ActiveMob am = ac.getActiveMob();
		String signal = s?ac.getStartSignal():ac.getEndSignal();
		am.signalMob(BukkitAdapter.adapt(p), signal);
		return;
	}
}
