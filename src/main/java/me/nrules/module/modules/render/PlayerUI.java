package me.nrules.module.modules.render;

import me.nrules.FriendManager;
import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.helper.FakeEntity;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RotationUtils;
import me.nrules.util.Vec3DUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerUI extends Module {
    public PlayerUI() {
        super("PlayerUI", Category.RENDER);
        Main.settingsManager.rSetting(new Setting("Boxes", this, false));
        Main.settingsManager.rSetting(new Setting("Shader", this, false));
        Main.settingsManager.rSetting(new Setting("Tracers", this, false));
    }

    private int playerBox;
    Minecraft mc = Minecraft.getMinecraft();
    private final ArrayList<EntityPlayer> players = new ArrayList<>();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if(mc.world == null && mc.player == null)
            return;

        EntityPlayerSP player = mc.player;
        this.players.clear();
        Stream<EntityPlayer> stream = player.world.playerEntities.parallelStream().filter(e -> (!e.isDead && e.getHealth() > 0.0F)).filter(e -> (e != player)).filter(e -> !(e instanceof FakeEntity)).filter(e -> (Math.abs(e.posY - (mc.player.posY)) <= 1000000.0D));
            stream = stream.filter(e -> !e.isPlayerSleeping());
            stream = stream.filter(e -> !e.isInvisible());
        this.players.addAll(stream.collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if(mc.world == null && mc.player == null)
            return;

        this.mc.world.loadedEntityList.stream().forEach(entity ->
        {
            if (entity != null && entity != this.mc.player)
            {

                if (Main.settingsManager.getSettingByName("Boxes").getValBoolean())
                    if (entity instanceof EntityPlayer)
                    {
                        doRenderEntity(entity);
                    }
                if (Main.settingsManager.getSettingByName("Shader").getValBoolean())
                {
                    entity.setGlowing(true);
                }
            }
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glTranslated(-TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY, -TileEntityRendererDispatcher.staticPlayerZ);
            if (Main.settingsManager.getSettingByName("Tracers").getValBoolean())
                renderLines(event.getPartialTicks());
            GL11.glColor4f(1.0F,1.0F, 1.0F, 1.0F);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
        });
    }

    private void doRenderEntity(Entity entity) {
    try {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2896);
        GL11.glLineWidth(0.05f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        int color = -1;
        float f = mc.player.getDistance((Entity)entity) / 20.0F;
        GL11.glColor4f(2.0F - f, f, 0.0F, 0.5F);
        if(FriendManager.isFriend(entity.getName())) {
            GL11.glColor4f(1, 0, 0, 0);
        }
        double data1 = TileEntityRendererDispatcher.staticPlayerX;
        double data2 = TileEntityRendererDispatcher.staticPlayerY;
        double data3 = TileEntityRendererDispatcher.staticPlayerZ;
        double sx = entity.posX - data1 - 0.5D;
        double sy = entity.posY - data2;
        double sz = entity.posZ - data3 - 0.5D;
        double x = 0.0D;
        double y = 0.0D;
        double z = 0.0D;
        GL11.glTranslated(sx, sy, sz);
        GL11.glBegin(1);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y + 2.0D, z);
        GL11.glVertex3d(x, y + 2.0D, z);
        GL11.glVertex3d(x + 1.0D, y + 2.0D, z);
        GL11.glVertex3d(x + 1.0D, y + 2.0D, z);
        GL11.glVertex3d(x + 1.0D, y, z);
        GL11.glVertex3d(x + 1.0D, y, z);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x + 1.0D, y, z);
        GL11.glVertex3d(x + 1.0D, y + 2.0D, z);
        GL11.glVertex3d(x + 1.0D, y + 2.0D, z);
        GL11.glVertex3d(x + 1.0D, y + 2.0D, z + 1.0D);
        GL11.glVertex3d(x + 1.0D, y + 2.0D, z + 1.0D);
        GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
        GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
        GL11.glVertex3d(x + 1.0D, y, z);
        GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
        GL11.glVertex3d(x + 1.0D, y + 2.0D, z + 1.0D);
        GL11.glVertex3d(x + 1.0D, y + 2.0D, z + 1.0D);
        GL11.glVertex3d(x, y + 2.0D, z + 1.0D);
        GL11.glVertex3d(x, y + 2.0D, z + 1.0D);
        GL11.glVertex3d(x, y, z + 1.0D);
        GL11.glVertex3d(x, y, z + 1.0D);
        GL11.glVertex3d(x + 1.0D, y, z + 1.0D);
        GL11.glVertex3d(x, y, z + 1.0D);
        GL11.glVertex3d(x, y + 2.0D, z + 1.0D);
        GL11.glVertex3d(x, y + 2.0D, z + 1.0D);
        GL11.glVertex3d(x, y + 2.0D, z);
        GL11.glVertex3d(x, y + 2.0D, z);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y, z + 1.0D);
        GL11.glEnd();
        GL11.glTranslated(-sx, -sy, -sz);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GL11.glDepthMask(true);
    } catch (Throwable var25) {
        var25.printStackTrace();
    }
    }
    private void renderBoxes(double partialTicks) {
        for (EntityPlayer e : this.players) {
            GL11.glPushMatrix();
            GL11.glTranslated(e.prevPosX + (e.posX - e.prevPosX) * partialTicks, e.prevPosY + (e.posY - e.prevPosY) * partialTicks, e.prevPosZ + (e.posZ - e.prevPosZ) * partialTicks);
            GL11.glScaled(e.width + 0.1D, e.height + 0.1D, e.width + 0.1D);
            float f = mc.player.getDistance((Entity)e) / 20.0F;
            GL11.glColor4f(2.0F - f, f, 0.0F, 0.5F);
            GL11.glCallList(this.playerBox);
            GL11.glPopMatrix();
        }
    }


    private void renderLines(double partialTicks) {
        Vec3d start = RotationUtils.getClientLookVec().addVector(0.0D, mc.player.getEyeHeight(), 0.0D).addVector(TileEntityRendererDispatcher.staticPlayerX, TileEntityRendererDispatcher.staticPlayerY, TileEntityRendererDispatcher.staticPlayerZ);
        GL11.glBegin(1);
        for (EntityPlayer e : this.players) {
            Vec3d end = e.getEntityBoundingBox().getCenter().subtract((new Vec3d(e.posX, e.posY, e.posZ))
                    .subtract(e.prevPosX, e.prevPosY, e.prevPosZ)
                    .scale(1.0D - partialTicks));
            float f = mc.player.getDistance((Entity)e) / 20.0F;
            int color = -1;
            float f3 = (float)(color >> 24 & 255) / 255.0F;
            float f5 = (float)(color >> 16 & 255) / 255.0F;
            float f1 = (float)(color >> 8 & 255) / 255.0F;
            float f2 = (float)(color & 255) / 255.0F;
            if (FriendManager.isFriend(e.getName()))
            {
                GL11.glColor4f(0,0,0,0);
            }else
            GL11.glColor4f(0.5f + f1, f2, f3,0.5F);
            GL11.glVertex3d(Vec3DUtil.getX(start), Vec3DUtil.getY(start),
                    Vec3DUtil.getZ(start));
            GL11.glVertex3d(Vec3DUtil.getX(end), Vec3DUtil.getY(end),
                    Vec3DUtil.getZ(end));
        }
        GL11.glEnd();
    }

    @Override
    public void onDisable() {
        this.mc.world.loadedEntityList.stream().forEach(entity ->
        {
            if (entity != null && entity != this.mc.player) {
                entity.setGlowing(false);
                super.onDisable();
            }
        });
    }
}
