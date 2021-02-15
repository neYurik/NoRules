package me.nrules.module.modules.combat;

import me.nrules.FriendManager;
import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AimBot extends Module
{

    public AimBot()
    {
        super("AimBot", Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("Range", this, 3.67, 0, 5, false));
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void AimBot(TickEvent.ClientTickEvent event)
    {
        if(mc.player == null && mc.world == null)
            return;

        for (Iterator<Entity> entities = mc.world.loadedEntityList.iterator(); entities.hasNext(); )
        {
            Object theObject = entities.next();
            if (theObject instanceof EntityLivingBase)
            {
                EntityLivingBase entity = (EntityLivingBase) theObject;
                if (entity instanceof EntityPlayerSP) continue;

                if (mc.player.getDistance(entity) <= Main.settingsManager.getSettingByName("Range").getValDouble() && !entity.isDead && !FriendManager.isFriend(entity.getName()))
                {
                        faceEntity(entity);
                }
            }

        }
    }


    private float[] getRotations ( double x, double y, double z)
    {
        double diffX = x + .5D - mc.player.posX;
        double diffY = (y + .5D) / 2D - (mc.player.posY + mc.player.getEyeHeight());
        double diffZ = z + .5D - mc.player.posZ;

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180D / Math.PI);

        return new float[]{yaw, pitch};
    }

    public static synchronized void faceEntity (EntityLivingBase entity)
    {
        final float[] rotations = getRotationsEntity(entity);

        if (rotations != null )
        {
            if (Minecraft.getMinecraft().player.ticksExisted % 3 == 0)
            Minecraft.getMinecraft().player.rotationYaw = rotations[0] + ThreadLocalRandom.current().nextInt(5) ;
            //Minecraft.getMinecraft().player.rotationPitch = rotations[1] + 1.0F;// 14
        }
    }

    public static float[] getRotationsNeeded (Entity entity)
    {
        if (entity == null)
        {
            return null;
        }

        final double diffX = entity.posX - Minecraft.getMinecraft().player.posX;
        final double diffZ = entity.posZ - Minecraft.getMinecraft().player.posZ;
        double diffY;

        if (entity instanceof EntityLivingBase)
        {
            final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight());
        } else { diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D - (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight()); }

        final double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[]{Minecraft.getMinecraft().player.rotationYaw + MathHelper.wrapDegrees(yaw - Minecraft.getMinecraft().player.rotationYaw), Minecraft.getMinecraft().player.rotationPitch + MathHelper.wrapDegrees(pitch - Minecraft.getMinecraft().player.rotationPitch)};
    }

    public static float[] getRotationsEntity(EntityLivingBase entity) {
        return getRotations1(entity.posX + randomNumber(0.03D, -0.03D), entity.posY + entity.getEyeHeight() - 0.4D + randomNumber(0.054D, -0.07D), entity.posZ + randomNumber(0.09D, -0.03D));
    }
    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }
    public static float[] getRotations1(double posX, double posY, double posZ) {
        EntityLivingBase player = Minecraft.getMinecraft().player;
        double x = posX - player.posX;
        double y = posY - player.posY + player.getEyeHeight();
        double z = posZ - player.posZ;
        double dist = Math.sqrt(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)-(Math.atan2(y, dist) * 180.0D / Math.PI);
        return new float[] { yaw, pitch };
    }


}
