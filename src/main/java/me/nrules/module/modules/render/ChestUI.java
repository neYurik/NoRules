package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class ChestUI extends Module {
    public ChestUI() {
        super("ChestUI", Category.RENDER);
        Main.settingsManager.rSetting(new Setting("Width", this, 1.5, 0.1, 5, false));
    }

    Minecraft mc = Minecraft.getMinecraft();
    RenderManager renderManager;

    @SubscribeEvent
        public void onUpdate(RenderWorldLastEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        for (final TileEntity entity : mc.world.loadedTileEntityList) {
            if (entity instanceof TileEntityChest || entity instanceof TileEntityEnderChest) {
                RenderUtils.renderOne((float) Main.settingsManager.getSettingByName("Width").getValDouble());
                TileEntityRendererDispatcher.instance.render(entity, event.getPartialTicks(), -1);
                RenderUtils.renderTwo();
                TileEntityRendererDispatcher.instance.render(entity, event.getPartialTicks(), -1);
                RenderUtils.renderThree();
                TileEntityRendererDispatcher.instance.render(entity, event.getPartialTicks(), -1);
                RenderUtils.renderFour(Color.WHITE);
                TileEntityRendererDispatcher.instance.render(entity, event.getPartialTicks(), -1);
                RenderUtils.renderFive();
                RenderUtils.setColor(Color.white);
            }
        }
    }
}
