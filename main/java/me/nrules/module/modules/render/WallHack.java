package me.nrules.module.modules.render;

import org.lwjgl.opengl.GL11;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WallHack extends Module {
    public WallHack() {
        super("WallHack", Category.RENDER);
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;

        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        RenderHelper.enableStandardItemLighting();

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if ((entity == mc.getRenderViewEntity() && mc.gameSettings.thirdPersonView == 0)) {
                continue;
            }

            mc.entityRenderer.disableLightmap();
            mc.getRenderManager().renderEntityStatic(entity, event.getPartialTicks(), false);
            mc.entityRenderer.enableLightmap();
        }

    }
}

