package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ESP extends Module
{
    public ESP() {
        super("ESP", Category.RENDER);
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent e) {
    }

}
