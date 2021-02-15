package me.nrules.module.modules.fun;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FakeHack extends Module {
    public FakeHack() {
        super("FakeHack", Category.FUN);
    }

    TimerHelper delay;
    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (mc.player == null || mc.player.isDead) return;
        List<Entity> targets = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= 5)
                .filter(entity -> !entity.isDead)
                .filter(this::attackCheck)
                .filter(entity -> !(entity instanceof EntityArmorStand))
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e)))
                .collect(Collectors.toList());

        if (targets.size() > 0) {
            EntityPlayer player = (EntityPlayer) targets.get(0);
            float[] rots = getFacePosEntityRemote(player, mc.player);
            player.swingArm(EnumHand.MAIN_HAND);
            player.rotationYawHead = rots[0];
            player.renderYawOffset = rots[0];
        }
    }


    public boolean attackCheck(Entity player) {
        if (player instanceof EntityPlayer &&
                ((EntityPlayer) player).getHealth() > 0 &&
                (Math.abs(mc.player.rotationYaw - (getNeededRotations((EntityLivingBase) player)[0])) % 180 < 190) &&
                !player.isInvisible() &&
                !(player.getUniqueID().equals(mc.player.getUniqueID()))) {
            return true;
        }
        return false;
    }

    public static float[] getNeededRotations(EntityLivingBase entityIn) {
        double d0 = entityIn.posX - Minecraft.getMinecraft().player.posX;
        double d1 = entityIn.posZ - Minecraft.getMinecraft().player.posZ;
        double d2 = entityIn.posY + entityIn.getEyeHeight() - (Minecraft.getMinecraft().player.getEntityBoundingBox().minY
                + (Minecraft.getMinecraft().player.getEntityBoundingBox().maxY - Minecraft.getMinecraft().player.getEntityBoundingBox().minY));
        double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1);
        float f = (float) (MathHelper.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float) (-(MathHelper.atan2(d2, d3) * 180.0D / Math.PI));
        return new float[] { f, f1 };
    }

    public static float[] getFacePosEntityRemote(EntityLivingBase facing, Entity en) {
        if (en == null) {
            return new float[]{facing.rotationYawHead, facing.rotationPitch};
        }
        return getFacePosRemote(new Vec3d(facing.posX, facing.posY + en.getEyeHeight(), facing.posZ),
                new Vec3d(en.posX, en.posY + en.getEyeHeight(), en.posZ));
    }

    public static float[] getFacePosRemote(Vec3d src, Vec3d dest) {
        double diffX = dest.x - src.x;
        double diffY = dest.y - (src.y);
        double diffZ = dest.z - src.z;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[]{MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch)};
    }

}
