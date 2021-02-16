package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoShift extends Module {
    public AutoShift() {
        super("AutoShift", Category.PLAYER);
    }

    TimerHelper timerHelper = new TimerHelper();
    MovementInput movementInput = new MovementInput();

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (Mouse.isButtonDown(0)) {
            if (!mc.player.onGround) {
                int key = mc.gameSettings.keyBindSneak.getKeyCode();
                KeyBinding.setKeyBindState(key, true);

            }
        }

        if (mc.player.fallDistance > 0.2f) {
            int key = mc.gameSettings.keyBindSneak.getKeyCode();
            KeyBinding.setKeyBindState(key, false);
        }

        if (mc.player.onGround) {
            int key = mc.gameSettings.keyBindSneak.getKeyCode();
            KeyBinding.setKeyBindState(key, false);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
            int key = mc.gameSettings.keyBindSneak.getKeyCode();
            KeyBinding.setKeyBindState(key, true);
        }
    }
}
