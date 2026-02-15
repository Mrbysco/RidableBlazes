package com.mrbysco.ridableblazes.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class SaddleHandler {
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent.EntityInteract event) {
		Player player = event.getEntity();
		if (event.getHand() == InteractionHand.OFF_HAND) return;
		if (event.getTarget() instanceof Blaze blaze && !player.level().isClientSide()) {
			if (blaze.isSaddled()) {
				if (!blaze.isVehicle() && !player.isSecondaryUseActive()) {
					if (!player.level().isClientSide()) {
						player.startRiding(blaze);
					}

					event.setCancellationResult(InteractionResult.SUCCESS);
				}
			} else {
				ItemStack itemstack = player.getItemInHand(event.getHand());
				blaze.setItemSlot(EquipmentSlot.SADDLE, itemstack.consumeAndReturn(1, player));
				event.setCancellationResult(InteractionResult.SUCCESS);
			}
		}
	}

	@SubscribeEvent
	public void onDamage(LivingIncomingDamageEvent event) {
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity.isPassenger() && event.getSource().getDirectEntity() instanceof Blaze) {
			event.setCanceled(true);
		}
	}
}
