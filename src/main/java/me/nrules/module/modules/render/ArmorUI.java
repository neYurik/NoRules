package me.nrules.module.modules.render;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ArmorUI extends Module {
    public ArmorUI() {
        super("ArmorUI", Category.RENDER);
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;

        EntityPlayer player = Minecraft.getMinecraft().player;
        NonNullList<ItemStack> equipped = player.inventory.armorInventory;
        ScaledResolution sc = new ScaledResolution(mc);
        int barWidth = 60;
        int k = 0;

        if (player.isCreative())
            return;

        for (int i = 0; i < equipped.size(); i++)
        {
            ItemStack o = equipped.get(i);
            if (!(o.getItem() instanceof ItemAir))
            {
                float dura = (float) (o.getMaxDamage() - o.getItemDamage()) / o.getMaxDamage();
                int j = (int) Math.round(dura * barWidth);
                int z = (int) Math.round(255.0D - (double) o.getItemDamage() * 255.0D / (double) o.getMaxDamage());
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder vertexbuffer = tessellator.getBuffer();
                int dX = sc.getScaledWidth() / 2 + 30;
                int dY = sc.getScaledHeight() - 43 - k * 2 - (player.isInsideOfMaterial(Material.WATER) ? 10 : 0);
                draw(vertexbuffer, dX, dY, barWidth, 2, 0, 0, 0, 255);
                draw(vertexbuffer, dX, dY, barWidth, 1, (255 - z) / 4, 69, 0, 255);
                draw(vertexbuffer, barWidth - j + dX, dY, j, 1, 255 - z, z, 0, 255);
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.enableDepth();
                k++;
            }
        }
    }

    private void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue,
                      int alpha) {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double) (x + 0), (double) (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double) (x + 0), (double) (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double) (x + width), (double) (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double) (x + width), (double) (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }

}