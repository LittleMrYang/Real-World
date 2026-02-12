package com.realworld.geo;

public final class FlatTerrainDataSource implements TerrainDataSource {
	public static final TerrainSample DEFAULT_SAMPLE = new TerrainSample(0.0, 15.0, 1000.0);

	@Override
	public TerrainSample sample(double latDeg, double lonDeg) {
		return DEFAULT_SAMPLE;
	}
}