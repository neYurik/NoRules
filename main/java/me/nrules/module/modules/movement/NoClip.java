package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.MoverType;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

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
