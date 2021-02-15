package me.nrules.module.modules.misc;

import me.nrules.event.Connection;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.server.SPacketPong;


public class PingSpoof extends Module {
    public PingSpoof() {
        super("PingSpoof", Category.MISC);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (packet instanceof CPacketKeepAlive) {
            final CPacketKeepAlive check = (CPacketKeepAlive)packet;
            check.key = 17000;
        }
        return true;
    }
}
