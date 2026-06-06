package com.osmanniii14.ore_vein_renderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class OreVeinRendererClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		VeinRenderer.initialize();
		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.world != null && client.player != null) {
				VeinDetectionManager.update(client.world, client.player.getBlockPos());
			}
		});
	}
}