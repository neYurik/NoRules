package me.nrules.module.modules.combat;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.event.Connection;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.MotionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.swing.text.html.parser.Entity;


public class Velocity extends Module {
    public Velocity() {
        super("Velocity", Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("Null", this, false));
        Main.settingsManager.rSetting(new Setting("Matrix", this, false));
        Main.settingsManager.rSetting(new Setting("Cancel", this, false));
        Main.settingsManager.rSetting(new Setting("Custom", this, true));
        Main.settingsManager.rSetting(new Setting("Horizontal", this, 90, 0, 100, false));
        Main.settingsManager.rSetting(new Setting("Vertical", this, 100, 0, 100, false));
    }

    Minecraft mc = Minecraft.getMinecraft();
    Entity entity;

    private double motionX;
    private double motionZ;

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {

        if (mc.player == null && mc.world == null)
            return;

        if (Main.settingsManager.getSettingByName("Matrix").getValBoolean()) {

            if (mc.player == null && mc.world == null)
                return;

            if (mc.player.hurtTime == 9) {
                this.motionX = this.mc.player.motionX;
                this.motionZ = this.mc.player.motionZ;
            } else if (this.mc.player.hurtTime == 8) {
                this.mc.player.motionX = -this.motionX * 1D;
                this.mc.player.motionZ = -this.motionZ * 1D;
                mc.player.motionY = -0.003f;
            }
        }

        if (Main.settingsManager.getSettingByName("Null").getValBoolean()) {
            if (mc.player == null && mc.world == null)
                return;

            if (mc.player.hurtTime > 0) {
                mc.player.motionX = 0;
                mc.player.motionZ = 0;
            }
        }

        if (Main.settingsManager.getSettingByName("Custom").getValBoolean()) {
            if (mc.player == null && mc.world == null)
                return;

            float horizontal = (float) Main.settingsManager.getSettingByName("Horizontal").getValDouble();
            float vertical = (float) Main.settingsManager.getSettingByName("Vertical").getValDouble();

            if (mc.player.hurtTime == mc.player.maxHurtTime && mc.player.maxHurtTime > 0) {
                mc.player.motionX *= (float) horizontal / 100;
                mc.player.motionY *= (float) vertical / 100;
                mc.player.motionZ *= (float) horizontal / 100;
            }
        }

    }


    @Override
    public boolean onPacket(Object packet, Connection.Side side)
    {
        if (packet instanceof SPacketEntityVelocity && Main.settingsManager.getSettingByName("Cancel").getValBoolean())
            return mc.player.getEntityId() != ((SPacketEntityVelocity)packet).getEntityID();
        
        return true;
    }
}