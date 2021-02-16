package me.nrules.module.modules.combat;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoCrystal extends Module {
    public AutoCrystal() {
        super("AutoCrystal", Category.COMBAT);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;

        for (Entity e : mc.world.loadedEntityList) {
            if (mc.player.getDistance(e) <= 4.5D && e instanceof EntityEnderCrystal)
                mc.playerController.attackEntity(mc.player, e);
        }
    }

}
