package com.mclegoman.creakingupdate.common;

import com.mclegoman.creakingupdate.common.block.BlockEntityRegistry;
import com.mclegoman.creakingupdate.common.block.BlockRegistry;
import com.mclegoman.creakingupdate.common.worldgen.decorators.DecoratorRegistry;
import net.fabricmc.api.ModInitializer;

public class CreakingUpdate implements ModInitializer {
	public static final String modID = "creakingupdate";
	@Override
	public void onInitialize() {
		BlockRegistry.init();
		BlockEntityRegistry.init();
		DecoratorRegistry.init();
	}
}