# RealWorld Mod (Lat/Lon Coordinate System)

Implemented:
- Spherical Earth mapping with 1 block = 1 meter and origin at lat 0 / lon 0 (see RealWorldCoords).
- Lat/Lon records and chunk bounds + chunk center helpers.
- Terrain data interface with a fast chunk cache and a flat placeholder data source.
- Commands: /latlon and /tp_latlon <lat> <lon>.
- Client HUD overlay showing live lat/lon.