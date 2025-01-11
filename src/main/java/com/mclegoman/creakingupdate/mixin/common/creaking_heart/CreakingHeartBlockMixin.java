package com.mclegoman.creakingupdate.mixin.common.creaking_heart;

import net.minecraft.block.CreakingHeartBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = CreakingHeartBlock.class)
public abstract class CreakingHeartBlockMixin {
	@Inject(method = "isNightAndNatural", at = @At("HEAD"), cancellable = true)
	private static void atnmm$alwaysSpawn(World world, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}
}