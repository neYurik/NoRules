package me.nrules.module.modules.ghost;

import me.nrules.FriendManager;
import me.nrules.clickgui.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Hitbox extends Module {
    public Hitbox() {
        super("Hitbox", Category.GHOST);
        Main.settingsManager.rSetting(new Setting("Height", this, 1.8, 1.8, 5, false));
        Main.settingsManager.rSetting(new Setting("Width", this, 0.6, 0.6, 5, false));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getMinecraft().player == null && Minecraft.getMinecraft().world == null)
            return;

        for (EntityLivingBase player : EntityUtils.getPlayersList())
        {
            if (!check((EntityLivingBase)player))
                continue;

            float width = (float) Main.settingsManager.getSettingByName("Width").getValDouble();
            float height = (float) Main.settingsManager.getSettingByName("Height").getValDouble();
            EntityUtils.setEntityBoundingBoxSize(player, width, height);
        }

    }

    public boolean check(EntityLivingBase entity) {
        if(entity instanceof EntityArmorStand) { return false; }
        if(entity == Minecraft.getMinecraft().player) { return false; }
        return !entity.isDead || FriendManager.isFriend(entity.getName());

    }

    public void onDisable()
    {
        for(EntityPlayer player : EntityUtils.getPlayersList())
        EntityUtils.setEntityBoundingBoxSize((Entity) player);
        super.onDisable();
    }

}
