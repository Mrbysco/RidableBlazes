package com.mrbysco.ridableblazes.util;

import com.mrbysco.ridableblazes.config.BlazeConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class RiderUtil {
	public static void positionRider(Blaze blaze, Entity passenger, Entity.MoveFunction callback) {
		if (blaze.hasPassenger(passenger)) {
			float f = Mth.cos(blaze.yBodyRot * ((float) Math.PI / 180F));
			float f1 = Mth.sin(blaze.yBodyRot * ((float) Math.PI / 180F));
			callback.accept(passenger,
					blaze.getX() + (double) (0.5F * f1),
					blaze.getY() + 0.75F,
					blaze.getZ() - (double) (0.5F * f)
			);
		}
	}

	public static void tickRidden(Blaze blaze, Player player, Vec3 travelVector) {
		blaze.setTarget(null);

		if (BlazeConfig.COMMON.burnWhileRiding.get()) {
			player.setRemainingFireTicks(20);
		}

		blaze.setYRot(player.getYRot());
		blaze.setXRot((player.getXRot() * 0.5F) % 360.0F);
		blaze.yRotO = blaze.yBodyRot = blaze.yHeadRot = blaze.getYRot();

		ShootUtil.tickFireballBurst(blaze, player);
	}

	public static Vec3 riddenInput(Blaze blaze, Player player, Vec3 travelVector) {
		float x = player.xxa * 0.5F;
		float z = player.zza;
		if (z <= 0.0F) {
			z *= 0.25F;
		}

		float y = 0.0f;
		if (player.jumping) {
			y = 2.0F;
		}
		return new Vec3(x, y, z);
	}

	public static float riddenSpeed(Blaze blaze, Player player) {
		if (blaze.onGround()) {
			return 0.15F;
		}
		return 0.75F;
	}
}
