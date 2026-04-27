package com.mrbysco.ridableblazes;

import com.mojang.logging.LogUtils;
import com.mrbysco.ridableblazes.config.BlazeConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(RidableBlazesMod.MOD_ID)
public class RidableBlazesMod {
	public static final String MOD_ID = "ridableblazes";
	public static final Logger LOGGER = LogUtils.getLogger();

	public RidableBlazesMod(Dist dist, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, BlazeConfig.commonSpec);

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}
	}
}
