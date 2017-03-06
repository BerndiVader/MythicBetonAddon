package com.gmail.berndivader.mmKillObjective;

import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import pl.betoncraft.betonquest.conversation.Conversation;

public class ActiveMobConversation extends Conversation {
	
	private ActiveMob am;
	private String startSignal, endSignal;

	public ActiveMobConversation(String playerID, String packName, String conversationID, ActiveMob activeMob, String sSignal, String eSignal) {
		super(playerID, packName, conversationID, activeMob.getEntity().getBukkitEntity().getLocation());
		this.am = activeMob;
		this.startSignal = sSignal;
		this.endSignal = eSignal;
	}
	
	public ActiveMob getActiveMob() {
		return this.am;
	}
	public String getStartSignal() {
		return this.startSignal;
	}
	public String getEndSignal() {
		return this.endSignal;
	}
}
