package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Fly extends Module {
    public Fly() {
        super("Fly", Category.MOVEMENT);
    }


    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getMinecraft().player == null && Minecraft.getMinecraft().world == null)
            return;

        Minecraft.getMinecraft().player.capabilities.isFlying = true;

    }

    public void onDisable()
    {
        Minecraft.getMinecraft().player.capabilities.isFlying = false;
        super.onDisable();
    }
}
