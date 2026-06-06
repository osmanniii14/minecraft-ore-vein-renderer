package com.osmanniii14.ore_vein_renderer;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class VeinDetectionManager {
    private static final Set<Block> ORE_BLOCKS = new HashSet<>();
    private static final int UPDATE_INTERVAL = 5;
    private static int tickCounter = 0;
    
    public static final Set<BlockPos> MARKED_POSITIONS = Collections.synchronizedSet(new HashSet<>());
    private static BlockPos lastPlayerPos = null;

    static {
        // Overworld ores - Stone variants
        ORE_BLOCKS.add(Blocks.COAL_ORE);
        ORE_BLOCKS.add(Blocks.DEEPSLATE_COAL_ORE);
        ORE_BLOCKS.add(Blocks.IRON_ORE);
        ORE_BLOCKS.add(Blocks.DEEPSLATE_IRON_ORE);
        ORE_BLOCKS.add(Blocks.COPPER_ORE);
        ORE_BLOCKS.add(Blocks.DEEPSLATE_COPPER_ORE);
        ORE_BLOCKS.add(Blocks.GOLD_ORE);
        ORE_BLOCKS.add(Blocks.DEEPSLATE_GOLD_ORE);
        ORE_BLOCKS.add(Blocks.REDSTONE_ORE);
        ORE_BLOCKS.add(Blocks.DEEPSLATE_REDSTONE_ORE);
        ORE_BLOCKS.add(Blocks.DIAMOND_ORE);
        ORE_BLOCKS.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        ORE_BLOCKS.add(Blocks.EMERALD_ORE);
        ORE_BLOCKS.add(Blocks.DEEPSLATE_EMERALD_ORE);
        ORE_BLOCKS.add(Blocks.LAPIS_ORE);
        ORE_BLOCKS.add(Blocks.DEEPSLATE_LAPIS_ORE);
        
        // Nether ores
        ORE_BLOCKS.add(Blocks.NETHER_GOLD_ORE);
        ORE_BLOCKS.add(Blocks.NETHER_QUARTZ_ORE);
        ORE_BLOCKS.add(Blocks.ANCIENT_DEBRIS);
        
        // Raw ore blocks
        ORE_BLOCKS.add(Blocks.RAW_COPPER_BLOCK);
        ORE_BLOCKS.add(Blocks.RAW_IRON_BLOCK);
        ORE_BLOCKS.add(Blocks.RAW_GOLD_BLOCK);
    }

    public static void update(World world, BlockPos playerPos) {
        tickCounter++;
        
        if (tickCounter >= UPDATE_INTERVAL) {
            tickCounter = 0;
            
            if (lastPlayerPos == null || lastPlayerPos.getSquaredDistance(playerPos) > 256) {
                lastPlayerPos = playerPos;
                scanForVeins(world, playerPos);
            }
        }
    }

    private static void scanForVeins(World world, BlockPos centerPos) {
        MARKED_POSITIONS.clear();
        int range = 48;
        
        Set<BlockPos> processed = new HashSet<>();
        
        for (int x = centerPos.getX() - range; x <= centerPos.getX() + range; x++) {
            for (int y = centerPos.getY() - range; y <= centerPos.getY() + range; y++) {
                for (int z = centerPos.getZ() - range; z <= centerPos.getZ() + range; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    
                    if (!processed.contains(pos) && isOreBlock(world, pos)) {
                        Set<BlockPos> vein = floodFillVein(world, pos, new HashSet<>());
                        processed.addAll(vein);
                        
                        if (vein.size() <= 3) {
                            MARKED_POSITIONS.addAll(vein);
                        }
                    }
                }
            }
        }
    }

    private static Set<BlockPos> floodFillVein(World world, BlockPos start, Set<BlockPos> visited) {
        Set<BlockPos> vein = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        
        queue.add(start);
        visited.add(start);
        
        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            vein.add(current);
            
            if (vein.size() > 3) {
                vein.remove(start);
                return vein;
            }
            
            for (BlockPos neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor) && vein.size() < 4) {
                    if (isOreBlock(world, neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }
        
        return vein;
    }

    private static List<BlockPos> getNeighbors(BlockPos pos) {
        List<BlockPos> neighbors = new ArrayList<>();
        
        // All 26 neighbors (6 straight + 12 edge diagonals + 8 corner diagonals)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx != 0 || dy != 0 || dz != 0) {
                        neighbors.add(pos.add(dx, dy, dz));
                    }
                }
            }
        }
        
        return neighbors;
    }

    private static boolean isOreBlock(World world, BlockPos pos) {
        return ORE_BLOCKS.contains(world.getBlockState(pos).getBlock());
    }
}