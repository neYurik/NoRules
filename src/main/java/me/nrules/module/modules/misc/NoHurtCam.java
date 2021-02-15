package me.nrules.module.modules.misc;

import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class
NoHurtCam extends Module {
    public NoHurtCam() {
        super("NoHurtCam", Category.MISC);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event)
    {
        if(mc.player == null && mc.world == null)
            return;

        if (!Main.moduleManager.getModule("Velocity").isToggled()) {
            mc.player.hurtTime = 0;
            mc.player.maxHurtTime = 0;
        }
    }
}
