package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class ItemUI extends Module {
    public ItemUI() {
        super("ItemUI", Category.RENDER);
    }

    private static final AxisAlignedBB ITEM_BOX = new AxisAlignedBB(-0.175, 0, -0.175, 0.175, 0.35, 0.175);

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent event)
    {

        if (Minecraft.getMinecraft().player == null && Minecraft.getMinecraft().world == null)
            return;
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(2);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);
        double renderPosX = Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double renderPosY = Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double renderPosZ = Minecraft.getMinecraft().getRenderManager().viewerPosZ;

        glTranslated(-renderPosX, -renderPosY, -renderPosZ);

        glColor3f(0F,1F,0F);

        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                glPushMatrix();
                glTranslated(entity.posX, entity.posY, entity.posZ);

                RenderUtils.drawOutlinedBox(ITEM_BOX);

                glPopMatrix();
            }
        }

        glColor3f(0,1,0);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glPopMatrix();
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
}
