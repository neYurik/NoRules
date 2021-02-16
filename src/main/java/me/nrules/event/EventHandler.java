package me.nrules.event;

import me.nrules.Main;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;

public class EventHandler
{

    Minecraft mc = Minecraft.getMinecraft();
    private boolean initialized = false;

    public ArrayList<Module> enableModule(){
        return Main.moduleManager.getModuleList().stream().filter(Module::isToggled).collect(toCollection(ArrayList::new));
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
