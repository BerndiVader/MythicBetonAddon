package com.gmail.berndivader.mythicbetonaddon;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import pl.betoncraft.betonquest.config.Config;
import pl.betoncraft.betonquest.config.ConfigAccessor;
import pl.betoncraft.betonquest.config.ConfigPackage;
import pl.betoncraft.betonquest.conversation.CombatTagger;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class StartBetonQuestConversationMechanic extends SkillMechanic implements ITargetedEntitySkill {
	
	private String packName, convName, sSignal, eSignal;

	public StartBetonQuestConversationMechanic(String skill, MythicLineConfig mlc) {
		super(skill, mlc);
		this.packName = mlc.getString(new String[]{"package","pack","p"},"");
		this.convName = mlc.getString(new String[]{"conversation","conv","c"},"");
		this.sSignal = mlc.getString(new String[]{"startsignal", "ssignal"},"bqStartSignal");
		this.eSignal = mlc.getString(new String[]{"endsignal", "esignal"},"bqEndSignal");
	}

	@Override
	public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
		if (!(target.isPlayer())) {
			System.err.println("Player needed for BetonQuest start conversation mechanic!");
			return false;
		}
		ConfigPackage p = Config.getPackages().get(this.packName);
		if (p==null) {
			System.err.println("The BetonQuest package "+this.packName+" doesnt exists!");
			return false;
		}
        ConfigAccessor c = p.getConversation(this.convName);
        if (c==null) {
			System.err.println("The BetonQuest conversation "+this.convName+" doesnt exists!");
			return false;
        }
		ActiveMob am = null;
		if (((am=(ActiveMob) data.getCaster())==null)) return false;
		String playerID = PlayerConverter.getID(BukkitAdapter.adapt(target.asPlayer()));
        if (CombatTagger.isTagged(playerID)) {
            Config.sendMessage(playerID, "busy");
            return false;
        }
        String convID=Config.getNpc(this.convName);
        if ((convID)==null) {
			System.err.println("The NPC alias ID for "+this.convName+" doesnt exists in main.yml!");
			return false;
        }
        new ActiveMobConversation(playerID, Config.getNpc(this.convName), am, this.sSignal, this.eSignal);
		return true;
	}
}
