package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.MotionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Freecam extends Module {
    public Freecam() {
        super("Freecam", Category.PLAYER);
    }

    Minecraft mc = Minecraft.getMinecraft();

    public void onDisable()
    {
       mc.player.noClip = false;

        {
           mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(
                   mc.player.posX, mc.player.getEntityBoundingBox().minY,
                   mc.player.posZ, mc.player.cameraYaw, mc.player.cameraPitch,
                   mc.player.onGround));
        }

        super.onDisable();
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
       mc.player.noClip = true;
       mc.player.fallDistance = 0;
       mc.player.onGround = true;

       mc.player.capabilities.isFlying = false;
       mc.player.motionX = 0;
       mc.player.motionY = 0;
       mc.player.motionZ = 0;

       float speed = 0.7f;
       mc.player.jumpMovementFactor = speed;

        if(mc.gameSettings.keyBindJump.isKeyDown())
        {
           mc.player.motionY += speed;
        }

        if(mc.gameSettings.keyBindSneak.isKeyDown())
        {
           mc.player.motionY -= speed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S) ||  Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_D))
        {
            float f = MotionUtils.getDirection();
            mc.player.motionX -= (MathHelper.sin(f) * 1.0049F);
            mc.player.motionZ += (MathHelper.cos(f) * 1.0049F);
        }

        if (mc.player.isInWater() && mc.player.isInLava())
        {
            float f = MotionUtils.getDirection();
            mc.player.motionX -= (MathHelper.sin(f) * 1.0049F);
            mc.player.motionZ += (MathHelper.cos(f) * 1.0049F);
        }

    }


    @Override
    public boolean onPacketSent(Packet<?> packet) {
        boolean skip = true;

        if (packet instanceof CPacketPlayer) {
            skip = false;
        }

        return skip;
    }


}
