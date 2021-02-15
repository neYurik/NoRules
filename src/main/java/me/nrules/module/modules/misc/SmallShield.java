package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SmallShield extends Module {
    public SmallShield() {
        super("SmallShield", Category.MISC);
    }

    Minecraft mc = Minecraft.getMinecraft();
    ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        //itemRenderer.equippedProgressOffHand = 0f;
    }
}