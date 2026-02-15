package com.mrbysco.ridableblazes.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Blaze.class)
public class BlazeMixin extends Monster implements PlayerRideable {
	protected BlazeMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public LivingEntity getControllingPassenger() {
		if (!this.isNoAi()) {
			Entity entity = this.getFirstPassenger();
			if (entity instanceof Player player) {
				return player;
			}
		}
		return super.getControllingPassenger();
	}
}
