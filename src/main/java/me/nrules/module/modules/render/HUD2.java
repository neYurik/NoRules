package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.ModuleManager;
import me.nrules.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;

public class HUD2 extends Module {
    public HUD2() {
        super("HUD2", Category.RENDER);
    }

    public static Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event)
    {
        doRender();
    }


    public static void doRender()
    {
        ScaledResolution sr = new ScaledResolution(mc);
        GL11.glPushMatrix();
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glTranslated(3.0D, 3.0D, 0.0D);
        mc.fontRenderer.drawString("NoRules", 0, 0, -16746560);
        GL11.glTranslated(-0.5D, -0.5D, 0.0D);
        mc.fontRenderer.drawString("NoRules", 0, 0, -14506000);
        GL11.glScaled(1.0D, 1.0D, 1.0D);
        GL11.glPopMatrix();
        mc.fontRenderer.drawString("vk.com/norules", 5, 22, -6250336);
        GL11.glTranslated(-0.5D, -0.5D, 0.0D);
        mc.fontRenderer.drawString("vk.com/norules", 5, 22, -2039584);
        drawArrayList();
    }

    private static void drawArrayList()
    {
        ScaledResolution scaledresolution = new ScaledResolution(mc);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        float f = -0.9F;
        ModuleManager.getModules().sort(Comparator.comparingInt(m -> Minecraft.getMinecraft().fontRenderer.getStringWidth(((Module) m).getName())).reversed());
        for (Module module : ModuleManager.getModules())
        {
            float f1 = 12.0F * f;
            float f2 = 30.0F;
            String s = module.getName();
            String s1 = module.getMode();
            int k = mc.fontRenderer.getStringWidth(s + s1);
            double d0 = module.offset();

            if (!module.isToggled())
            {
                d0 = 1.0D - d0;
            }

            if (d0 != 0.0D)
            {
                float f3 = (float)Math.round((double)k * Math.sin(Math.toRadians(d0 * 90.0D)));
                int l = 6;
                int i1 = 0;
                RenderUtils.drawRect((double)(i - 1), (double)(f1 + 12.0F), (double)((float)(i - l) - f3 + 1.0F), (double)(f1 + f2 - 8.4F), Integer.MIN_VALUE);
                RenderUtils.drawRect((double)((float)(i - l) - f3 + 2.0F), (double)(f1 + 12.0F), (double)((float)(i - l) - f3), (double)(f1 + f2 - 8.4F), -855638017);
                mc.fontRenderer.drawStringWithShadow(s + s1, (float)i - f3 + 4.0F - (float)l - (float)i1, 13.5F + f1, Color.WHITE.getRGB());
                f += 0.8F;
            }
        }
    }


}
