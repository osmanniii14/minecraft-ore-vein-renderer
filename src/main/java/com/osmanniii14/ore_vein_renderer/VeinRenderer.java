package com.osmanniii14.ore_vein_renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;

@Environment(EnvType.CLIENT)
public class VeinRenderer {
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) return;
        initialized = true;

        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            renderOreVeins(context);
        });
    }

    private static void renderOreVeins(WorldRenderEvents.AfterTranslucent context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        var cameraPos = context.camera().getPos();
        double camX = cameraPos.x;
        double camY = cameraPos.y;
        double camZ = cameraPos.z;

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.setShader(net.minecraft.client.render.GameRenderer::getPositionColorShader);
        
        VertexConsumer vertexConsumer = context.consumers().getBuffer(RenderLayer.LINES);

        for (BlockPos pos : VeinDetectionManager.MARKED_POSITIONS) {
            double x = pos.getX() - camX;
            double y = pos.getY() - camY;
            double z = pos.getZ() - camZ;

            Box box = new Box(x, y, z, x + 1, y + 1, z + 1);
            drawBoxOutline(vertexConsumer, box, 0.0F, 0.0F, 1.0F, 1.0F);
        }

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    private static void drawBoxOutline(VertexConsumer consumer, Box box, float red, float green, float blue, float alpha) {
        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;
        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;

        // Bottom edges
        addLine(consumer, minX, minY, minZ, maxX, minY, minZ, red, green, blue, alpha);
        addLine(consumer, maxX, minY, minZ, maxX, minY, maxZ, red, green, blue, alpha);
        addLine(consumer, maxX, minY, maxZ, minX, minY, maxZ, red, green, blue, alpha);
        addLine(consumer, minX, minY, maxZ, minX, minY, minZ, red, green, blue, alpha);

        // Top edges
        addLine(consumer, minX, maxY, minZ, maxX, maxY, minZ, red, green, blue, alpha);
        addLine(consumer, maxX, maxY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
        addLine(consumer, maxX, maxY, maxZ, minX, maxY, maxZ, red, green, blue, alpha);
        addLine(consumer, minX, maxY, maxZ, minX, maxY, minZ, red, green, blue, alpha);

        // Vertical edges
        addLine(consumer, minX, minY, minZ, minX, maxY, minZ, red, green, blue, alpha);
        addLine(consumer, maxX, minY, minZ, maxX, maxY, minZ, red, green, blue, alpha);
        addLine(consumer, maxX, minY, maxZ, maxX, maxY, maxZ, red, green, blue, alpha);
        addLine(consumer, minX, minY, maxZ, minX, maxY, maxZ, red, green, blue, alpha);
    }

    private static void addLine(VertexConsumer consumer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        consumer.vertex(x1, y1, z1).color(red, green, blue, alpha).next();
        consumer.vertex(x2, y2, z2).color(red, green, blue, alpha).next();
    }
}