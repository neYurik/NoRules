package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoPush extends Module {
    public NoPush() {
        super("NoPush", Category.PLAYER);
    }

    Minecraft mc = Minecraft.getMinecraft();

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
