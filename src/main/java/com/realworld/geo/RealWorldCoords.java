package com.realworld.geo;

import net.minecraft.core.BlockPos;

public final class RealWorldCoords {
	public static final int MAX_LON_BLOCKS = 20_000_000;
	public static final int MAX_LAT_BLOCKS = MAX_LON_BLOCKS / 2;
	public static final double DEG_PER_BLOCK = 180.0 / MAX_LON_BLOCKS;
	public static final double BLOCKS_PER_DEG = (double)MAX_LON_BLOCKS / 180.0;

	private RealWorldCoords() {
	}

	public static LatLon fromBlockPos(BlockPos pos) {
		return fromXZ(pos.getX(), pos.getZ());
	}

	public static LatLon fromXZ(double x, double z) {
		double lat = z * DEG_PER_BLOCK;
		double lon = x * DEG_PER_BLOCK;
		return new LatLon(clampLat(lat), wrapLon(lon));
	}

	public static BlockPos toBlockPos(double latDeg, double lonDeg, int y) {
		int x = toBlockX(lonDeg);
		int z = toBlockZ(latDeg);
		return new BlockPos(x, y, z);
	}

	public static int toBlockX(double lonDeg) {
		double lon = wrapLon(lonDeg);
		return (int)Math.round(lon * BLOCKS_PER_DEG);
	}

	public static int toBlockZ(double latDeg) {
		double lat = clampLat(latDeg);
		return (int)Math.round(lat * BLOCKS_PER_DEG);
	}

	public static LatLonBounds chunkBounds(int chunkX, int chunkZ) {
		int minBlockX = chunkX << 4;
		int minBlockZ = chunkZ << 4;
		int maxBlockX = minBlockX + 16;
		int maxBlockZ = minBlockZ + 16;
		LatLon min = fromXZ(minBlockX, minBlockZ);
		LatLon max = fromXZ(maxBlockX, maxBlockZ);
		return new LatLonBounds(min.latDeg(), max.latDeg(), min.lonDeg(), max.lonDeg());
	}

	public static LatLon chunkCenterLatLon(int chunkX, int chunkZ) {
		int centerBlockX = (chunkX << 4) + 8;
		int centerBlockZ = (chunkZ << 4) + 8;
		return fromXZ(centerBlockX, centerBlockZ);
	}

	public static double wrapLon(double lonDeg) {
		double wrapped = lonDeg % 360.0;
		if (wrapped >= 180.0) {
			wrapped -= 360.0;
		} else if (wrapped < -180.0) {
			wrapped += 360.0;
		}
		return wrapped;
	}

	public static double clampLat(double latDeg) {
		if (latDeg > 90.0) {
			return 90.0;
		}
		if (latDeg < -90.0) {
			return -90.0;
		}
		return latDeg;
	}

	public static String formatDegrees(double degrees) {
		double rounded = Math.round(degrees * 1_000_000.0) / 1_000_000.0;
		return Double.toString(rounded);
	}
}