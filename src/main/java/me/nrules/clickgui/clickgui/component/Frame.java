package me.nrules.clickgui.clickgui.component;

import me.nrules.Main;
import me.nrules.clickgui.clickgui.component.components.Button;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

//Your Imports

public class Frame {

	public ArrayList<Component> components;
	public Category category;
	private boolean open;
	private int width;
	private int y;
	private int x;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	final int[] count = {1};
	public int dragY;
	public static FontRenderer font = Minecraft.getMinecraft().fontRenderer;

	public Frame(Category cat) {
		this.components = new ArrayList<Component>();
		this.category = cat;
		this.width = 88;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = false;
		this.isDragging = false;
		int tY = this.barHeight;
		
		for(Module mod : Main.moduleManager.getModulesInCategory(category)) {
			Button modButton = new Button(mod, this, tY);
			this.components.add(modButton);
			tY += 12;
		}
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public void renderFrame(FontRenderer fontRenderer) {
		Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, Color.BLACK.getRGB());
		GL11.glPushMatrix();
		GL11.glScalef(1f,1f, 1f);
		font.drawString(this.category.name(), (this.x) + 8, (this.y + 2), -1);
		font.drawString(this.open ? "<" : ">", (this.x + this.width - 10), (this.y + 2), -1);
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.components.isEmpty()) {
				Gui.drawRect(this.x, this.y + this.barHeight, this.x + 1, this.y  + this.barHeight + (12 * components.size()), rainbow(count[0] * 190));
				Gui.drawRect(this.x, this.y + this.barHeight + (12 * components.size()), this.x + this.width, this.y + this.barHeight + (12 * components.size()) + 1, rainbow(count[0] * 190));
				Gui.drawRect(this.x + this.width, this.y + this.barHeight, this.x + this.width - 1, this.y + this.barHeight + (12 * components.size()),  rainbow(count[0] * 190));
				count[0]++;
				for(Component component : components) {
					component.renderComponent();
				}
			}
		}
	}
	
	public void refresh() {
		int off = this.barHeight;
		for(Component comp : components) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}

	public static int rainbow(int delay)
	{
		double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 80);
		rainbowState %= 360;
		return Color.getHSBColor((float) (rainbowState / 360f), 0.5f, 1f).getRGB();
	}
	
}
