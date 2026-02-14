package com.mrbysco.ridableblazes.mixin;

import com.mrbysco.ridableblazes.util.RiderUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Blaze;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(
			at = @At("HEAD"),
			method = "positionRider(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity$MoveFunction;)V",
			cancellable = true
	)
	public void ridableblazes$positionRider(Entity passenger, Entity.MoveFunction callback, CallbackInfo ci) {
		Entity entity = (Entity) (Object) this;
		if (entity instanceof Blaze blaze) {
			RiderUtil.positionRider(blaze, passenger, callback);
			ci.cancel();
		}
	}
}
