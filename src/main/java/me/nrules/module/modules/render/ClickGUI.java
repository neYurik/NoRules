package me.nrules.module.modules.render;

import me.nrules.GUIClick.clickgui.ClickGui;
import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.clickGUI.ClickGUIScreen;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;


public class ClickGUI extends Module
{


    private ClickGui clickGUIScreen;

    public ClickGUI()
    {
        super("ClickGUI", Category.RENDER);
        this.setKey(Keyboard.KEY_RSHIFT);
    }

    public ClickGui getClickGUIScreen() {
        return this.clickGUIScreen;
    }

    public void setClickGUIScreen(ClickGui clickGui) {
        this.clickGUIScreen = clickGui;
    }


    Minecraft mc = Minecraft.getMinecraft();

    public void onEnable()
    {
        mc.displayGuiScreen(getClickGUIScreen());
        toggle();
        super.onEnable();
    }
}