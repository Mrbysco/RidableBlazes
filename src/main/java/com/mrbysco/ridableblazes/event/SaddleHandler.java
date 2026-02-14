package com.mrbysco.ridableblazes.event;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SaddleHandler {
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent.EntityInteract event) {
		Player player = event.getEntity();
		if (event.getTarget() instanceof Blaze blaze && blaze instanceof Saddleable saddleable) {
			if (saddleable.isSaddled() && !blaze.isVehicle() && !player.isSecondaryUseActive()) {
				if (!player.level().isClientSide) {
					player.startRiding(blaze);
				}

				event.setCancellationResult(
						InteractionResult.sidedSuccess(player.level().isClientSide)
				);
			}
		}
	}
	@SubscribeEvent
	public void onDamage(LivingDamageEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity.isPassenger() && event.getSource().getDirectEntity() instanceof Blaze) {
			event.setCanceled(true);
		}
	}
}
