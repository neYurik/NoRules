package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FullBright extends Module {
    public FullBright() {
        super("FullBright", Category.RENDER);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event)
    {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 100f;
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 1;
        super.onDisable();
    }
}
