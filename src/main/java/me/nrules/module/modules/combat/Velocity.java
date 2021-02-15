package me.nrules.module.modules.combat;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.event.Connection;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
                this.motionX = mc.player.motionX;
                this.motionZ = mc.player.motionZ;
            } else if (mc.player.hurtTime == 8) {
                mc.player.motionX = -this.motionX;
                mc.player.motionZ = -this.motionZ;
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
                mc.player.motionX *= horizontal / 100;
                mc.player.motionY *= vertical / 100;
                mc.player.motionZ *= horizontal / 100;
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