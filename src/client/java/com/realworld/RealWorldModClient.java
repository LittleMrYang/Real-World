package com.realworld;

import com.realworld.debug.LatLonDebugEntry;
import com.realworld.geo.LatLon;
import com.realworld.geo.RealWorldCoords;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.resources.Identifier;

public class RealWorldModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Identifier latLonId = Identifier.fromNamespaceAndPath(RealWorldMod.MOD_ID, "latlon");
		DebugScreenEntries.register(latLonId, new LatLonDebugEntry());

		ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
			client.debugEntries.setStatus(latLonId, DebugScreenEntryStatus.IN_OVERLAY);
		});

		HudElementRegistry.addLast(latLonId, (drawContext, deltaTracker) -> {
			Minecraft client = Minecraft.getInstance();
			if (client.player == null || client.options.hideGui) {
				return;
			}
			LatLon latLon = RealWorldCoords.fromBlockPos(client.player.blockPosition());
			String text = "Lat " + RealWorldCoords.formatDegrees(latLon.latDeg())
					+ "  Lon " + RealWorldCoords.formatDegrees(latLon.lonDeg());
			drawContext.drawString(client.font, text, 8, 8, 0xE0E0E0);
		});
	}
}