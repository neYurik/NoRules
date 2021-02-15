package me.nrules.module.modules.misc;


import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class Keystrokes extends Module {
    public Keystrokes() {
        super("Keystrokes", Category.RENDER);
        Main.settingsManager.rSetting(new Setting("PosX", this, 1.88D, 1.0D, 900.0D, false));
        Main.settingsManager.rSetting(new Setting("PosY", this, 1.88D, -185.0D, 350.0D, false));
        Main.settingsManager.rSetting(new Setting("Mouse", this, false));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event)
    {
        if (!mc.gameSettings.showDebugInfo) {
            int x = (int) Main.settingsManager.getSettingByName("PosX").getValDouble();
            int y = (int) Main.settingsManager.getSettingByName("PosY").getValDouble();
            int color = Color.WHITE.getRGB();
            RenderUtils.drawRect(x + 20, y + 111, x + 41, y + 130, 0x90000000);
            RenderUtils.drawRect(x + 1, y + 130, x + 61, y + 150, 0x90000000);
            if (mc.gameSettings.keyBindForward.isKeyDown()) {
                RenderUtils.drawRect(x + 21, y + 112, x + 40, y + 130, color);
            }
            if (mc.gameSettings.keyBindBack.isKeyDown()) {
                RenderUtils.drawRect(x + 21, y + 131, x + 40, y + 149, color);
            }
            if (mc.gameSettings.keyBindLeft.isKeyDown()) {
                RenderUtils.drawRect(x + 2, y + 131, x + 20, y + 149, color);
            }
            if (mc.gameSettings.keyBindRight.isKeyDown()) {
                RenderUtils.drawRect(x + 41, y + 131, x + 60, y + 149, color);
            }
            if (Main.settingsManager.getSettingByName("Mouse").getValBoolean()) {
                RenderUtils.drawRect(x + 30, y + 170, x + 61, y + 150, 0x90000000);
                RenderUtils.drawRect(x + 1, y + 170, x + 30, y + 150, 0x90000000);
                if (mc.gameSettings.keyBindAttack.isKeyDown()) {
                    RenderUtils.drawRect(x + 2, y + 150, x + 30, y + 169, -1);
                }
                if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                    RenderUtils.drawRect(x + 60, y + 150, x + 31, y + 169, -1);
                }
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("LMB", x + 7, y + 156, 0xffffffff);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("RMB", x + 37, y + 156, 0xffffffff);
            }
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("W", x + 28, y + 117, 0xffffffff);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("A", x + 8, y + 136, 0xffffffff);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("S", x + 28, y + 136, 0xffffffff);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("D", x + 48, y + 136, 0xffffffff);
        }
    }

}