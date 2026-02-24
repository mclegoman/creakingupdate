package com.mclegoman.creakingupdate.client;

import com.mclegoman.creakingupdate.client.item.ItemGroupRegistry;
import com.mclegoman.creakingupdate.common.util.Panorama;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class CreakingUpdateClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ItemGroupRegistry.init();
		Panorama.init();
		ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
	}
	public void onTick(MinecraftClient client) {
		Panorama.tick(client);
	}
}