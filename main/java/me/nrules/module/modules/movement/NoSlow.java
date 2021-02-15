package me.nrules.module.modules.movement;

import com.sun.org.apache.bcel.internal.generic.RET;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", Category.MOVEMENT);
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if(mc.player == null && mc.world == null)
            return;

        if(mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow)
            return;
        if(mc.player.inventory.getCurrentItem().getItem() instanceof ItemShield)
            return;

        if (mc.player.isHandActive())
        {
            mc.player.stopActiveHand();
        }
    }

    public void onDisable()
    {
        mc.player.stopActiveHand();
        super.onDisable();
    }
}