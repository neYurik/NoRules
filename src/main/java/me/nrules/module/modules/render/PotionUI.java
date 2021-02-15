package me.nrules.module.modules.render;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collection;
import java.util.List;

public class PotionUI extends Module
{
    public PotionUI() {
        super("PotionUI", Category.RENDER);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event)
    {

        if (Minecraft.getMinecraft().player == null && Minecraft.getMinecraft().world == null)
            return;

        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = new ScaledResolution(mc);
        if (mc.inGameHasFocus)
        {
            if (mc.player == null && mc.world == null)
                return;

            int i = 0;
            Collection<PotionEffect> collection = mc.player.getActivePotionEffects();
            List<PotionEffect> sorted = Lists.reverse(Ordering.natural().sortedCopy(collection));
            for (PotionEffect potioneffect : sorted) {
                Potion potion = potioneffect.getPotion();
                ++i;
                String s1 = I18n.format(potion.getName(), new Object[0]);
                if (potioneffect.getAmplifier() == 1) {
                    s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
                } else if (potioneffect.getAmplifier() == 2) {
                    s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
                } else if (potioneffect.getAmplifier() == 3) {
                    s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
                }
                s1 = s1 + " \u00a7f" + Potion.getPotionDurationString(potioneffect, 1.0F);
                mc.fontRenderer.drawStringWithShadow(s1,
                        res.getScaledWidth() - mc.fontRenderer.getStringWidth(s1) - 7,
                        res.getScaledHeight() - 8 * i - 4, potion.getLiquidColor());
            }
        }
    }
}
