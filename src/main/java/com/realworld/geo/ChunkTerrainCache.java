package com.realworld.geo;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.function.LongFunction;
import net.minecraft.world.level.ChunkPos;

public final class ChunkTerrainCache {
	private final int maxEntries;
	private final Long2ObjectLinkedOpenHashMap<TerrainSample> cache = new Long2ObjectLinkedOpenHashMap<>();

	public ChunkTerrainCache(int maxEntries) {
		this.maxEntries = Math.max(1, maxEntries);
	}

	public TerrainSample getOrCompute(int chunkX, int chunkZ, LongFunction<TerrainSample> factory) {
		long key = ChunkPos.asLong(chunkX, chunkZ);
		TerrainSample sample = cache.getAndMoveToLast(key);
		if (sample != null) {
			return sample;
		}
		sample = factory.apply(key);
		cache.putAndMoveToLast(key, sample);
		if (cache.size() > maxEntries) {
			cache.removeFirst();
		}
		return sample;
	}

	public void clear() {
		cache.clear();
	}
}