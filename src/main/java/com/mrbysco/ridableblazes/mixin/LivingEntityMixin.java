package com.mrbysco.ridableblazes.mixin;

import com.mrbysco.ridableblazes.util.RiderUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow
	public abstract boolean canUseSlot(EquipmentSlot slot);

	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(
			at = @At("HEAD"),
			method = "tickRidden(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/Vec3;)V"
	)
	public void ridableblazes$tickRidden(Player player, Vec3 travelVector, CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity instanceof Blaze blaze) {
			RiderUtil.tickRidden(blaze, player, travelVector);
		}
	}

	@Inject(
			at = @At("HEAD"),
			method = "getRiddenInput(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
			cancellable = true
	)
	public void ridableblazes$getRiddenInput(Player player, Vec3 travelVector, CallbackInfoReturnable<Vec3> cir) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity instanceof Blaze blaze) {
			Vec3 vec3 = RiderUtil.riddenInput(blaze, player, travelVector);
			if (!travelVector.equals(vec3))
				cir.setReturnValue(vec3);
		}
	}

	@Inject(
			at = @At("HEAD"),
			method = "getRiddenSpeed(Lnet/minecraft/world/entity/player/Player;)F",
			cancellable = true
	)
	public void ridableblazes$getRiddenSpeed(Player player, CallbackInfoReturnable<Float> cir) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity instanceof Blaze blaze) {
			cir.setReturnValue(RiderUtil.riddenSpeed(blaze, player));
		}
	}

	@Inject(
			at = @At("HEAD"),
			method = "isEquippableInSlot(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;)Z",
			cancellable = true
	)
	public void ridableblazes$isEquippableInSlot(ItemStack stack, EquipmentSlot slot, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity instanceof Blaze) {
			Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
			cir.setReturnValue(equippable == null
					? slot == EquipmentSlot.MAINHAND && this.canUseSlot(EquipmentSlot.MAINHAND)
					: slot == equippable.slot() && this.canUseSlot(equippable.slot()));
		}
	}
}
