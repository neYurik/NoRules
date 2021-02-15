package me.nrules.module.modules.ghost;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class Reach extends Module {
    public Reach() {
        super("Reach", Category.GHOST);
        Main.settingsManager.rSetting(new Setting("Range", this, 3.67, 3, 5, false));
    }

    private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    private AxisAlignedBB boundingBox = ZERO_AABB;
    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        this.mc.pointedEntity = j(Main.settingsManager.getSettingByName("Range").getValDouble());
    }

    private Entity j(double distance) {
        RayTraceResult r = this.mc.player.rayTrace(distance, 1.0F);
        double distanceTo = distance;
        Vec3d getPosition = this.mc.player.getPositionEyes(1.0F);
        if (r != null) {
            distanceTo = r.hitVec.distanceTo(getPosition);
        }
        Vec3d ad = this.mc.player.getLook(1.0F);
        Vec3d addVector = getPosition.addVector(ad.x * distance, ad.y * distance, ad.z * distance);
        Entity entity = null;
        List a = this.mc.world.getEntitiesWithinAABBExcludingEntity(this.mc.player, this.boundingBox.expand(ad.x * distance, ad.y * distance, ad.z * distance).expand(1.0D, 1.0D, 1.0D));
        double d = distanceTo;
        int n3 = 0; for (int i = 0; i < a.size(); i = n3) {
            Entity entity2 = (Entity)a.get(n3);
            if (entity2.canBeCollidedWith()) {
                Entity entity3 = entity2;
                float getCollisionBorderSize = entity3.getCollisionBorderSize();
                AxisAlignedBB expand = this.boundingBox.expand(getCollisionBorderSize, getCollisionBorderSize, getCollisionBorderSize);
                RayTraceResult calculateIntercept = expand.calculateIntercept(getPosition, addVector);
                if (expand.contains(getPosition)) {
                    if ((0.0D < d) || (d == 0.0D)) {//
                        entity = entity2;
                        d = 0.0D;
                    }
                }
                else {
                    double j;
                    if ((calculateIntercept != null) && (((j = getPosition.distanceTo(calculateIntercept.hitVec)) < d) || (d == 0.0D))) {
                        if (entity2 == this.mc.player.getRidingEntity()) {
                            if (d == 0.0D) {
                                entity = entity2;
                            }
                        }
                        else {
                            entity = entity2;
                            d = j;
                        }
                    }
                }
            }
            n3++;
        }

        return entity;
    }


}
