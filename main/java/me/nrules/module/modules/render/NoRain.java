package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoRain extends Module {
    public NoRain() {
        super("NoRain", Category.RENDER);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getMinecraft().world != null) {
            Minecraft.getMinecraft().world.setRainStrength(0.0f);
        }
    }
}
