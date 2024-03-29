package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoPush extends Module {
    public NoPush() {
        super("NoPush", Category.PLAYER);
    }


    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event)
    {
        if(mc.player == null && mc.world == null)
            return;

        mc.player.entityCollisionReduction = 1f;
    }


    @Override
    public void onDisable() {
        mc.player.entityCollisionReduction = 0.1f;
        super.onDisable();
    }
}
