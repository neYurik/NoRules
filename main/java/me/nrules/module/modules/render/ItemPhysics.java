package me.nrules.module.modules.render;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ItemPhysics extends Module {
    public ItemPhysics() {
        super("ItemPhysics", Category.RENDER);
    }
    public static Random random = new Random();
    public static Minecraft mc = Minecraft.getMinecraft();
    public static RenderItem renderItem = mc.getRenderItem();
    public static long tick;
    public static double rotation;

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event) {
        Entity par1Entity = null;
        double x = par1Entity.posX;
        double y = par1Entity.posY;
        double z = par1Entity.posZ;
        float par8 = 0;
        float par9 = 0;
        onRender(par1Entity, x, y, z, par8, par9);
    }

    public void onRender(Entity par1Entity,double x, double y, double z, float par8, float par9)
    {
            rotation = 0.5D;
            if (!mc.inGameHasFocus) {
                rotation = 0.0D;
            }
            EntityItem entityitem = (EntityItem) par1Entity;
            ItemStack itemstack = entityitem.getItem();
            if (itemstack.getItem() != null) {
                random.setSeed(187L);
                mc.getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                mc.getRenderManager().renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false,
                        false);
                GlStateManager.enableRescaleNormal();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.pushMatrix();
                IBakedModel ibakedmodel = renderItem.getItemModelMesher().getItemModel(itemstack);
                int i = func_177077_a(entityitem, x, y, z, par9, ibakedmodel);
                BlockPos blockpos = new BlockPos(entityitem);
                if (entityitem.rotationPitch > 360.0F) {
                    entityitem.rotationPitch = 1.0F;
                }
                if (!Double.isNaN(entityitem.posX) && !Double.isNaN(entityitem.posY) && !Double.isNaN(entityitem.posZ) && entityitem.world != null) {
                    if (entityitem.onGround) {
                        if ((entityitem.rotationPitch != 0.0F) && (entityitem.rotationPitch != 90.0F) &&
                                (entityitem.rotationPitch != 180.0F) && (entityitem.rotationPitch != 270.0F)) {
                            double d0 = formPositiv(entityitem.rotationPitch);
                            double d1 = formPositiv(entityitem.rotationPitch - 90.0F);
                            double d2 = formPositiv(entityitem.rotationPitch - 180.0F);
                            double d3 = formPositiv(entityitem.rotationPitch - 270.0F);
                            if ((d0 <= d1) && (d0 <= d2) && (d0 <= d3)) {
                                if (entityitem.rotationPitch < 0.0F) {
                                    entityitem.rotationPitch = ((float) (entityitem.rotationPitch + rotation));
                                } else {
                                    entityitem.rotationPitch = ((float) (entityitem.rotationPitch - rotation));
                                }
                            }
                            if ((d1 < d0) && (d1 <= d2) && (d1 <= d3)) {
                                if (entityitem.rotationPitch - 90.0F < 0.0F) {
                                    entityitem.rotationPitch = ((float) (entityitem.rotationPitch + rotation));
                                } else {
                                    entityitem.rotationPitch = ((float) (entityitem.rotationPitch - rotation));
                                }
                            }
                            if ((d2 < d1) && (d2 < d0) && (d2 <= d3)) {
                                if (entityitem.rotationPitch - 180.0F < 0.0F) {
                                    entityitem.rotationPitch = ((float) (entityitem.rotationPitch + rotation));
                                } else {
                                    entityitem.rotationPitch = ((float) (entityitem.rotationPitch - rotation));
                                }
                            }
                            if ((d3 < d1) && (d3 < d2) && (d3 < d0)) {
                                if (entityitem.rotationPitch - 270.0F < 0.0F) {
                                    entityitem.rotationPitch = ((float) (entityitem.rotationPitch + rotation));
                                } else {
                                    entityitem.rotationPitch = ((float) (entityitem.rotationPitch - rotation));
                                }
                            }
                        }
                    } else {
                        BlockPos blockpos1 = new BlockPos(entityitem);
                        blockpos1.add(0, 1, 0);
                        IBlockState iBlockState = Minecraft.getMinecraft().world.getBlockState(new BlockPos(
                                this.mc.player.posX,
                                this.mc.player.posY + 1.0E-4D,
                                this.mc.player.posZ));

                        Material material = entityitem.world.getBlockState(blockpos1).getBlock().getMaterial(iBlockState);
                        Material material1 = entityitem.world.getBlockState(blockpos).getBlock().getMaterial(iBlockState);
                        boolean flag1 = entityitem.isInsideOfMaterial(Material.WATER);
                        boolean flag2 = entityitem.isInWater();
                        if ((flag1 | material == Material.WATER | material1 == Material.WATER | flag2)) {
                            entityitem.rotationPitch = ((float) (entityitem.rotationPitch + rotation / 4.0D));
                        } else {
                            entityitem.rotationPitch = ((float) (entityitem.rotationPitch + rotation * 2.0D));
                        }
                    }
                }
                GL11.glRotatef(entityitem.rotationYaw, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(entityitem.rotationPitch + 90.0F, 1.0F, 0.0F, 0.0F);
                for (int j = 0; j < i; j++) {
                    if (ibakedmodel.isAmbientOcclusion()) {
                        GlStateManager.pushMatrix();
                        GlStateManager.scale(0.5F, 0.5F, 0.5F);
                        renderItem.renderItem(itemstack, ibakedmodel);
                        GlStateManager.popMatrix();
                    } else {
                        GlStateManager.pushMatrix();
                        if ((j > 0) && (shouldSpreadItems())) {
                            GlStateManager.translate(0.0F, 0.0F, 0.046875F * j);
                        }
                        renderItem.renderItem(itemstack, ibakedmodel);
                        if (!shouldSpreadItems()) {
                            GlStateManager.translate(0.0F, 0.0F, 0.046875F);
                        }
                        GlStateManager.popMatrix();
                    }
                }
                GlStateManager.popMatrix();
                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();
                mc.getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                mc.getRenderManager().renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
            }
        }

        public static int func_177077_a (EntityItem items,double x, double y, double z, float p_177077_8_, IBakedModel
        p_177077_9_){
            ItemStack itemstack = items.getItem();
            Item item = itemstack.getItem();
            if (item == null) {
                return 0;
            }
            boolean flag = p_177077_9_.isAmbientOcclusion();
            int i = func_177078_a(itemstack);
            float f = 0.25F;
            float f1 = 0.0F;
            GlStateManager.translate((float) x, (float) y + f1 + 0.03F, (float) z);
            float f2 = 0.0F;
            if ((flag) || ((mc.getRenderManager().options != null) && (mc.getRenderManager().options.fancyGraphics))) {
                GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
            }
            if (!flag) {
                f2 = -0.0F * (i - 1) * 0.5F;
                float f3 = -0.0F * (i - 1) * 0.5F;
                float f4 = -0.046875F * (i - 1) * 0.5F;
                GlStateManager.translate(f2, f3, f4);
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return i;
        }

        public static boolean shouldSpreadItems () {
            return true;
        }

        public static double formPositiv ( float rotationPitch){
            return rotationPitch > 0.0F ? rotationPitch : -rotationPitch;
        }

        public static int func_177078_a (ItemStack stack){
            byte b0 = 1;
            if (stack.getMaxStackSize() > 48) {
                b0 = 5;
            } else if (stack.getMaxStackSize() > 32) {
                b0 = 4;
            } else if (stack.getMaxStackSize() > 16) {
                b0 = 3;
            } else if (stack.getMaxStackSize() > 1) {
                b0 = 2;
            }
            return b0;
        }

        public static byte getMiniBlockCount (ItemStack stack,byte original){
            return original;
        }

        public static byte getMiniItemCount (ItemStack stack,byte original){
            return original;
        }
    }

