package me.nrules.module;

import me.nrules.event.Connection;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;

public class Module {

    public String name;
    private int key;
    private Category category;
    private String mode = "";
    public boolean toggled;
    private int color;
    private long lastEnableTime;
    private long lastDisableTime;


    public Module(String name, Category category) {
        super();
        this.name = name;
        this.category = category;
        this.key = 0;
        this.toggled = false;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;

        if(this.toggled) {
            this.onEnable();
        }else {
            this.onDisable();
        }
    }

    public String getMode()
    {
        return this.mode;
    }

    public double offset()
    {
        return (double)Math.abs(MathHelper.clamp((float)(Minecraft.getSystemTime() - (this.toggled ? this.lastEnableTime : this.lastDisableTime)), -300.0F, 300.0F) / 300.0F);
    }

    public void toggle() {
        this.toggled = !this.toggled;

        if(this.toggled) {
            this.onEnable();
        }else {
            this.onDisable();
        }
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public String getName() {
        return this.name;
    }

    public Category category() {
        return  this.category;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean onPacket(Object packet, Connection.Side side) { return true;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(final int color) {
        this.color = color;
    }


}
