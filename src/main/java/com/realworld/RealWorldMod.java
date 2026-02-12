package com.realworld;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.realworld.geo.LatLon;
import com.realworld.geo.RealWorldCoords;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealWorldMod implements ModInitializer {
	public static final String MOD_ID = "realworld";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("latlon")
					.executes(ctx -> {
						ServerPlayer player = ctx.getSource().getPlayerOrException();
						LatLon latLon = RealWorldCoords.fromBlockPos(player.blockPosition());
						String message = "Lat " + RealWorldCoords.formatDegrees(latLon.latDeg())
								+ ", Lon " + RealWorldCoords.formatDegrees(latLon.lonDeg());
						ctx.getSource().sendSystemMessage(Component.literal(message));
						return 1;
					}));

			dispatcher.register(Commands.literal("tp_latlon")
					.then(Commands.argument("lat", DoubleArgumentType.doubleArg(-90.0, 90.0))
							.then(Commands.argument("lon", DoubleArgumentType.doubleArg(-180.0, 180.0))
									.executes(ctx -> {
										ServerPlayer player = ctx.getSource().getPlayerOrException();
										double lat = DoubleArgumentType.getDouble(ctx, "lat");
										double lon = DoubleArgumentType.getDouble(ctx, "lon");
										int x = RealWorldCoords.toBlockX(lon);
										int z = RealWorldCoords.toBlockZ(lat);
										double y = player.getY();
										player.teleportTo(x + 0.5, y, z + 0.5);
										String message = "Teleported to lat " + RealWorldCoords.formatDegrees(lat)
												+ ", lon " + RealWorldCoords.formatDegrees(lon);
										ctx.getSource().sendSystemMessage(Component.literal(message));
										return 1;
									}))));
		});

		LOGGER.info("RealWorld coordinates initialized");
	}
}