package com.mrbysco.ridableblazes.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.Vec3;

public class ShootUtil {
	private static final String NBT_FIREBALL_COOLDOWN = "rb_fireball_cooldown";
	private static final String NBT_FIREBALL_BURST = "rb_fireball_burst";
	private static final String NBT_FIREBALL_DELAY = "rb_fireball_delay";

	private static final int BURST_COUNT = 3;
	private static final int BURST_DELAY_TICKS = 6;
	private static final int COOLDOWN_TICKS = 100;
	private static final float TRIGGER_STRENGTH = 0.9F;

	protected static void tickFireballBurst(Blaze blaze, Player player) {
		if (blaze.level().isClientSide) {
			return;
		}

		CompoundTag tag = blaze.getPersistentData();
		FireballState state = FireballState.load(tag);

		state.tickDown();

		if (state.canStartBurst() && shouldTrigger(player)) {
			state.startBurst(blaze);
		}

		state.tickBurst(blaze);

		state.save(tag);
	}

	private static boolean shouldTrigger(Player player) {
		return player.swinging && player.getAttackStrengthScale(0.5F) > TRIGGER_STRENGTH;
	}

	private static void shootFireball(Blaze blaze) {
		if (!blaze.level().isClientSide) {
			if (!blaze.isSilent()) {
				blaze.level().levelEvent((Player) null, 1018, blaze.blockPosition(), 0);
			}

			Vec3 look = blaze.getLookAngle();
			double inaccuracy = 0.1D;
			double d1 = blaze.getRandom().triangle(look.x, inaccuracy);
			double d2 = blaze.getRandom().triangle(look.y, inaccuracy);
			double d3 = blaze.getRandom().triangle(look.z, inaccuracy);

			Vec3 vec3 = new Vec3(d1, d2, d3);
			SmallFireball smallfireball = new SmallFireball(blaze.level(), blaze, vec3.normalize());
			smallfireball.setPos(smallfireball.getX(), blaze.getY(0.5D) + 0.5D, smallfireball.getZ());
			blaze.level().addFreshEntity(smallfireball);
		}
	}

	private static final class FireballState {
		private int cooldown;
		private int burst;
		private int delay;

		static FireballState load(CompoundTag tag) {
			FireballState state = new FireballState();
			state.cooldown = tag.getInt(NBT_FIREBALL_COOLDOWN);
			state.burst = tag.getInt(NBT_FIREBALL_BURST);
			state.delay = tag.getInt(NBT_FIREBALL_DELAY);
			return state;
		}

		void save(CompoundTag tag) {
			tag.putInt(NBT_FIREBALL_COOLDOWN, cooldown);
			tag.putInt(NBT_FIREBALL_BURST, burst);
			tag.putInt(NBT_FIREBALL_DELAY, delay);
		}

		void tickDown() {
			if (cooldown > 0) {
				cooldown--;
			}
			if (delay > 0) {
				delay--;
			}
		}

		boolean canStartBurst() {
			return cooldown <= 0 && burst == 0;
		}

		void startBurst(Blaze blaze) {
			burst = BURST_COUNT;
			delay = 0;
			cooldown = COOLDOWN_TICKS;
			blaze.setCharged(true);
		}

		void tickBurst(Blaze blaze) {
			if (burst <= 0 || delay > 0) {
				return;
			}

			shootFireball(blaze);
			burst--;
			delay = (burst > 0) ? BURST_DELAY_TICKS : 0;

			if (burst == 0) {
				blaze.setCharged(false);
			}
		}
	}
}
