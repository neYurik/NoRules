package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Step extends Module {
    public Step() {
        super("Step", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if(mc.player == null && mc.world == null)
            return;

       mc.player.stepHeight = 1.5f;
    }

    @Override
    public void onDisable() {
        mc.player.stepHeight = 0.5f;
        super.onDisable();
    }
}
