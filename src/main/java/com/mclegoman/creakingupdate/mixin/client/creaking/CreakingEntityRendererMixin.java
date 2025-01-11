package com.mclegoman.creakingupdate.mixin.client.creaking;

import com.mclegoman.creakingupdate.common.entity.CreakingVariant;
import com.mclegoman.creakingupdate.common.entity.CreakingVariantRenderState;
import net.minecraft.client.render.entity.CreakingEntityRenderer;
import net.minecraft.client.render.entity.state.CreakingEntityRenderState;
import net.minecraft.entity.mob.CreakingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = CreakingEntityRenderer.class)
public abstract class CreakingEntityRendererMixin {
	@Inject(method = "updateRenderState(Lnet/minecraft/entity/mob/CreakingEntity;Lnet/minecraft/client/render/entity/state/CreakingEntityRenderState;F)V", at = @At("TAIL"))
	private void perspective$updateRenderState(CreakingEntity creakingEntity, CreakingEntityRenderState creakingEntityRenderState, float f, CallbackInfo ci) {
		((CreakingVariantRenderState)creakingEntityRenderState).atnmm$setVariant(((CreakingVariant)creakingEntity).atnmm$getVariant());
	}
	@Inject(method = "getTexture(Lnet/minecraft/client/render/entity/state/CreakingEntityRenderState;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
	private void atnmm$getTexture(CreakingEntityRenderState creakingEntityRenderState, CallbackInfoReturnable<Identifier> cir) {
		String variant = ((CreakingVariantRenderState) creakingEntityRenderState).atnmm$getVariant();
		if (!variant.isEmpty()) cir.setReturnValue(Identifier.ofVanilla("textures/entity/creaking/" + variant.toLowerCase().replace(" ", "").replace("/", "") + "_creaking.png"));
	}
}