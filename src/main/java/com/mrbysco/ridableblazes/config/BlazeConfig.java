package com.mrbysco.ridableblazes.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BlazeConfig {
	public static class Common {
		public final ForgeConfigSpec.BooleanValue burnWhileRiding;

		Common(ForgeConfigSpec.Builder builder) {
			//General settings
			builder.comment("General settings")
					.push("general");

			burnWhileRiding = builder
					.comment("If true riding a blaze will cause the rider to burn (default: true)")
					.define("burnWhileRiding", true);

			builder.pop();
		}
	}


	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

}
