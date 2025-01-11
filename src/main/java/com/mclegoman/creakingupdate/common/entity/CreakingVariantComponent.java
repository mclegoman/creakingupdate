package com.mclegoman.creakingupdate.common.entity;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;

public class CreakingVariantComponent {
	private final DataTracker dataTracker;
	private final TrackedData<String> variant;

	public CreakingVariantComponent(DataTracker dataTracker, TrackedData<String> variant) {
		this.dataTracker = dataTracker;
		this.variant = variant;
	}
	public void writeNbt(NbtCompound nbt) {
		nbt.putString("variant", this.getVariant());
	}
	public void readNbt(NbtCompound nbt) {
		this.setVariant(nbt.getString("variant"));
	}
	public void setVariant(String value) {
		this.dataTracker.set(this.variant, value);
	}
	public String getVariant() {
		return this.dataTracker.get(this.variant);
	}
}

