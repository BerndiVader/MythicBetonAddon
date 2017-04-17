package com.gmail.berndivader.mmKillObjective;

import org.bukkit.Bukkit;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.conditions.ConditionAction;
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityCondition;
import pl.betoncraft.betonquest.BetonQuest;
import pl.betoncraft.betonquest.config.Config;
import pl.betoncraft.betonquest.config.ConfigPackage;
import pl.betoncraft.betonquest.utils.PlayerConverter;
import pl.betoncraft.betonquest.utils.Utils;

public class mmBetonHasTag extends SkillCondition implements IEntityCondition {
	
	private String tag, pack;

	public mmBetonHasTag(String line, MythicLineConfig mlc) {
		super(line);
		
		Bukkit.getLogger().info(line);
		try {
			this.ACTION = ConditionAction.valueOf(mlc.getString(new String[]{"action","a"}, "TRUE").toUpperCase());
		} catch (Exception ex) {
			this.ACTION = ConditionAction.TRUE;
		}
		this.tag = mlc.getString(new String[]{"tag","t"},"");
		this.pack = mlc.getString(new String[]{"package","pack","p"},"default");
	}

	@Override
	public boolean check(AbstractEntity entity) {
		if (!entity.isPlayer()) return false;
		ConfigPackage p = Config.getPackages().get(this.pack);
		String t = Utils.addPackage(p, this.tag);
		String pid = PlayerConverter.getID(BukkitAdapter.adapt(entity.asPlayer()));
		return BetonQuest.getInstance().getPlayerData(pid).hasTag(t);
	}
}
