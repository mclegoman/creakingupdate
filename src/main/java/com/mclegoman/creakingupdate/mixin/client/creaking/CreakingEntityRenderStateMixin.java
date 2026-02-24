package com.mclegoman.creakingupdate.mixin.client.creaking;

import com.mclegoman.creakingupdate.common.entity.CreakingVariantRenderState;
import net.minecraft.client.render.entity.state.CreakingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CreakingEntityRenderState.class)
public class CreakingEntityRenderStateMixin implements CreakingVariantRenderState {
	@Override
	public String atnmm$getVariant() {
		return this.atnmm$stringName;
	}
	@Override
	public void atnmm$setVariant(String stringName) {
		this.atnmm$stringName = stringName;
	}
	@Unique
	private String atnmm$stringName;
}
