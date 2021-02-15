package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiAFK extends Module {
    public AntiAFK() {
        super("AntiAFK", Category.PLAYER);
    }

    TimerHelper timerHelper = new TimerHelper();
    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;

        if (timerHelper.hasReached(3250))
        {
            timerHelper.reset();
            mc.player.jump();
            mc.player.motionY = 0.25f;
        }

    }
}
