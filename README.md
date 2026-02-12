# RealWorld

A Fabric mod example that maps real-world latitude/longitude to Minecraft block coordinates and shows the current lat/lon in the client HUD and debug overlay.

## Features
1. `/latlon`: prints your current latitude/longitude in chat.
2. `/tp_latlon <lat> <lon>`: teleports to a given latitude/longitude (`lat` in `-90..90`, `lon` in `-180..180`).
3. Client HUD shows lat/lon in the top-left.
4. Debug screen entry shows lat/lon in the overlay.

## Coordinate Mapping
1. X maps to longitude (Lon), Z maps to latitude (Lat).
2. Longitude range uses `MAX_LON_BLOCKS = 20,000,000`, latitude range uses `MAX_LAT_BLOCKS = 10,000,000`.
3. Linear mapping with longitude wrapped to `[-180, 180)` and latitude clamped to `[-90, 90]`.

## Project Layout
1. `src/main/java`: common (server/logic) code
2. `src/client/java`: client-only code
3. `src/main/resources`: common resources (`fabric.mod.json`, mixins, assets)
4. `src/client/resources`: client resources (client mixins)

## Requirements
1. Java 21
2. Gradle + Fabric Loom
3. Minecraft `1.21.11`
4. Fabric Loader `0.18.2`
5. Fabric API `0.139.4+1.21.11`

Versions are managed in `gradle.properties`.

## Common Commands (PowerShell & Shell)
*Powershell can use `./gradlew.bat`*
1. Build: `./gradlew build`
2. Run client: `./gradlew runClient`
3. Run server: `./gradlew runServer`

## Key Files
1. `src/main/java/com/realworld/RealWorldMod.java`: server entrypoint and command registration
2. `src/client/java/com/realworld/RealWorldModClient.java`: client entrypoint and HUD rendering
3. `src/client/java/com/realworld/debug/LatLonDebugEntry.java`: debug screen entry
4. `src/main/java/com/realworld/geo/RealWorldCoords.java`: lat/lon <-> block coordinate conversion
5. `src/main/resources/fabric.mod.json`: mod metadata and entrypoints

## License
See `LICENSE`.
