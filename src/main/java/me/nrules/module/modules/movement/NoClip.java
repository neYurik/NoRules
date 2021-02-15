package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoClip extends Module
{
    public NoClip()
    {
        super("NoClip", Category.MOVEMENT);
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        mc.player.noClip = true;
        mc.player.fallDistance = 0;
        mc.player.onGround = true;

        mc.player.capabilities.isFlying = false;
        mc.player.motionX = 0;
        mc.player.motionY = 0;
        mc.player.motionZ = 0;

        float speed = 0.2f;
        mc.player.jumpMovementFactor = speed;

        if (mc.gameSettings.keyBindJump.isKeyDown())
        {
            mc.player.motionY += speed;
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY -= speed;
        }
    }

    public void onDisable()
    {

        mc.player.noClip = false;
        super.onDisable();
    }
}
