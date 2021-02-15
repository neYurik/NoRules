package me.nrules.module.modules.combat;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoTotems extends Module {
    public AutoTotems() {
        super("AutoTotems", Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("Health", this, 10, 1, 20, true));
    }

    Minecraft mc = Minecraft.getMinecraft();
    int totems;
    boolean moving = false;
    boolean returnI = false;


    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) totems++;
        else
            {
            if (!mc.player.getHeldItemOffhand().isEmpty())
                return;

            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            moving = false;
            if (!mc.player.inventory.getItemStack().isEmpty())
                returnI = true;
            return;
        }

        if (mc.player.inventory.getItemStack().isEmpty())
        {
            if (totems == 0) return;
            int t = -1;
            for (int i = 0; i < 45; i++)
                if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                    t = i;
                    break;
                }
            if (t == -1) return;
            if (mc.player.getHealth() >= 10) {
                mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
                moving = true;
            }
        }
    }

}
