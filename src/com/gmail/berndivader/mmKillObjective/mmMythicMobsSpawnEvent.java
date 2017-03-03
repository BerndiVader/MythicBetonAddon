package com.gmail.berndivader.mmKillObjective;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import io.lumine.xikage.mythicmobs.MythicMobs;
import pl.betoncraft.betonquest.InstructionParseException;
import pl.betoncraft.betonquest.VariableNumber;
import pl.betoncraft.betonquest.api.QuestEvent;

public class mmMythicMobsSpawnEvent extends QuestEvent {
	
	private Location location;
	private String mobtype;
	private VariableNumber amount, level;

	public mmMythicMobsSpawnEvent(String packName, String instructions) throws InstructionParseException {
		super(packName, instructions);
		
		String[] parse = instructions.split(" ");
		if (parse.length<4) throw new InstructionParseException("Not enough arguments");
		String[] coords = parse[1].split(",");
		if (coords.length<4) throw new InstructionParseException("Wrong location format");
		World world = Bukkit.getServer().getWorld(coords[3]);
		if (world==null) throw new InstructionParseException("World does not exist");
		double x,y,z;
		try {
			x = Double.parseDouble(coords[1]);
			y = Double.parseDouble(coords[2]);
			z = Double.parseDouble(coords[3]);
		} catch (NumberFormatException e) {
			throw new InstructionParseException("Could not parse coordinates");
		}
		this.location = new Location(world,x,y,z);

		String[]parseMob = parse[2].split(":");
        if (parseMob.length != 2) throw new InstructionParseException("Wrong mob format");
        this.mobtype = parseMob[0];
        try {
            this.level = new VariableNumber(packName, parse[2].split(":")[1]);
        } catch (NumberFormatException e) {
            throw new InstructionParseException("Could not parse mob level");
        }
        try {
            this.amount = new VariableNumber(packName, parse[3]);
        } catch (NumberFormatException e) {
            throw new InstructionParseException("Could not parse mob amount");
        }		
	}

	@Override
	public void run(String playerID) {
		int a = this.amount.getInt(playerID);
		int l = this.level.getInt(playerID);
		for (int i=0; i < a; i++) {
			MythicMobs.inst().getMobManager().spawnMob(this.mobtype, this.location, l);
		}
	}
}
