package me.nrules.module.modules.ghost;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.ModuleManager;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SelfDelete extends Module {
    public SelfDelete() {
        super("Panic", Category.MISC);
    }

    EventBus eventBus;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        for(Module m : ModuleManager.getModules()) {
            m.setToggled(false);
        }
    }
}
