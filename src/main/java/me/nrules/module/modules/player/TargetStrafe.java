package me.nrules.module.modules.player;

import me.nrules.FriendManager;
import me.nrules.clickgui.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RenderUtils;
import me.nrules.util.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
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

public class TargetStrafe extends Module {
    public TargetStrafe() {
        super("TargetStrafe", Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("Speed", this, 0.2, 0, 0.5, false));
        Main.settingsManager.rSetting(new Setting("Distance", this, 3, 0.1, 6, false));
        Main.settingsManager.rSetting(new Setting("Range", this, 7, 0.1, 15, false));
        Main.settingsManager.rSetting(new Setting("DamageBoost", this, 1.5, 1, 5, false));
        Main.settingsManager.rSetting(new Setting("AutoJump", this, true));
    }

    public static MovementInput movementInput;
    public static EntityLivingBase target;
    public static int direction = -1;
    public static Minecraft mc = Minecraft.getMinecraft();
    public static int index;

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

    public double getMovementSpeed() {
        double d = 0.2873;
        if (Minecraft.getMinecraft().player.isPotionActive((Potion.getPotionById(1)))) {
            int n = Minecraft.getMinecraft().player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            d *= 1.0 + 0.2 * (double) (n + 1);
        }
        return d;
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

    public final boolean doStrafeAtSpeed(double d) {
        boolean bl = true;
        Entity entity = this.getTargetEz();
        if (entity != null) {
            if (mc.player.onGround && Main.settingsManager.getSettingByName("AutoJump").getValBoolean() && mc.player.ticksExisted % 2 == 0) {
                mc.player.jump();
                mc.player.motionY = 0.39f;
            }
            float[] arrf = RotationUtils.getNeededRotations1((EntityLivingBase) entity);
            if ((double) Minecraft.getMinecraft().player.getDistance(entity) <= Main.settingsManager.getSettingByName("Distance").getValDouble()) {
                mc.player.renderYawOffset = arrf[0];
                mc.player.rotationYawHead = arrf[0];
                setSpeed((d - 0.22) + Main.settingsManager.getSettingByName("Speed").getValDouble(), arrf[0], direction, 0.0);
            } else {
                setSpeed((d - 0.22) + Main.settingsManager.getSettingByName("Speed").getValDouble(), arrf[0], direction, 1.0);
            }
        }
        return bl;
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.collidedHorizontally && mc.player.ticksExisted % 4 == 0) {
            invertStrafe();
        }
        mc.player.movementInput.moveForward = 0.0f;
        double d = this.getMovementSpeed();
        doStrafeAtSpeed(d);
    }

    public static void setSpeed(double d, float f, double d2, double d3) {
        double d4 = d3;
        double d5 = d2;
        float f2 = f;
        if (d4 == 0.0 && d5 == 0.0) {
            mc.player.motionZ = 0.0;
            mc.player.motionX = 0.0;
        } else {
            if (d4 != 0.0) {
                if (d5 > 0.0) {
                    f2 += (float) (d4 > 0.0 ? -45 : 45);
                } else if (d5 < 0.0) {
                    f2 += (float) (d4 > 0.0 ? 45 : -45);
                }
                d5 = 0.0;
                if (d4 > 0.0) {
                    d4 = 1.0;
                } else if (d4 < 0.0) {
                    d4 = -1.0;
                }
            }
            double d6 = Math.cos(Math.toRadians(f2 + 90.0f));
            double d7 = Math.sin(Math.toRadians(f2 + 90.0f));
            mc.player.motionX = d4 * d * d6 + d5 * d * d7;
            mc.player.motionZ = d4 * d * d7 - d5 * d * d6;

            if (mc.player.hurtTime > 0 && mc.player.isHandActive())
            {
                if (mc.player.onGround)
                {
                    mc.player.jump();
                    mc.player.motionY = 0.32f;
                }

                mc.player.motionX = (d4 * d * d6 + d5 * d * d7) * Main.settingsManager.getSettingByName("DamageBoost").getValDouble();
                mc.player.motionZ = (d4 * d * d7 - d5 * d * d6) * Main.settingsManager.getSettingByName("DamageBoost").getValDouble();
            }
        }
    }

    private boolean target(Entity entity) {
        return this.attackCheckin(entity);
    }

    private void invertStrafe() {
        direction = -direction;
    }

    public boolean attackCheckin(Entity entity) {
        return entity instanceof EntityPlayer && ((EntityPlayer)entity).getHealth() > 0.0f && !FriendManager.isFriend(entity.getName()) && Math.abs(mc.player.rotationYaw - RotationUtils.getNeededRotations1((EntityLivingBase)entity)[0]) % 180.0f < 190.0f && !entity.isInvisible() && !entity.getUniqueID().equals(mc.player.getUniqueID());
    }
}