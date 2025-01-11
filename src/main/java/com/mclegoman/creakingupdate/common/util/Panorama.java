package com.mclegoman.creakingupdate.common.util;

import com.mclegoman.creakingupdate.common.CreakingUpdate;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Panorama {
	public static KeyBinding takePanorama = null;
	public static boolean canTakePanorama() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}
	public static void init() {
		if (canTakePanorama()) {
			takePanorama = KeyBindingHelper.registerKeyBinding(new KeyBinding(CreakingUpdate.modID + ".keybinding.take_panorama", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, CreakingUpdate.modID + ".keybindings"));
		}
	}
	public static void tick(MinecraftClient client) {
		if (canTakePanorama()) {
			if (takePanorama != null && takePanorama.wasPressed()) {
				if (client.player != null) {
					client.player.setYaw(findClosest(client.player.getYaw(client.getRenderTickCounter().getTickDelta(true)) % 360.0F));
					client.player.setPitch(0.0F);
					client.takePanorama(FabricLoader.getInstance().getGameDir().toFile(), 1024, 1024);
				}
			}
		}
	}
	public static float findClosest(float originalFloat) {
		float[] arrayOfFloats = new float[]{0.0F, 90.0F, 180.0F, 270F, 360.0F};
		float closestValue = arrayOfFloats[0];
		for (float value : arrayOfFloats) {
			if (Math.abs(value - originalFloat) < Math.abs(closestValue - originalFloat)) {
				closestValue = value;
			}
		}
		return closestValue;
	}
}