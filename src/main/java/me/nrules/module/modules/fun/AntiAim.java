package me.nrules.module.modules.fun;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.Random;

public class AntiAim extends Module {
    public AntiAim() {
        super("AntiAim", Category.FUN);
    }

    Minecraft mc = Minecraft.getMinecraft();
    private float KeepYaw;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if(mc.player == null && mc.world == null)
            return;

        if (!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
            Minecraft.getMinecraft().player.renderYawOffset = Math.round(360 * new Random().nextInt(360));
            Minecraft.getMinecraft().player.rotationYawHead = Math.round(360 * new Random().nextInt(360));
        }

    }
}
