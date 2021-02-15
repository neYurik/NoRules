package me.nrules.module.modules.combat;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FastBow extends Module {
    public FastBow() {
        super("FastBow", Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("Delay", this, 2.02, 0.1, 20, false));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        final Minecraft mc = Minecraft.getMinecraft();

        if (mc.player == null && mc.world == null)
            return;

        if(mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
            if(mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >=  Main.settingsManager.getSettingByName("Delay").getValDouble()) {
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
                mc.player.stopActiveHand();
            }
        }
    }

}
