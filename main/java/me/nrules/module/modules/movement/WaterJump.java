package me.nrules.module.modules.movement;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class WaterJump extends Module
{
    public WaterJump()
    {
        super("WaterFeatures", Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("MotionY", this, 1, 0, 10, false));
        Main.settingsManager.rSetting(new Setting("FastUp", this, false));
        Main.settingsManager.rSetting(new Setting("FastDown", this, false));
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getMinecraft().player == null && Minecraft.getMinecraft().world == null)
            return;


        if(!Main.settingsManager.getSettingByName("FastDown").getValBoolean() && !Main.settingsManager.getSettingByName("FastUp").getValBoolean()) {
            if (Minecraft.getMinecraft().player.isInWater() || Minecraft.getMinecraft().player.isInLava()) {
                Minecraft.getMinecraft().player.motionY = Main.settingsManager.getSettingByName("MotionY").getValDouble();
            }
        }

        if (Main.settingsManager.getSettingByName("FastDown").getValBoolean())
        {
            if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode()) && mc.player.isInWater()) {
                if (mc.player.ticksExisted % 3 == 0)
                {
                    mc.player.motionY -= .069f;
                }
            }
        }

        if (Main.settingsManager.getSettingByName("FastUp").getValBoolean())
        {
            if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode()) && mc.player.isInWater()) {
                if (mc.player.ticksExisted % 3 == 0)
                {
                    mc.player.motionY += .02155555f;
                }
            }
        }


    }
}
