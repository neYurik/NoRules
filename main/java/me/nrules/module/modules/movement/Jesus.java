package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Jesus extends Module
{
    public Jesus()
    {
        super("Jesus", Category.MOVEMENT);
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;

        IBlockState iBlockState = Minecraft.getMinecraft().world.getBlockState(new BlockPos(this.mc.player.posX, this.mc.player.posY + 1.0E-4D, this.mc.player.posZ));

        if (iBlockState.getMaterial() == Material.WATER || iBlockState.getMaterial() == Material.LAVA)
        {
                this.mc.player.motionY = 0.0D;
                EntityPlayerSP player = this.mc.player;
                player.motionX *= 0.9990000095367432D;
                player = this.mc.player;
                player.motionZ *= 0.9990000095367432D;

                if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()))
                {
                    player = this.mc.player;
                    player.motionY += 0.10000000149011612D;
                }

                if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()))
                {
                    player = this.mc.player;
                    player.motionY -= 0.10000000149011612D;
                }

                if (this.mc.player.collidedHorizontally)
                {
                    player = this.mc.player;
                    player.motionY += 0.10000000149011612D;
                }
        }
    }
}
