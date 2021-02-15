package me.nrules.module.modules.movement;

import me.nrules.clickgui.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Timer extends Module {
    public Timer() {
        super("Timer", Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("Speed", this, 1, 0.1, 100, false));

    }

    public static Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {


        //=  / (float) Main.settingsManager.getSettingByName("Speed").getValDouble());
    }

    @Override
    public void onEnable() {

    }

    public void onDisable() {
        super.onDisable();
    }



}
