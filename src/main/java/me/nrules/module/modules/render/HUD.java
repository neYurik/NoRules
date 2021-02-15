package me.nrules.module.modules.render;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.font.FontLoader;
import me.nrules.font.MinecraftFontRenderer;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.ModuleManager;
import me.nrules.util.Refrence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;

public class HUD extends Module {
    public HUD() {
        super("HUD", Category.RENDER);
        Main.settingsManager.rSetting(new Setting("Simple", this, true));
        Main.settingsManager.rSetting(new Setting("Rainbow", this, false));
    }

    private int y;
    public static Minecraft mc = Minecraft.getMinecraft();
    public static FontRenderer fr = mc.fontRenderer;

    public static class ModuleComparator implements Comparator<Module> {
        @Override
        public int compare(Module arg0, Module arg1) {
            if (Minecraft.getMinecraft().fontRenderer.getStringWidth(arg0.getName()) > Minecraft.getMinecraft().fontRenderer.getStringWidth(arg1.getName())) {
                return -1;
            }
            if (Minecraft.getMinecraft().fontRenderer.getStringWidth(arg0.getName()) > Minecraft.getMinecraft().fontRenderer.getStringWidth(arg1.getName())) {
                return 1;
            }
            return 0;
        }
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event) {

        if (Main.settingsManager.getSettingByName("Simple").getValBoolean()) {
            Collections.sort(ModuleManager.getModules(), new ModuleComparator());
            ScaledResolution sr = new ScaledResolution(mc);
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            int count = 2;

            if (mc.player == null && mc.world == null)
                return;

            if (mc.currentScreen == null) {
                String drawFPS = String.valueOf(Minecraft.getMinecraft().getDebugFPS());
                ModuleManager.getModules().sort(Comparator.comparingInt(m -> Minecraft.getMinecraft().fontRenderer.getStringWidth(((Module) m).getName())).reversed());

                Gui.drawRect(1, 1, 65, 15, Color.BLACK.getRGB());
                GL11.glPushMatrix();
                GL11.glScaled(1.5, 1.5, 1.5);
                final int[] counter = {1};
                Minecraft.getMinecraft().fontRenderer.drawString("N", 2, 2, rainbow1(counter[0] * 2));
                Minecraft.getMinecraft().fontRenderer.drawString("o", 8, 2, -1);
                Minecraft.getMinecraft().fontRenderer.drawString("R", 15, 2, rainbow1(counter[0] * 2));
                counter[0]++;
                Minecraft.getMinecraft().fontRenderer.drawString("u", 21, 2, -1);
                Minecraft.getMinecraft().fontRenderer.drawString("l", 27, 2, -1);
                Minecraft.getMinecraft().fontRenderer.drawString("e", 30, 2, -1);
                Minecraft.getMinecraft().fontRenderer.drawString("s", 36, 2, -1);
                GL11.glPopMatrix();
                renderArrayList(sr);
            }
        }

        if (Main.settingsManager.getSettingByName("Rainbow").getValBoolean()) {
            if (mc.currentScreen == null) {

                Gui.drawRect(1, 1, 65, 15, Color.BLACK.getRGB());
                GL11.glPushMatrix();
                GL11.glScaled(1.5, 1.5, 1.5);
                final int[] counter = {1};
                Minecraft.getMinecraft().fontRenderer.drawString("N", 2, 2, rainbow1(counter[0] * 2));
                Minecraft.getMinecraft().fontRenderer.drawString("o", 8, 2, -1);
                Minecraft.getMinecraft().fontRenderer.drawString("R", 15, 2, rainbow1(counter[0] * 2));
                counter[0]++;
                Minecraft.getMinecraft().fontRenderer.drawString("u", 21, 2, -1);
                Minecraft.getMinecraft().fontRenderer.drawString("l", 27, 2, -1);
                Minecraft.getMinecraft().fontRenderer.drawString("e", 30, 2, -1);
                Minecraft.getMinecraft().fontRenderer.drawString("s", 36, 2, -1);
                GL11.glPopMatrix();

                ModuleManager.modules.sort(Comparator.comparingInt(m -> fr.getStringWidth(((Module) m).getName())).reversed());

                ScaledResolution sr = new ScaledResolution(mc);

                int y = 2;
                for (Module m : ModuleManager.getModules()) {
                    if (!m.isToggled())
                        continue;

                    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.name) - 5, y, sr.getScaledWidth(), fr.FONT_HEIGHT + y, 0x70000000);
                    fr.drawString(m.getName(), sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 4, y - 1, astofloc((y * 25)), true);


                    y += fr.FONT_HEIGHT;
                }
            }
        }
    }

        private static void renderArrayList(ScaledResolution sr) {
            Collections.sort(Main.moduleManager.modules, new ModuleComparator());
            int count = 0;
            final int[] counter = {1};
            for(Module m : ModuleManager.getModules()){
                if(m.isToggled()) {
                    int offset = (count * (fr.FONT_HEIGHT + 5));
                    Gui.drawRect((sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 10), offset, (sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 10), fr.FONT_HEIGHT, Color.black.getRGB());
                    Gui.drawRect((sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 6), offset, sr.getScaledWidth(), 12 + offset + 2, Color.black.getRGB());
                    Gui.drawRect(sr.getScaledWidth() - 2, offset, sr.getScaledWidth(), offset + 15, rainbow1(counter[0] * 350));
                    int n = 0;
                    counter[0]++;
                    fr.drawString(m.getName(), (sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 4), 4 + offset, -1);
                    count++;
                }
            }
        }

    public static int rainbow(long offset) {
        float hue = (float)(System.nanoTime() + offset) / 5.0E9F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F))), 16);
        Color c = new Color((int)color);
        return (new Color((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getGreen() / 255, (float)c.getAlpha() / 255.0F)).getRGB();
    }

    public static int rainbow1 (int delay)
    {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20);
        rainbowState %=360;
        return Color.getHSBColor((float) (rainbowState / 360f), 0.5f,1f).getRGB();
    }

    public static int astofloc(int delay) {
        float speed = 3200.0F;
        float hue = (float)(System.currentTimeMillis() % (int)speed) + (delay / 2);
        while (hue > speed)
            hue -= speed;
        hue /= speed;
        if (hue > 0.5D)
            hue = 0.5F - hue - 0.5F;
        hue += 0.5F;
        return Color.HSBtoRGB(hue, 0.5F, 1.0F);
    }

}

