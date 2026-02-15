package com.mrbysco.ridableblazes.registry;

import com.mrbysco.ridableblazes.RidableBlazesMod;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class RidableAttachement {
	private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, RidableBlazesMod.MOD_ID);
	public static final Supplier<AttachmentType<ItemStack>> SADDLE = ATTACHMENT_TYPES.register("saddle", () ->
			AttachmentType.builder(() -> ItemStack.EMPTY)
					.serialize(ItemStack.OPTIONAL_CODEC)
					.sync(ItemStack.OPTIONAL_STREAM_CODEC).build());

	public static void register(IEventBus eventBus) {
		ATTACHMENT_TYPES.register(eventBus);
	}
}
