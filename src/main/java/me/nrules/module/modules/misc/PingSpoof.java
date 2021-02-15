package me.nrules.module.modules.misc;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.event.Connection;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.network.play.client.CPacketKeepAlive;

import java.time.Duration;
import java.time.Instant;


public class PingSpoof extends Module {
    public PingSpoof() {
        super("PingSpoof", Category.MISC);
        Main.settingsManager.rSetting(new Setting("Delay", this, 20, 1.0D, 300, true));
    }
    Instant lastSent = Instant.now();
    // @Override
    //     public boolean c(Object packet, Connection.Side side) {
    //         if (packet instanceof CPacketKeepAlive) {
    //             final CPacketKeepAlive check = (CPacketKeepAlive)packet;
    //             check.key = 17000;
    //         }
    //        return true;
    //   }


    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.OUT)
            if (packet instanceof CPacketKeepAlive && this.isToggled()) {
                Instant now = Instant.now();
                if ((float) Duration.between(this.lastSent, now).getSeconds() >= Main.settingsManager.getSettingByName("Delay").getValDouble() / 10.0f) {
                    this.lastSent = Instant.now();
                    return false;
                }
            }
        return true;
    }
}
