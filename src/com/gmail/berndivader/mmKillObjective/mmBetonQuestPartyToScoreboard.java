package com.gmail.berndivader.mmKillObjective;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import pl.betoncraft.betonquest.VariableNumber;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class mmBetonQuestPartyToScoreboard extends SkillMechanic implements ITargetedEntitySkill {

	private String pTag,pack;
	private VariableNumber range;
	
	public mmBetonQuestPartyToScoreboard(String skill, MythicLineConfig mlc) {
		super(skill, mlc);
		this.ASYNC_SAFE = true;
		this.pTag = mlc.getString(new String[]{"tag","t"},"BetonParty");
		this.range = new VariableNumber(mlc.getDouble(new String[]{"range","r"},20));
	}
		

	@Override
	public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
		if (!target.isPlayer()) return false;
		String pid = PlayerConverter.getID(BukkitAdapter.adapt(target.asPlayer()));
		return true;
	}
}
