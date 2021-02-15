package me.nrules.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class EntityUtils
{

    public static Minecraft mc = Minecraft.getMinecraft();

    public static void setEntityBoundingBoxSize(Entity entity, float width, float height) {
        EntitySize size = getEntitySize(entity);
        entity.width = size.width;
        entity.height = size.height;
        double d0 = (double) (width) / 2.0D;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, entity.posY, entity.posZ - d0, entity.posX + d0,
                entity.posY + (double) height, entity.posZ + d0));
    }


    public static List<EntityPlayer> getPlayersList() {
        return (Minecraft.getMinecraft().world.playerEntities);
    }

    public static EntitySize getEntitySize(Entity entity) {
        EntitySize entitySize = new EntitySize(0.6F, 1.8F);
        return entitySize;
    }

    public static Entity getEntity(double distance) {
        if (getEntity(distance, 0.0D, 0.0F) == null)
            return null;
        return (Entity)getEntity(distance, 0.0D, 0.0F)[0];
    }

    public static Object[] getEntity(double distance, double expand, float partialTicks) {
        Entity var2 = mc.getRenderViewEntity();
        Entity entity = null;
        if (var2 != null && mc.world != null) {
            mc.mcProfiler.startSection("pick");
            double var3 = distance;
            double var5 = var3;
            Vec3d var7 = var2.getPositionEyes(0.0F);
            Vec3d var8 = var2.getLook(0.0F);
            Vec3d var9 = var7.addVector(var8.x * var3, var8.y * var3, var8.z * var3);
            Vec3d var10 = null;
            float var11 = 1.0F;
            List<Entity> var12 = mc.world.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().expand(var8.x * var3, var8.y * var3, var8.z * var3).expand(var11, var11, var11));
            double var13 = var5;
            for (int var15 = 0; var15 < var12.size(); var15++) {
                Entity var16 = var12.get(var15);
                if (var16.canBeCollidedWith()) {
                    float var17 = var16.getCollisionBorderSize();
                    AxisAlignedBB var18 = var16.getEntityBoundingBox().expand(var17, var17, var17);
                    var18 = var18.expand(expand, expand, expand);
                    RayTraceResult var19 = var18.calculateIntercept(var7, var9);
                    if (var18.contains(var7)) {
                        if (0.0D < var13 || var13 == 0.0D) {
                            entity = var16;
                            var10 = (var19 == null) ? var7 : var19.hitVec;
                            var13 = 0.0D;
                        }
                    } else if (var19 != null) {
                        double var20 = var7.distanceTo(var19.hitVec);
                        if (var20 < var13 || var13 == 0.0D) {
                            boolean canRiderInteract = false;
                            if (var16 == var2.getRidingEntity()) {
                                if (var13 == 0.0D) {
                                    entity = var16;
                                    var10 = var19.hitVec;
                                }
                            } else {
                                entity = var16;
                                var10 = var19.hitVec;
                                var13 = var20;
                            }
                        }
                    }
                }
            }
            if (var13 < var5 &&
                    !(entity instanceof net.minecraft.entity.EntityLivingBase) && !(entity instanceof net.minecraft.entity.item.EntityItemFrame))
                entity = null;
            mc.mcProfiler.endSection();
            if (entity == null || var10 == null)
                return null;
            return new Object[] { entity, var10 };
        }
        return null;
    }


    public static List<Entity> getEntityList(){
        return Minecraft.getMinecraft().world.getLoadedEntityList();
    }

    public static void setEntityBoundingBoxSize(Entity entity) {
        EntitySize size = getEntitySize(entity);
        entity.width = size.width;
        entity.height = size.height;
        double d0 = (double) (entity.width) / 2.0D;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, entity.posY, entity.posZ - d0, entity.posX + d0,
                entity.posY + (double) entity.height, entity.posZ + d0));
    }

}