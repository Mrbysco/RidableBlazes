package com.mrbysco.ridableblazes;

import com.mojang.logging.LogUtils;
import com.mrbysco.ridableblazes.capabilities.CapabilityHandler;
import com.mrbysco.ridableblazes.config.BlazeConfig;
import com.mrbysco.ridableblazes.event.SaddleHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(RidableBlazesMod.MOD_ID)
public class RidableBlazesMod {
	public static final String MOD_ID = "ridableblazes";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final ResourceLocation SADDLE_CAP = new ResourceLocation(MOD_ID, "saddled");

	public RidableBlazesMod() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BlazeConfig.commonSpec);

		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new SaddleHandler());
	}
}
