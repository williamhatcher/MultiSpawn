# MultiSpawn
Multiple spawnpoints per world!

This Minecraft Spigot/Bukkit/Paper plugin will teleport a player to the spawnpoint they're closest to.

Useful if you want players to respawn at the closest hospital when they die, or an easy way to teleport them to the 
closest viewing area in an arena upon death!

## Setting Spawns
For this plugin to work properly, you should set a few spawnpoints using
`/addSpawn` while in game, or optionally providing `x y z` to set a specific location. 
You can also provide `pitch` and `yaw` if you'd like.

## Configuration
Default `config.yml`:
```yaml
excluded_worlds:
  - world_the_nether
  - world_the_end
```
**excluded_worlds** is a list of worlds that the plugin will ignore.

## Commands
Command | Permission | Description
--------|------------|------------
/addSpawn [x y z] [pitch] [yaw] | `multispawn.add` | Adds a new spawnpoint at your location. [x y z], [pitch], [yaw] are optional.
/listSpawns | `multispawn.list` | List all spawnpoints.
/nearestSpawn | `multispawn.nearest` | Teleports you to nearest spawnpoint.
/nearestSpawn [player] | `multispawn.nearest.others` | Teleports [player] to their nearest spawnpoint.
/realSpawn | `multispawn.realspawn` | Teleports you to the true world spawn.
/realSpawn [player] | `multispawn.realspawn.others` | Teleports player to the true world spawn.

## Support
You're welcome to create an issue if you find a bug report, or reach out to me on Discord:
**William_CTO#3981**

## Downloading
Download the plugin on the [releases](https://github.com/williamhatcher/MultiSpawn/releases) page.
