package me.nrules.module.modules.ghost;

import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SelfDestruct extends Module {
    public SelfDestruct() {
        super("SelfDestruct", Category.GHOST);
    }

    Minecraft mc = Minecraft.getMinecraft();

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
