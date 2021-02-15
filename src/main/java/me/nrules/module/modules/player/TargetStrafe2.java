package me.nrules.module.modules.player;

import me.nrules.FriendManager;
import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.MotionUtils;
import me.nrules.util.RenderUtils;
import me.nrules.util.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TargetStrafe2 extends Module {
    public TargetStrafe2() {
        super("TargetStrafe", Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("Speed", this, 0.2, 0, 1, false));
        Main.settingsManager.rSetting(new Setting("Distance", this, 3, 0.1, 6, false));
    }

    public static transient boolean direction = false;
    Minecraft mc = Minecraft.getMinecraft();
    public static int index;
    public static EntityLivingBase target;

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) throws Throwable {
        if (mc.world == null || mc.player == null) return;

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (entityLivingBase.isDead || entityLivingBase == mc.player)
                    continue;
                drawCirle(entity, target != null && entity == target ? new Color(255, 72, 67) : new Color(255, 255, 255), event.getPartialTicks());
            }
        }

    }

    private void drawCirle(Entity entity, Color color, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        final double x = RenderUtils.interpolate(entity.posX, entity.lastTickPosX, partialTicks) - mc.getRenderManager().viewerPosX;
        final double y = RenderUtils.interpolate(entity.posY, entity.lastTickPosY, partialTicks) - mc.getRenderManager().viewerPosY;
        final double z = RenderUtils.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks) - mc.getRenderManager().viewerPosZ;
        GL11.glLineWidth(4.0f);
        final ArrayList<Vec3d> posArrayList = new ArrayList<>();
        for (float rotation = 0; rotation < (3.141592f * 2.0); rotation += 3.141592f * 2.0f / 27f) {
            final Vec3d pos = new Vec3d(Main.settingsManager.getSettingByName("Distance").getValDouble() * Math.cos(rotation) + x, y, Main.settingsManager.getSettingByName("Distance").getValDouble() * Math.sin(rotation) + z);
            posArrayList.add(pos);
        }
        GL11.glEnable(GL11.GL_LINE_STIPPLE);
        GL11.glLineStipple(4, (short) 0xAAAA);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            final float r = ((float) 1 / 255) * color.getRed();
            final float g = ((float) 1 / 255) * color.getGreen();
            final float b = ((float) 1 / 255) * color.getBlue();
            for (Vec3d pos : posArrayList) {
                GL11.glColor3d(Main.moduleManager.getModule("Speed").isToggled() && posArrayList.indexOf(pos) == index ? 0.15f : r, Main.moduleManager.getModule("Speed").isToggled() && posArrayList.indexOf(pos) == index ? 0.15f : g, Main.moduleManager.getModule("Speed").isToggled() && posArrayList.indexOf(pos) == index ? 1 : b);
                GL11.glVertex3d(pos.x, pos.y, pos.z);
            }
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_STIPPLE);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glLineWidth(1);
        GL11.glPopMatrix();
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.collidedVertically) {
            direction = !direction;
        }

        Entity entity = this.getTargetEz();
        if (entity == null) {
            return;
        } else {

            if (!mc.player.onGround && mc.player.fallDistance != 0) {
                double currentSpeed = MotionUtils.getSpeed1();

                MotionUtils.setMotion(0);

                double yawChange = 90;

                if (mc.player.getDistance(entity) < Main.settingsManager.getSettingByName("Distance").getValDouble() && mc.player.getDistance(entity) > Main.settingsManager.getSettingByName("Distance").getValDouble() - 0.05) {
                    yawChange = 10;
                }

                float f = (float) ((RotationUtils.getRotations((EntityLivingBase)entity)[0] + (direction ? -yawChange : yawChange)) * 0.017453292F);
                double x2 = entity.posX, z2 = entity.posZ;
                x2 -= (double) (MathHelper.sin(f) * (Main.settingsManager.getSettingByName("Distance").getValDouble() + 2.55) * -1);
                z2 += (double) (MathHelper.cos(f) * (Main.settingsManager.getSettingByName("Distance").getValDouble() + 2.55) * -1);

                float currentSpeed1 = MotionUtils.getSpeed1();

                MotionUtils.setMotion(currentSpeed1 + Main.settingsManager.getSettingByName("Speed").getValDouble(), RotationUtils.getRotationFromPosition(x2, z2, mc.player.posY)[0]);

                if (currentSpeed > MotionUtils.getSpeed1()) {
                    direction = !direction;
                }

            }
        }

    }

    public Entity getTargetEz() {
        if (mc.player == null || mc.player.isDead) {
            return null;
        }
        List list = mc.world.loadedEntityList.stream().filter(entity -> entity != mc.player).filter(entity -> mc.player.getDistance(entity) <= Main.settingsManager.getSettingByName("Range").getValDouble()).filter(entity -> !entity.isDead).filter(this::target).sorted(Comparator.comparing(entity -> mc.player.getDistance(entity))).collect(Collectors.toList());
        if (list.size() > 0) {
            return (Entity) list.get(0);
        }
        return null;
    }

    private boolean target(Entity entity) {
        return this.attackCheckin(entity);
    }

    public boolean attackCheckin(Entity entity) {
        return entity instanceof EntityPlayer && ((EntityPlayer)entity).getHealth() > 0.0f && !FriendManager.isFriend(entity.getName()) && Math.abs(mc.player.rotationYaw - RotationUtils.getNeededRotations1((EntityLivingBase)entity)[0]) % 180.0f < 190.0f && !entity.isInvisible() && !entity.getUniqueID().equals(mc.player.getUniqueID());
    }
}