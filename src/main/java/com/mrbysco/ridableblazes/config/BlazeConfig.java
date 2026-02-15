package com.mrbysco.ridableblazes.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BlazeConfig {
	public static class Common {
		public final ModConfigSpec.BooleanValue burnWhileRiding;

		Common(ModConfigSpec.Builder builder) {
			//General settings
			builder.comment("General settings")
					.push("general");

			burnWhileRiding = builder
					.comment("If true riding a blaze will cause the rider to burn (default: true)")
					.define("burnWhileRiding", true);

			builder.pop();
		}
	}


	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

}
