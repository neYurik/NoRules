package me.nrules.module.modules.movement;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.MotionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ElytraFly extends Module {
    public ElytraFly() {
        super("ElytraFly", Category.MOVEMENT);
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.isElytraFlying()) {
            float f = MotionUtils.getDirection();
            mc.player.motionX -= (double) (MathHelper.sin(f) * 0.0069F);
            mc.player.motionY *= 0.0F;
            mc.player.motionZ += (double) (MathHelper.cos(f) * 0.0069F);

            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                mc.player.motionY += 0.3F;
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                mc.player.motionY -= 0.2F;
            }
        }
    }
}
