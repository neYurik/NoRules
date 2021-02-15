package me.nrules.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ReachUtils {

    public static Object[] getEntityCustom(float pitch, float yaw, final double distance, final double expand, final float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        final Entity var2 = mc.getRenderViewEntity();
        Entity entity = null;
        if (var2 == null || mc.world == null) {
            return null;
        }
        mc.mcProfiler.startSection("pick");
        final Vec3d var3 = var2.getPositionEyes(0.0f);
        final Vec3d var4 = var2.getLookVec();
        final Vec3d var5 = var3.addVector(var4.x * distance, var4.y * distance, var4.z * distance);
        Vec3d var6 = null;
        final float var7 = 1.0f;
        final List var8 = mc.world.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().expand(var4.x * distance, var4.y * distance, var4.z * distance).expand(var7, var7, var7));
        double var9 = distance;
        for (int var10 = 0; var10 < var8.size(); ++var10) {
            final Entity var11 = (Entity) var8.get(var10);
            if (var11.canBeCollidedWith()) {
                final float var12 = var11.getCollisionBorderSize();

                AxisAlignedBB var13 = var11.getEntityBoundingBox().expand(var12, var12, var12);
                var13 = var13.expand(expand, expand, expand);
                final RayTraceResult var14 = var13.calculateIntercept(var3, var5);
                if (var13.contains(var3)) {
                    if (0.0 < var9 || var9 == 0.0) {
                        entity = var11;
                        var6 = ((var14 == null) ? var3 : var14.hitVec);
                        var9 = 0.0;
                    }
                }
                else if (var14 != null) {
                    final double var15 = var3.distanceTo(var14.hitVec);
                    if (var15 < var9 || var9 == 0.0) {
                        boolean canRiderInteract = false;
//                        if (Reflector.ForgeEntity_canRiderInteract.exists()) {
//                            canRiderInteract = Reflector.callBoolean(var11, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
//                        }
                        if (var11 == var2.getRidingEntity() && !canRiderInteract) {
                            if (var9 == 0.0) {
                                entity = var11;
                                var6 = var14.hitVec;
                            }
                        }
                        else {
                            entity = var11;
                            var6 = var14.hitVec;
                            var9 = var15;
                        }
                    }
                }
            }
        }
        if (var9 < distance && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        mc.mcProfiler.endSection();
        if (entity == null || var6 == null) {
            return null;
        }
        return new Object[] { entity, var6 };
    }

}
