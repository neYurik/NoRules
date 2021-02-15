package me.nrules.helper;

import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ClickGUIScreen extends GuiScreen
{

    double scaling;
    public int currentTab;
    public Category categoryType = Category.COMBAT;

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        ScaledResolution sr;
        if (Minecraft.getMinecraft().player == null && Minecraft.getMinecraft().world == null) {
            return;
        }
        if ((this.scaling <= 1))
            this.scaling += 0.09;
        GL11.glPushMatrix();
        GL11.glTranslated(50 / 2, 1, 0);
        GL11.glScalef((float) scaling, (float) scaling, 1f);
        GL11.glTranslated(-50 / 2, -1, 0);
        drawDefaultBackground();
        Gui.drawRect(30, 23, 610, 330, 0x20000000);
        Gui.drawRect(35, 27, 200, 320, Color.black.darker().getRGB());
        Gui.drawRect(100, 27, 600, 320, Color.darkGray.darker().getRGB());
        GL11.glPopMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);

        int count = 0;
        for (Category c : Category.values())
        {
            fr.drawStringWithShadow(c.name, 50, 30 + (count * 46), Color.WHITE.getRGB());
            count++;
        }

        if (this.categoryType == Category.COMBAT)
        {
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList())
            {
                if (m.getCategory() == Category.COMBAT)
                {
                    float x = 110;
                    float y = 30 + (count1 * 15);
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;
                    fr.drawString(m.getName(), 110, 30 + (count1 * 15), hovered ? m.isToggled() ? Color.yellow.darker().getRGB() : Color.yellow.getRGB() : m.isToggled() ? Color.yellow.darker().getRGB() : -1);
                    count1++;
                }
            }
        }

        if (this.categoryType == Category.MISC)
        {
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList())
            {
                if (m.getCategory() == Category.MISC)
                {
                    float x = 110;
                    float y = 30 + (count1 * 15);
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;
                    fr.drawString(m.getName(), 110, 30 + (count1 * 15), hovered ? m.isToggled() ? Color.yellow.darker().getRGB() : Color.yellow.getRGB() : m.isToggled() ? Color.yellow.darker().getRGB() : -1);
                    count1++;
                }
            }
        }

        if (this.categoryType == Category.GHOST)
        {
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList())
            {
                if (m.getCategory() == Category.GHOST)
                {
                    float x = 110;
                    float y = 30 + (count1 * 15);
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;
                    fr.drawString(m.getName(), 110, 30 + (count1 * 15), hovered ? m.isToggled() ? Color.yellow.darker().getRGB() : Color.yellow.getRGB() : m.isToggled() ? Color.yellow.darker().getRGB() : -1);
                    count1++;
                }
            }
        }

        if (this.categoryType == Category.FUN)
        {
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList())
            {
                if (m.getCategory() == Category.FUN)
                {
                    float x = 110;
                    float y = 30 + (count1 * 15);
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;
                    fr.drawString(m.getName(), 110, 30 + (count1 * 15), hovered ? m.isToggled() ? Color.yellow.darker().getRGB() : Color.yellow.getRGB() : m.isToggled() ? Color.yellow.darker().getRGB() : -1);
                    count1++;
                }
            }
        }

        if (this.categoryType == Category.PLAYER)
        {
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList())
            {
                if (m.getCategory() == Category.PLAYER)
                {
                    float x = 110;
                    float y = 30 + (count1 * 15);
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;
                    fr.drawString(m.getName(), 110, 30 + (count1 * 15), hovered ? m.isToggled() ? Color.yellow.darker().getRGB() : Color.yellow.getRGB() : m.isToggled() ? Color.yellow.darker().getRGB() : -1);
                    count1++;
                }
            }
        }

        if (this.categoryType == Category.MOVEMENT)
        {
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList())
            {
                if (m.getCategory() == Category.MOVEMENT)
                {
                    float x = 110;
                    float y = 30 + (count1 * 15);
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;
                    fr.drawString(m.getName(), 110, 30 + (count1 * 15), hovered ? m.isToggled() ? Color.yellow.darker().getRGB() : Color.yellow.getRGB() : m.isToggled() ? Color.yellow.darker().getRGB() : -1);
                    count1++;
                }
            }
        }

        if (this.categoryType == Category.RENDER)
        {
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList())
            {
                if (m.getCategory() == Category.RENDER)
                {
                    float x = 110;
                    float y = 30 + (count1 * 15);
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;
                    fr.drawString(m.getName(), 110, 30 + (count1 * 15), hovered ? m.isToggled() ? Color.yellow.darker().getRGB() : Color.yellow.getRGB() : m.isToggled() ? Color.yellow.darker().getRGB() : -1);
                    count1++;
                }
            }
        }


    }

    @Override
    public void initGui()
    {
        scaling = 0;
        if (OpenGlHelper.shadersSupported)
        {
            Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    @Override
    public void onGuiClosed()
    {
        if (Minecraft.getMinecraft().entityRenderer.getShaderGroup() != null)
        {
            Minecraft.getMinecraft().entityRenderer.stopUseShader();
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        String[] buttons = {"COMBAT", "RENDER", "MOVEMENT", "PLAYER", "MISC", "GHOST", "FUN"};

        int count = 0;
        for (String name : buttons)
        {
            float x = 20;
            float y = 30 + (count * 30);

            if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(name) && mouseY < y + mc.fontRenderer.FONT_HEIGHT)
            {
                switch (name)
                {
                    case "COMBAT":
                        this.categoryType = Category.COMBAT;
                        break;

                    case "RENDER":
                        this.categoryType = Category.RENDER;
                        break;

                    case "MOVEMENT":
                        this.categoryType = Category.MOVEMENT;
                        break;

                    case "PLAYER":
                        this.categoryType = Category.PLAYER;
                        break;

                    case "GHOST":
                        this.categoryType = Category.GHOST;
                        break;

                    case "FUN":
                        this.categoryType = Category.FUN;
                        break;

                    case "MISC":
                        this.categoryType = Category.MISC;
                        break;
                }
            }
        }
    }
}
