package me.nrules.module.modules.misc;

import me.nrules.FriendManager;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class MClickFriend extends Module {
    public MClickFriend() {
        super("MClickFriend", Category.MISC);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;
        if (Mouse.isButtonDown(2) && mc.player.ticksExisted % 6 == 0  && mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().objectMouseOver.entityHit;
            String name = player.getName();
            if (FriendManager.isFriend(name)) {
                FriendManager.deleteFriend(name);
            } else {
                FriendManager.addFriend(name, name);
            }
        }
    }
}
