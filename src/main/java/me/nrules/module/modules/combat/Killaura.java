package me.nrules.module.modules.combat;

import me.nrules.FriendManager;
import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RotationUtils;
import me.nrules.util.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Killaura extends Module
{
    public Killaura()
    {
        super("Killaura", Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("Range", this, 3.67, 3, 5, false));
        Main.settingsManager.rSetting(new Setting("FOV", this, 187, 0, 360, false));
        Main.settingsManager.rSetting(new Setting("Rotations", this, false));
        Main.settingsManager.rSetting(new Setting("Players", this, true));
        Main.settingsManager.rSetting(new Setting("Mobs", this, false));
        Main.settingsManager.rSetting(new Setting("Animals", this, false));
        Main.settingsManager.rSetting(new Setting("Villagers", this, false));
    }

    public static EntityPlayerSP entity = null;
    TimerHelper timerHelper = new TimerHelper();
    private float[] facing;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        for (Iterator<Entity> entities = mc.world.loadedEntityList.iterator(); entities.hasNext(); ) {
            Object theObject = entities.next();
            if (theObject instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) theObject;

                if (entity instanceof EntityPlayerSP) continue;

                facing = RotationUtils.getNeededRotations(RotationUtils.getRandomCenter(entity.getEntityBoundingBox()));
                if (mc.player.getDistance(entity) < Main.settingsManager.getSettingByName("Range").getValDouble()) {
                    mc.player.rotationYawHead = facing[0];
                    mc.player.renderYawOffset = facing[0];
                }

                if (timerHelper.hasReached(600) && !FriendManager.isFriend(entity.getName()) && mc.player.ticksExisted % 3 == 0 && entity.isEntityAlive() && mc.player.fallDistance > 0.1 && (checkEntityID(entity)) && mc.player.getDistance(entity) < Main.settingsManager.getSettingByName("Range").getValDouble()) {
                    timerHelper.reset();
                    if (entity.isEntityAlive() || entity.isDead) {
                        if (canAttack(entity) && isPlayerValid(entity)) {
                            mc.player.setSprinting(false);
                            if (isInFOV(entity, Main.settingsManager.getSettingByName("FOV").getValDouble()))
                                if (Main.settingsManager.getSettingByName("Rotations").getValBoolean())
                                    faceEntity(entity);

                            mc.playerController.attackEntity(mc.player, entity);
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                            mc.player.setSprinting(true);
                        }
                        continue;
                    }
                }
            }
        }
    }


    public boolean checkEntityID(Entity entity) {
        boolean check;
        if (!(entity.getEntityId() > 1070000000 || entity.getEntityId() <= -1)) {
            check = true;
        } else {
            check = false;
        }

        return check;
    }

    private boolean canAttack(EntityLivingBase player) {
        if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
            if (player instanceof EntityPlayer && !Main.settingsManager.getSettingByName("Players").getValBoolean())
                return false;
            if (player instanceof EntityAnimal && !Main.settingsManager.getSettingByName("Animals").getValBoolean())
                return false;
            if (player instanceof EntityMob && !Main.settingsManager.getSettingByName("Mobs").getValBoolean())
                return false;
            if (player instanceof EntityVillager && !Main.settingsManager.getSettingByName("Villagers").getValBoolean())
                return false;
            if(player.isOnSameTeam(mc.player))
                return false;
            if(player.isInvisible())
                return false;
        }
        return player != mc.player && player.isEntityAlive() && mc.player.getDistance(player) <= mc.playerController.getBlockReachDistance() && player.ticksExisted > 22;
    }

    public boolean isPlayerValid(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            Collection<NetworkPlayerInfo> playerInfos = this.mc.player.connection.getPlayerInfoMap();
            for (NetworkPlayerInfo info : playerInfos) {
                if (info.getGameProfile().getName().matches(entity.getName())) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean isInFOV(EntityLivingBase entity, double angle) {
        angle *= .5D;
        double angleDiff = getAngleDifference(mc.player.rotationYaw, getRotations(entity)[0]);
        return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
    }

    private float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360F;
        float dist = f > 180F ? 360F - f : f;
        return dist;
    }

    private float[] getRotations(Entity entity) {
        double x = entity.posX - mc.player.posX;
        double z = entity.posZ - mc.player.posZ;
        double y = (entity instanceof net.minecraft.entity.monster.EntityEnderman) ? (entity.posY - mc.player.posY) : (
                entity.posY + entity.getEyeHeight() - 1.9D - mc.player.posY +
                        mc.player.getEyeHeight() - 1.9D);
        double helper = MathHelper.sqrt(x * x + z * z);
        float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
        float newPitch = (float)-Math.toDegrees(Math.atan(y / helper));
        if (z < 0.0D && x < 0.0D) {
            newYaw = (float)(90.0D + Math.toDegrees(Math.atan(z / x)));
        } else if (z < 0.0D && x > 0.0D) {
            newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(z / x)));
        }
        return new float[] { newPitch, newYaw };
    }

    public static synchronized void faceEntity(EntityLivingBase entity) {
        //final float[] rotations = getRotationsEntity(entity);
        final float[] rotations1 = faceTarget(entity, 1000f, 1000f, false);

        if (rotations1 != null) {
            Minecraft.getMinecraft().player.rotationYaw = rotations1[0]  + ThreadLocalRandom.current().nextInt(13) + new Random().nextInt(5);
        }
    }

    public static float[] faceTarget(final Entity target, final float p_706252, final float p_706253,
                                     final boolean miss) {
        final double var4 = target.posX - mc.player.posX;
        final double var5 = target.posZ - mc.player.posZ;
        double var7;
        if (target instanceof EntityLivingBase) {
            final EntityLivingBase var6 = (EntityLivingBase) target;
            var7 = var6.posY + var6.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0
                    - (mc.player.posY + mc.player.getEyeHeight());
        }
        final Random rnd = new Random();
        final double var8 = MathHelper.sqrt(var4 * var4 + var5 * var5);
        final float var9 = (float) (Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float var10 = (float) (-(Math.atan2(var7 - ((target instanceof EntityPlayer) ? 0.25 : 0.0), var8) * 180.0
                / 3.141592653589793));
        final float pitch = changeRotation(mc.player.rotationPitch, var10, p_706253);
        final float yaw = changeRotation(mc.player.rotationYaw, var9, p_706252);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(final float p_706631, final float p_706632, final float p_706633) {
        float var4 = MathHelper.wrapDegrees(p_706632 - p_706631);
        if (var4 > p_706633) {
            var4 = p_706633;
        }
        if (var4 < -p_706633) {
            var4 = -p_706633;
        }
        return p_706631 + var4;
    }

    public static float[] getRotationsNeeded(Entity entity) {
        if (entity == null)
            return null;
        double diffX = entity.posX - Minecraft.getMinecraft().player.posX;
        double diffY;
        if ((entity instanceof EntityLivingBase)) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.666D - (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight());
        } else {
            diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2 - (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight());
        }
        double diffZ = entity.posZ - Minecraft.getMinecraft().player.posZ;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180 / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180 / 3.141592653589793D) ;
        return new float[] { Minecraft.getMinecraft().player.rotationYaw + MathHelper.wrapDegrees(yaw - Minecraft.getMinecraft().player.rotationYaw), Minecraft.getMinecraft().player.rotationPitch + MathHelper.wrapDegrees(pitch - Minecraft.getMinecraft().player.rotationPitch) };
    }

    public static float[] getRotationsEntity(EntityLivingBase entity) {
        return getRotations(entity.posX + randomNumber(0.03D, -0.03D), entity.posY + entity.getEyeHeight() - 0.4D + randomNumber(0.07D, -0.07D), entity.posZ + randomNumber(0.03D, -0.03D));
    }
    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public static float[] getRotations(double posX, double posY, double posZ) {
        EntityLivingBase player = Minecraft.getMinecraft().player;
        double x = posX - player.posX;
        double y = posY - player.posY + player.getEyeHeight() - 0.5F;
        double z = posZ - player.posZ;
        double dist = Math.sqrt(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)-(Math.atan2(y, dist) * 180.0D / Math.PI);
        return new float[] { yaw, pitch };
    }


    public void onDisable()
    {
        entity = null;
        super.onDisable();
    }


}


