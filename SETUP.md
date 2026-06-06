# Quick Setup Guide

## For Windows Users:

1. **Make sure you have Java 21 installed**
   - Download from: https://www.oracle.com/java/technologies/downloads/#java21
   - Or check: Open Command Prompt and type `java -version`

2. **Download this repository as ZIP**
   - Click the green "Code" button on GitHub
   - Select "Download ZIP"
   - Extract the ZIP file

3. **Open Command Prompt in the extracted folder**
   - Right-click in empty space → "Open Command Prompt here" (or "Open in Terminal")

4. **Run the build**
   ```bash
   gradlew build
   ```

5. **Find your JAR**
   - Look in: `build/libs/ore-vein-renderer-1.0.0.jar`
   - Copy this file to your Minecraft `mods` folder

## Your mods folder location:
- **Windows**: `%APPDATA%\.minecraft\mods`
- **Mac**: `~/Library/Application Support/minecraft/mods`
- **Linux**: `~/.minecraft/mods`

## Requirements:
- Java 21 or newer
- Minecraft 1.21.11 with Fabric Loader 0.19.2
- Fabric API 0.97.3+1.21.11

## Troubleshooting:

If you still get errors, download the prebuilt JAR from GitHub Releases instead (coming soon).

---

**Questions?** Open an issue on GitHub!