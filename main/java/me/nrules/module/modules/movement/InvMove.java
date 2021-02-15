package me.nrules.module.modules.movement;

import me.nrules.GUIClick.clickgui.ClickGui;
import me.nrules.clickGUI.ClickGUIScreen;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.render.ClickGUI;
import me.nrules.util.MotionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class InvMove extends Module {
    public InvMove() {
        super("InvMove", Category.MOVEMENT);
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (!(mc.currentScreen instanceof GuiContainer)
                && !(mc.currentScreen instanceof ClickGui))
            return;

        double speed = 0.05;

        mc.player.setSprinting(false);

        if (mc.player.isInWater())
            speed /= 10;

        if (!mc.player.onGround)
            speed /= 4.0;

        handleForward(speed + 0.0060);
        handleJump();

        if (!mc.player.onGround)
            speed /= 2.0;

        handleBack(speed);
        handleLeft(speed / 4);
        handleRight(speed / 4);


        if (Keyboard.isKeyDown(Keyboard.KEY_D) && Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            speed /= 1.5;
            handleRight(speed / 1.7);
        } else {
            handleRight(speed);
        }


        if (Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            speed /= 1.5;
            handleLeft(speed / 1.7);
        } else
        {
            handleLeft(speed);
        }

    }

    void moveForward(double speed) {
        float direction = MotionUtils.getDirection();
        mc.player.motionX -= (double) (MathHelper.sin(direction) * speed);
        mc.player.motionZ += (double) (MathHelper.cos(direction) * speed);
    }

    void moveBack(double speed) {
        float direction = MotionUtils.getDirection();
        mc.player.motionX += (double) (MathHelper.sin(direction) * speed);
        mc.player.motionZ -= (double) (MathHelper.cos(direction) * speed);
    }

    void moveLeft(double speed) {
        float direction = MotionUtils.getDirection();
        mc.player.motionZ += (double) (MathHelper.sin(direction) * speed);
        mc.player.motionX += (double) (MathHelper.cos(direction) * speed);
    }

    void moveRight(double speed) {
        float direction = MotionUtils.getDirection();
        mc.player.motionZ -= (double) (MathHelper.sin(direction) * speed);
        mc.player.motionX -= (double) (MathHelper.cos(direction) * speed);
    }

    public void check()
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_W))
        {
            this.moveLeft(0.03);
            this.moveLeft(0.03);
        }
    }

    public void check1()
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_D) && Keyboard.isKeyDown(Keyboard.KEY_W))
        {
            this.moveRight(0.03);
            this.moveForward(0.03);
        }
    }



    void handleForward(double speed) {
        if(!Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()))
            return;
        moveForward(speed);
    }

    void handleBack(double speed) {
        if(!Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()))
            return;
        moveBack(speed);
    }

    void handleLeft(double speed) {
        if(!Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()) && !Keyboard.isKeyDown(Keyboard.KEY_A))
            return;
        moveLeft(speed);
    }

    void handleRight(double speed) {
        if(!Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()))
            return;
        moveRight(speed);
    }

    void handleJump() {
        if(mc.player.onGround &&
                Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()))
            mc.player.jump();
    }

}
