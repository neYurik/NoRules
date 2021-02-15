package me.nrules.module.modules.ghost;


import me.nrules.FriendManager;
import me.nrules.clickgui.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class AimAssist extends Module {
    public AimAssist() {
        super("AimAssist", Category.GHOST);
        Main.settingsManager.rSetting(new Setting("Speed", this, 3.67, 0.01, 5, false));
        Main.settingsManager.rSetting(new Setting("Range", this, 3.67, 3, 5, false));
    }

    private static Minecraft mc = Minecraft.getMinecraft();
    private EntityPlayer target;
    private List<EntityPlayer> targetlist = new ArrayList();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {

        if (mc.player == null && mc.world == null)
            return;

        targetlist.clear();

        for (EntityPlayer e : mc.world.playerEntities) {
            if (isAttackable(e)) {
                targetlist.add(e);
            }
        }

        if (mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
            Entity entity = mc.objectMouseOver.entityHit;
            if ((entity instanceof EntityPlayer)) {
                target = ((EntityPlayer) entity);
                return;
            }
        }

        if ((!targetlist.contains(target)) && (target != null)) {
            target = null;
            return;
        }

        if (target == null)
            return;

        mc.player.rotationYaw = ((float) (mc.player.rotationYaw - (mc.player.rotationYaw - getAngles(target)[0]) * Main.settingsManager.getSettingByName("Speed").getValDouble()));
        //mc.player.rotationPitch = ((float) (mc.player.rotationPitch - (mc.player.rotationPitch - getAngles(target)[1]) * Main.settingsManager.getSettingByName("Speed").getValDouble()));
    }

    public float[] getAngles(Entity entity) {
        float xDiff = (float) (entity.posX - mc.player.posX);
        float yDiff = (float) (entity.getEntityBoundingBox().minY + entity.getEyeHeight() - mc.player.getEntityBoundingBox().maxY);
        float zDiff = (float) (entity.posZ - mc.player.posZ);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D - 90.0D);
        float pitch = (float) -Math.toDegrees(Math.atan(yDiff / Math.sqrt(zDiff * zDiff + xDiff * xDiff)));
        return new float[]{yaw, pitch};
    }


    private float updateRotation(float par1, float par2, float par3) {
        float var4 = MathHelper.wrapDegrees(par2 - par1);
        if (var4 > par3) {
            var4 = par3;
        }
        if (var4 < -par3) {
            var4 = -par3;
        }
        return par1 + var4;
    }

    private boolean isAttackable(Entity e) {
        if (e == null)
            return false;
        if ((e instanceof EntityPlayer)) {
            EntityPlayer p = (EntityPlayer) e;
            if (FriendManager.isFriend(e.getName()))
                return false;

            if ((!e.isDead) && (!p.isInvisible()) && (mc.player.getDistance(p) <= Main.settingsManager.getSettingByName("Range").getValDouble()) && (mc.player.canEntityBeSeen(p)) && (p != mc.player)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onDisable() {
        target = null;
        super.onDisable();
    }
}