package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.combat.Killaura;
import me.nrules.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class TargetHUD extends Module {
    public TargetHUD() {
        super("TargetHUD", Category.RENDER);

    }

}
