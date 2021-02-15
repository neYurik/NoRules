package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import scala.collection.parallel.ParIterableLike;

public class Sprint extends Module
{
    public Sprint()
    {
        super("Sprint", Category.PLAYER);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        {
            if (Minecraft.getMinecraft().player == null && Minecraft.getMinecraft().world == null)
                return;
        }

        Minecraft.getMinecraft().player.setSprinting(true);
    }

}
