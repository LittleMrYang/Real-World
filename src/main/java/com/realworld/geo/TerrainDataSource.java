package com.realworld.geo;

public interface TerrainDataSource {
	TerrainSample sample(double latDeg, double lonDeg);
}