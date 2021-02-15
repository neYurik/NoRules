package me.nrules.module.modules.movement;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.MotionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;

public class Speed extends Module {
    public Speed() {
        super("Speed", Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("WellMore", this, false));
        Main.settingsManager.rSetting(new Setting("SunRise", this, false));
        //Main.settingsManager.rSetting(new Setting("MST", this, false));
    }

    KeyBinding keyBinding;
    public double moveSpeed;
    Minecraft mc = Minecraft.getMinecraft();
    EntityLivingBase target;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;


        if (Main.settingsManager.getSettingByName("SunRise").getValBoolean()) {
            SunRise();
        }
        if (Main.settingsManager.getSettingByName("WellMore").getValBoolean()) {
            WellMore();
        }
    }

    public void WellMore()
    {

        boolean boost = Math.abs(mc.player.rotationYawHead - mc.player.rotationYaw) < 90;

        if (mc.player.moveForward > 0 && mc.player.hurtTime < 5) {
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
                if (mc.player.ticksExisted % 8 == 0 && mc.player.onGround) {
//            mc.player.jump();
                    float f = MotionUtils.getDirection();
                    mc.player.motionX -= (double) (MathHelper.sin(f) * 0.0049F);
                    mc.player.motionZ += (double) (MathHelper.cos(f) * 0.0049F);
                } else {
                    double currentSpeed = Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
                    double direction = MotionUtils.getDirection();

                    double speed = boost ? 1.0064 : 1.001;

                    mc.player.motionX = -Math.sin(direction) * speed * currentSpeed;
                    mc.player.motionZ = Math.cos(direction) * speed * currentSpeed;
                }
            }
        }
    }

    public void SunRise() {
        if (mc.player.fallDistance > 0.50) {
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                mc.player.isAutoJumpEnabled();
                mc.player.motionX *= 1.1800;
                mc.player.motionZ *= 1.1800;
            }

            if (mc.player.fallDistance > 1.50) {
                if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                    mc.player.motionX *= 0.8;
                    mc.player.motionZ *= 0.8;
                }

            }
        }


    }

    public void MST()
    {

    }

}