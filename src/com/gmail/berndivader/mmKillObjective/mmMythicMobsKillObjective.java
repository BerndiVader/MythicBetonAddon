package com.gmail.berndivader.mmKillObjective;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import pl.betoncraft.betonquest.BetonQuest;
import pl.betoncraft.betonquest.InstructionParseException;
import pl.betoncraft.betonquest.api.Objective;
import pl.betoncraft.betonquest.config.Config;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class mmMythicMobsKillObjective extends Objective implements Listener {
	
	private String[] types, names;
	private int amount;
	private boolean notify;
	private int level;
	private int lmin,lmax;
	
	public mmMythicMobsKillObjective(String name, String label, String instruction) throws InstructionParseException {
		super(name, label, instruction);
		this.types = new String[0];
		this.names = new String[0];
		this.level = 0;
		this.template = MMData.class;
		String[] is = instruction.split(" ");
		if (is.length<2) throw new InstructionParseException("Not enough arguments");
		int a = 1;
		boolean b = false;
		for (String i : is) {
			if (i.contains("type:")) {
				types=(i.substring(5).split(","));
			} else if (i.contains("name:")) {
				names=(i.substring(5).replaceAll("_", " ").split(","));
			} else if (i.contains("amount:")) {
				try {
					a = Integer.parseInt(i.substring(7));
				} catch (NumberFormatException e) {
					throw new InstructionParseException("Could not parse amount");
				}
			} else if (i.contains("notify")) {
				b = true;
			} else if (i.contains("level:")) {
				this.level=1;
				if (i.substring(6).contains("-")) {
					this.level=2;
					String[] lt = i.substring(6).split("-");
					this.lmin = Integer.valueOf(lt[0]);
					this.lmax = Integer.valueOf(lt[1]);
				} else {
					this.lmin = Integer.valueOf(i.substring(6));
				}
			}
		}
		if (a<1) throw new InstructionParseException("Amount cannot be less than 1");
		this.amount=a;
		this.notify=b;
	}
	
	@EventHandler
	public void onMythicMobsDeathEvent(MythicMobDeathEvent e) {
		if (!(e.getKiller() instanceof Player)) return;
		if (this.level==1) {
			if (this.lmin!=e.getMobLevel()) return;
		} else if (this.level==2) {
			if (!(e.getMobLevel()>=this.lmin && e.getMobLevel()<=this.lmax)) return;
		}
		if (Arrays.asList(this.types).contains(e.getMobType().getInternalName()) || Arrays.asList(this.names).contains(e.getEntity().getCustomName())) {
			String playerID = PlayerConverter.getID((Player)e.getKiller());
			if (containsPlayer(playerID) && checkConditions(playerID)) {
				MMData pData = (MMData)dataMap.get(playerID);
				pData.kill();
				if (pData.killed()) {
					completeObjective(playerID);
				} else if (this.notify) {
					Config.sendMessage(playerID, "mobs_to_kill", new String[]{String.valueOf(pData.getAmount())});
				}
			}
		}
	}

	@Override
	public String getDefaultDataInstruction() {
        return Integer.toString(this.amount);
	}

	@Override
	public void start() {
        Bukkit.getPluginManager().registerEvents(this, BetonQuest.getInstance());
	}

	@Override
	public void stop() {
        HandlerList.unregisterAll(this);
	}
	
	public static class MMData extends ObjectiveData {
        
        private int amount;
        
        public MMData(String instruction, String playerID, String objID) {
            super(instruction, playerID, objID);
            amount = Integer.parseInt(instruction);
        }
        
        private void kill() {
            amount--;
            update();
        }
        
        private boolean killed() {
            return amount <= 0;
        }
        
        private int getAmount() {
            return amount;
        }
        
        @Override
        public String toString() {
            return String.valueOf(amount);
        }
    }
}
