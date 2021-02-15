package me.nrules.module.modules.ghost;

import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;

public class SelfDestruct extends Module {
    public SelfDestruct() {
        super("SelfDestruct", Category.GHOST);
    }

    public void onEnable()
    {
        for (Module module : Main.moduleManager.getModuleList())
        {
            if (module.isToggled())
            {
                module.toggle();
            }
            module.setKey(0);
        }
        mc.displayGuiScreen(null);
    }

}
