package com.mrbysco.ridableblazes.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SaddleCapability implements ISaddle, ICapabilitySerializable<CompoundTag>, ICapabilityProvider {
	private ItemStack saddle = ItemStack.EMPTY;

	@Override
	public boolean isSaddled() {
		return !saddle.isEmpty();
	}

	@Override
	public void setSaddled(@NotNull ItemStack saddleStack) {
		this.saddle = saddleStack;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		return CapabilityHandler.SADDLE_CAP.orEmpty(cap, LazyOptional.of(() -> this));
	}

	@Override
	public CompoundTag serializeNBT() {
		return saddle.save(new CompoundTag());
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		saddle = ItemStack.of(nbt);
	}
}
