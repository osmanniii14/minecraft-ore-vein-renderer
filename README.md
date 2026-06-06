# Ore Vein Renderer

A Minecraft Fabric mod that renders small ore veins through walls in blue.

## Features

- **Detects ALL ore types** in Minecraft 1.21.11 (Mounts & Mayhem)
- **Vein Size Limit**: Only marks veins with 3 or fewer blocks
- **Connection Detection**: Marks single ore blocks only if connected (straight or diagonal)
- **Blue Highlighting**: All detected veins render with blue outlines through walls
- **48-block Radius**: Detects veins within 48 blocks of the player
- **Performance Optimized**: Updates every 5 ticks for minimal performance impact

## Supported Ores

### Overworld Ores
- Coal Ore & Deepslate Coal Ore
- Iron Ore & Deepslate Iron Ore
- Copper Ore & Deepslate Copper Ore
- Gold Ore & Deepslate Gold Ore
- Redstone Ore & Deepslate Redstone Ore
- Diamond Ore & Deepslate Diamond Ore
- Emerald Ore & Deepslate Emerald Ore
- Lapis Ore & Deepslate Lapis Ore

### Nether Ores
- Nether Gold Ore
- Nether Quartz Ore
- Ancient Debris

### Raw Ore Blocks
- Raw Copper Block
- Raw Iron Block
- Raw Gold Block

## Installation

1. Download the mod JAR from the releases
2. Place it in your `mods` folder
3. Launch Minecraft with Fabric Loader 0.19.2

## Requirements

- Minecraft 1.21.11 (Mounts & Mayhem Update)
- Fabric Loader 0.19.2 or newer
- Java 21 or newer
- Fabric API 0.97.3+1.21.11 or newer

## Building

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`

## License

MIT