package me.nrules.module.modules.player;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoGapple extends Module {
    public AutoGapple() {
        super("AutoGapple", Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("Health", this, 9, 0, 20, false));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event)
    {

        if (mc.player == null && mc.world == null)
            return;

        int key = mc.gameSettings.keyBindUseItem.getKeyCode();
        if (mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemAppleGold) {
            if(mc.player.getHealth() <= Main.settingsManager.getSettingByName("Health").getValDouble()) {
                KeyBinding.setKeyBindState(key, true);
            }
            if(mc.player.getHealth() >= Main.settingsManager.getSettingByName("Health").getValDouble()) {
                KeyBinding.setKeyBindState(key, false);
            }
        }

    }


}
