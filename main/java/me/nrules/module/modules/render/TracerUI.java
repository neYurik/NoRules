package me.nrules.module.modules.render;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TracerUI extends Module {
    public TracerUI() {
        super("TracerUI", Category.RENDER);
        Main.settingsManager.rSetting(new Setting("Float ", this, 10, 1, 100, false));
        Main.settingsManager.rSetting(new Setting("Double", this, 10, 1, 100, false));
        Main.settingsManager.rSetting(new Setting("Player", this, true));
        Main.settingsManager.rSetting(new Setting("Mob", this, false));
    }

    public static double ticks;
    public static double x, y, z;
    Minecraft mc = Minecraft.getMinecraft();


    private void player(EntityLivingBase entity) {

        render(1, 1, 1, 1, entity);
    }

    private void render(float red, float green, float blue, float alpha, EntityLivingBase entityLivingBase) {

        double renderPosX = mc.getRenderManager().viewerPosX;
        double renderPosY = mc.getRenderManager().viewerPosY;
        double renderPosZ = mc.getRenderManager().viewerPosZ;
        double xPos = (entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * ticks) - renderPosX;
        double yPos = (entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * ticks) - renderPosY;
        double zPos = (entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * ticks) - renderPosZ;
//        LogHelper.info("X:" + x + " Y:" + y + " Z:" + z);
        RenderUtils.drawTracer(xPos, yPos, zPos, 2, 1, 1, 1, 0);


    }

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event)
    {
        for (Object entityList : mc.world.loadedEntityList) {
            if (!(entityList instanceof EntityLivingBase)) {
                continue;
            }

            EntityLivingBase entity = (EntityLivingBase) entityList;

            if (Main.settingsManager.getSettingByName("Player").getValBoolean()) {
                if (entity instanceof EntityPlayer) {
                    if (entity != mc.player && !entity.isInvisible()) {
                        player(entity);

                    }
                }
            }

            if (Main.settingsManager.getSettingByName("Mob").getValBoolean()) {
                if (entity instanceof EntityMob) {
                    player(entity);
                }
            }
        }
    }
}
