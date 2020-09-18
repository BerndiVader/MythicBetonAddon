package com.gmail.berndivader.mythicbetonaddon;

import org.bukkit.Location;

import io.lumine.xikage.mythicmobs.MythicMobs;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.VariableNumber;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.LocationData;

public class MythicMobsSpawnEvent extends QuestEvent {
	
	private LocationData location;
	private String mobtype;
	private VariableNumber amount, level;

	public MythicMobsSpawnEvent(Instruction instructions) throws InstructionParseException {
		super(instructions);
		
		if (instruction.getInstruction().toLowerCase().contains("loc:")) {
			this.location = instructions.getLocation("loc");
		} else throw new InstructionParseException("No Location found!");
		if (instruction.getInstruction().toLowerCase().contains("mobtype:")) {
			this.mobtype = instructions.getOptional("mobtype");
		} else throw new InstructionParseException("No MythicMob mobtype found!");
		
		this.level = new VariableNumber(instructions.getInt("level",1));
		this.amount = new VariableNumber(instructions.getInt("amount", 1));
	}

	@Override
	protected Void execute(String playerID) throws QuestRuntimeException {
		int a = this.amount.getInt(playerID);
		int l = this.level.getInt(playerID);
		Location loc = this.location.getLocation(playerID);
		for (int i=0; i < a; i++) {
			MythicMobs.inst().getMobManager().spawnMob(this.mobtype, loc, l);
		}
		return null;
	}
}
