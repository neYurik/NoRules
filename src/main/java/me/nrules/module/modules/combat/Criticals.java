package me.nrules.module.modules.combat;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", Category.COMBAT);
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if(mc.player == null && mc.world == null)
            return;


        if (mc.player.fallDistance > 0.7)
        {
            mc.player.motionY = 0;
        }


    }
}