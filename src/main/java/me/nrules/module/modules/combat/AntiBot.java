package me.nrules.module.modules.combat;

import com.google.common.collect.Ordering;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AntiBot extends Module {
    public AntiBot() {
        super("AntiBot", Category.COMBAT);
    }

    public static List<EntityPlayer> bots = new ArrayList<>();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;

        for (Object entity : mc.world.loadedEntityList) {
            if (((Entity)entity).isInvisible() && entity != mc.player)
                mc.world.removeEntity((Entity)entity);
        }

        for (int k = 0; k < mc.world.playerEntities.size(); k++)
       {
           EntityPlayer ent = mc.world.playerEntities.get(k);
           List<EntityPlayer> tablist = this.getTapPlayerList();
           if (!bots.contains(ent) && !tablist.contains(ent))
           {
               bots.add(ent);
           } else if (bots.contains(ent) && tablist.contains(ent))
           {
               bots.remove(ent);
           }
       }
    }

    private List<EntityPlayer> getTapPlayerList()
    {
        final List<EntityPlayer> list;
        (list = new ArrayList<>()).clear();
        Ordering<NetworkPlayerInfo>ENTRY_ORDERING = ENTRY_ORDERING();
        if (ENTRY_ORDERING == null)
        {
            return list;
        }
        final List<NetworkPlayerInfo> players = ENTRY_ORDERING.sortedCopy(mc.player.connection.getPlayerInfoMap());
        for (final Object o : players)
        {
            final NetworkPlayerInfo info = (NetworkPlayerInfo) o;
            if (info == null)
            {
                continue;
            }
            list.add(mc.world.getPlayerEntityByName(info.getGameProfile().getName()));
        }
        return list;
    }

    private Ordering<NetworkPlayerInfo>ENTRY_ORDERING()
    {
        try {
            final Class<GuiPlayerTabOverlay> c = GuiPlayerTabOverlay.class;
            final Field f = c.getDeclaredField("ENTRY_ORDERING");
            f.setAccessible(true);
            return (Ordering<NetworkPlayerInfo>)f.get(GuiPlayerTabOverlay.class);

        }catch (Exception e)
        {
            return null;
        }
    }

}