package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AutoMine extends Module {
    public AutoMine() {
        super("AutoMine", Category.PLAYER);
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;

        int key = mc.gameSettings.keyBindAttack.getKeyCode();
        KeyBinding.setKeyBindState(key, true);
        if (Mouse.isButtonDown(0))
        {
            int key1 = mc.gameSettings.keyBindAttack.getKeyCode();
            KeyBinding.setKeyBindState(key1, false);
        }
    }

    @Override
    public void onDisable() {
        int key = mc.gameSettings.keyBindAttack.getKeyCode();
        KeyBinding.setKeyBindState(key, false);
        super.onDisable();
    }
}