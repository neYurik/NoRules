package me.nrules.event;

import me.nrules.Main;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler
{

    Minecraft mc = Minecraft.getMinecraft();
    private boolean initialized = false;

    public boolean onPacket(Object packet, Connection.Side side) {
        boolean suc = true;
        for (Module module : Main.moduleManager.getModuleList()) {
            if (!module.isToggled() || mc.world == null) {
                continue;
            }
            suc &= module.onPacket(packet, side);
        }
        return suc;
    }


    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if(mc.player == null || mc.world == null) {
            initialized = false;
            return;
        }
        try {
            if (!initialized) {
                new Connection(this);
                initialized = true;
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

}
