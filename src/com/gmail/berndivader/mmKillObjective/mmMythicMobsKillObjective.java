package com.gmail.berndivader.mmKillObjective;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import pl.betoncraft.betonquest.BetonQuest;
import pl.betoncraft.betonquest.Instruction;
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
	
	public mmMythicMobsKillObjective(Instruction instruction) throws InstructionParseException {
		super(instruction);
		this.types = new String[0];
		this.names = new String[0];
		this.level = 0;
		this.template = MMData.class;
		
		if (instruction.getInstruction().toLowerCase().contains("type:")) {
			this.types = instruction.getOptional("type").split(",");
		}
		if (instruction.getInstruction().toLowerCase().contains("name:")) {
			this.names = instruction.getOptional("name").replaceAll("_", " ").split(",");
		}
		this.amount = instruction.getInt(instruction.getOptional("amount"), 1);
		this.notify = instruction.hasArgument("notify");
		if (instruction.getInstruction().toLowerCase().contains("level:")) {
			String i = instruction.getOptional("level");
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
	
	@Override
	public String getProperty(String name, String playerID) {
		name = name.toLowerCase();
		if (name.equals("left")) {
			return Integer.toString(this.amount - ((MMData) dataMap.get(playerID)).getAmount());
		} else if (name.equals("amount")) {
			return Integer.toString(((MMData) dataMap.get(playerID)).getAmount());
		}
		return "";
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
