package me.nrules.clickgui.clickgui;

import me.nrules.clickgui.clickgui.component.Component;
import me.nrules.clickgui.clickgui.component.Frame;
import me.nrules.clickgui.settings.SnowSetting;
import me.nrules.module.Category;
import me.nrules.util.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class ClickGui extends GuiScreen {

	public static ArrayList<Frame> frames;
	public static int color = -1;
	double scaling;
	int ms;
	Random random = new Random();
	TimerHelper timerHelper;
	public static FontRenderer font = Minecraft.getMinecraft().fontRenderer;
	private ArrayList<SnowSetting> _snowList = new ArrayList<SnowSetting>();

	public ClickGui() {
		this.frames = new ArrayList<Frame>();
		int frameX = 5;
		for(Category category : Category.values()) {
			Frame frame = new Frame(category);
			frame.setX(frameX);
			frames.add(frame);
			frameX += frame.getWidth() + 1;
		}
	}
	
	@Override
	public void initGui() {
		scaling = 0;
		if (OpenGlHelper.shadersSupported) {
			Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		final ScaledResolution res = new ScaledResolution(mc);

		for(Frame frame : frames) {
			if((this.scaling <= 1))
				this.scaling += 0.009;
			GL11.glPushMatrix();
			GL11.glTranslated(width / 100, height / 100, 0);
			GL11.glScalef((float)scaling, (float)scaling, 1f);
			GL11.glTranslated(-width / 100, -height / 100, 0);
			frame.renderFrame(font);
			frame.updatePosition(mouseX, mouseY);
			//this.doRender();
			GL11.glPopMatrix();
			for(Component comp : frame.getComponents()) {
				comp.updateComponent(mouseX, mouseY);
			}

			String s = "Version: 2.2";
			int i = 0;

			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(s,
					res.getScaledWidth() - mc.fontRenderer.getStringWidth(s) - 886,
					res.getScaledHeight() - 8 * i - 12, Color.white.darker().getRGB());

		}
	}
	
	@Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		for(Frame frame : frames) {
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
				frame.setDrag(true);
				frame.dragX = mouseX - frame.getX();
				frame.dragY = mouseY - frame.getY();
			}
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
				frame.setOpen(!frame.isOpen());
			}
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		for(Frame frame : frames) {
			if(frame.isOpen() && keyCode != 1) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.keyTyped(typedChar, keyCode);
					}
				}
			}
		}
		if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
	}

	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
		for(Frame frame : frames) {
			frame.setDrag(false);
		}
		for(Frame frame : frames) {
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseReleased(mouseX, mouseY, state);
					}
				}
			}
		}
	}

	@Override
	public void onGuiClosed() {
		if (this.mc.entityRenderer.getShaderGroup() != null) {
			mc.entityRenderer.stopUseShader();
		}
		super.onGuiClosed();

	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
}
