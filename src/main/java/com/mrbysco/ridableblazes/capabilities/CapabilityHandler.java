package com.mrbysco.ridableblazes.capabilities;

import com.mrbysco.ridableblazes.RidableBlazesMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityHandler {
	public static final Capability<ISaddle> SADDLE_CAP = CapabilityManager.get(new CapabilityToken<>() {
	});


	@SubscribeEvent
	public void register(RegisterCapabilitiesEvent event) {
		event.register(ISaddle.class);
	}

	@SubscribeEvent
	public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Blaze) {
			event.addCapability(RidableBlazesMod.SADDLE_CAP, new SaddleCapability());
		}
	}
}
