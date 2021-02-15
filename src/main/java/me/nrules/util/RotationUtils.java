package me.nrules.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class RotationUtils {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotations(EntityLivingBase ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + (ent.getEyeHeight() / 2.0F);
        return getRotationFromPosition(x, z, y);
    }

    public static Vec3d getRandomCenter(AxisAlignedBB bb) {
        return new Vec3d(bb.minX + (bb.maxX - bb.minX) * 0.8 * Math.random(), bb.minY + (bb.maxY - bb.minY) * 1 * Math.random(), bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * Math.random());
    }

    public static float[] getNeededRotations(Vec3d vec) {
        Vec3d eyesPos = new Vec3d((Minecraft.getMinecraft()).player.posX, (Minecraft.getMinecraft()).player.posY + new Random().nextInt(2) + (Minecraft.getMinecraft()).player.getEyeHeight(), (Minecraft.getMinecraft()).player.posZ);
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
        return new float[] { MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch) };
    }

    public static float[] getNeededRotations1(EntityLivingBase entityIn) {
        double d0 = entityIn.posX - mc.player.posX;
        double d1 = entityIn.posZ - mc.player.posZ;
        double d2 = entityIn.posY + entityIn.getEyeHeight() - (mc.player.getEntityBoundingBox().minY + (mc.player.getEntityBoundingBox().maxY - mc.player.getEntityBoundingBox().minY));
        double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1);
        float f = (float) (MathHelper.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float) (-(MathHelper.atan2(d2, d3) * 180.0D / Math.PI));
        return new float[]{f, f1};
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().player.posX;
        double zDiff = z - Minecraft.getMinecraft().player.posZ;
        double yDiff = y - Minecraft.getMinecraft().player.posY - 1.2;

        double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[]{yaw, pitch};
    }

    public static float[] getRotations(double x, double y, double z)
    {
        double diffX = x + .95D - mc.player.posX;
        double diffY = (y + .95D) / 2D - (mc.player.posY + mc.player.getEyeHeight());
        double diffZ = z + .95D - mc.player.posZ;

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);

        return new float[] { yaw, pitch };
    }

    public static float[] getRotationsNeeded(Entity entity)
    {
        if (entity == null)
            return null;
        double diffX = entity.posX - Minecraft.getMinecraft().player.posX;
        double diffY;
        if ((entity instanceof EntityLivingBase)) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.666D - (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight());
        } else {
            diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D - (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight());
        }
        double diffZ = entity.posZ - Minecraft.getMinecraft().player.posZ;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
        return new float[] { Minecraft.getMinecraft().player.rotationYaw + MathHelper.wrapDegrees(yaw - Minecraft.getMinecraft().player.rotationYaw), Minecraft.getMinecraft().player.rotationPitch + MathHelper.wrapDegrees(pitch - Minecraft.getMinecraft().player.rotationPitch) };
    }

    public static Vec3d getClientLookVec() {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        float f = MathHelper.cos(-player.rotationYaw * 0.017453292F - 3.1415927F);
        float f1 = MathHelper.sin(-player.rotationYaw * 0.017453292F - 3.1415927F);
        float f2 = -MathHelper.cos(-player.rotationPitch * 0.017453292F);
        float f3 = MathHelper.sin(-player.rotationPitch * 0.017453292F);
        return new Vec3d((f1 * f2), f3, (f * f2));
    }


}
