package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoFall extends Module
{
    public NoFall()
    {
        super("NoFall", Category.MOVEMENT);
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event)
    {
        if(mc.world == null && mc.player == null)
            return;

        if (mc.player.fallDistance >= 2f && isBlockUnder())
        {
            {
                mc.player.connection.sendPacket(new CPacketPlayer(true));
            }
        }
    }


    private boolean isBlockUnder()
    {
        for (int i = (int) (mc.player.posY - 1.0); i > 0; --i)
        {
            BlockPos pos = new BlockPos(mc.player.posX, i, mc.player.posZ);
            if (mc.world.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }


}