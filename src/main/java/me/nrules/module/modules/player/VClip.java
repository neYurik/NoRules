package me.nrules.module.modules.player;

import me.nrules.clickgui.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VClip extends Module {
    public VClip() {
        super("VClip", Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("Y", this, 56, 1, 200, false));
    }

    TimerHelper timerHelper = new TimerHelper();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getMinecraft().player == null && Minecraft.getMinecraft().world == null)
            return;

        float tp = (float) Main.settingsManager.getSettingByName("Y").getValDouble();
        for (int i = 0; i < 1; i ++)
        {
            Minecraft.getMinecraft().player.setEntityBoundingBox(Minecraft.getMinecraft().player.getEntityBoundingBox().offset(0, -tp, 0));
            super.onDisable();
        }
        if (Minecraft.getMinecraft().player.fallDistance > 0.1)
            Minecraft.getMinecraft().player.motionY = 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
