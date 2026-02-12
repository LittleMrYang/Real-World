package com.realworld.debug;

import com.realworld.geo.LatLon;
import com.realworld.geo.RealWorldCoords;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import org.jspecify.annotations.Nullable;

public final class LatLonDebugEntry implements DebugScreenEntry {
	@Override
	public void display(DebugScreenDisplayer debugScreenDisplayer,
			@Nullable Level level,
			@Nullable LevelChunk levelChunk, @Nullable LevelChunk levelChunk2) {
		Minecraft client = Minecraft.getInstance();
		if (client.player == null || debugScreenDisplayer == null) {
			return;
		}
		LatLon latLon = RealWorldCoords.fromBlockPos(client.player.blockPosition());
		String line = "Lat/Lon: " + RealWorldCoords.formatDegrees(latLon.latDeg())
				+ ", " + RealWorldCoords.formatDegrees(latLon.lonDeg());
		debugScreenDisplayer.addLine(line);
	}
}