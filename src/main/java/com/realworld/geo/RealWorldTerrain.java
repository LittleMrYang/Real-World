package com.realworld.geo;

public final class RealWorldTerrain {
	private static final int DEFAULT_CACHE_SIZE = 4096;
	private static final ChunkTerrainCache CACHE = new ChunkTerrainCache(DEFAULT_CACHE_SIZE);
	private static volatile TerrainDataSource dataSource = new FlatTerrainDataSource();

	private RealWorldTerrain() {
	}

	public static void setDataSource(TerrainDataSource source) {
		dataSource = (source == null) ? new FlatTerrainDataSource() : source;
		CACHE.clear();
	}

	public static TerrainSample sampleAtLatLon(double latDeg, double lonDeg) {
		return dataSource.sample(latDeg, lonDeg);
	}

	public static TerrainSample sampleAtBlock(int blockX, int blockZ) {
		int chunkX = blockX >> 4;
		int chunkZ = blockZ >> 4;
		return sampleForChunk(chunkX, chunkZ);
	}

	public static TerrainSample sampleForChunk(int chunkX, int chunkZ) {
		return CACHE.getOrCompute(chunkX, chunkZ, key -> {
			LatLon center = RealWorldCoords.chunkCenterLatLon(chunkX, chunkZ);
			return dataSource.sample(center.latDeg(), center.lonDeg());
		});
	}
}