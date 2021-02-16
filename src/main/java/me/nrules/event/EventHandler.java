package me.nrules.event;

import me.nrules.Main;
import me.nrules.module.Module;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class EventHandler
{
//
//    Minecraft mc = Minecraft.getMinecraft();
//    private boolean initialized = false;

    public List<Module> enableModule(){
        return Main.moduleManager.getModuleList().stream().filter(Module::isToggled).collect(toList());
    }

//    @SubscribeEvent
//    public void onClientTick(TickEvent.ClientTickEvent event) {
//        if(mc.player == null || mc.world == null) {
//            initialized = false;
//            return;
//        }
//        try {
//            if (!initialized) {
//                new Connection(this);
//                initialized = true;
//            }
//        } catch (RuntimeException ex) {
//            ex.printStackTrace();
//        }
//    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void logIn(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        new ConnectionHandler(this, (NetHandlerPlayClient) e.getHandler());
    }


}
