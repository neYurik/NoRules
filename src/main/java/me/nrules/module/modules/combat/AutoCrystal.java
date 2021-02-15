package me.nrules.module.modules.combat;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
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

        for (Entity e : this.mc.world.loadedEntityList) {
            if (e.getDistance(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ) <= 4.5D && e instanceof net.minecraft.entity.item.EntityEnderCrystal)
                this.mc.playerController.attackEntity((EntityPlayer)mc.player, e);
        }
    }

}
