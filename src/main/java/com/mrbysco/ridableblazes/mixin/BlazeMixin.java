package com.mrbysco.ridableblazes.mixin;

import com.mrbysco.ridableblazes.registry.RidableAttachement;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Blaze.class)
public class BlazeMixin extends Monster implements Saddleable, PlayerRideable {
	protected BlazeMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public boolean isSaddleable() {
		return this.isAlive();
	}

	@Override
	public void equipSaddle(@NotNull ItemStack stack, @Nullable SoundSource source) {
		ItemStack saddle = getData(RidableAttachement.SADDLE);
		if (saddle.isEmpty() && stack.is(Items.SADDLE)) {
			setData(RidableAttachement.SADDLE, stack);
			if (source != null) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
						net.minecraft.sounds.SoundEvents.HORSE_SADDLE, source, 0.5F, 1.0F);
			}
		}
	}

	@Override
	public boolean isSaddled() {
		ItemStack saddle = getData(RidableAttachement.SADDLE);
		return !saddle.isEmpty();
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
