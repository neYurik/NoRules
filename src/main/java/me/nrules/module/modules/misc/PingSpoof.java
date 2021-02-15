package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;


public class PingSpoof extends Module {
    public PingSpoof() {
        super("PingSpoof", Category.MISC);
    }

    // @Override
    // public boolean onPacket(Object packet, Connection.Side side) {
        // if (packet instanceof CPacketKeepAlive) {
        //     final CPacketKeepAlive check = (CPacketKeepAlive)packet;
        //     check.key = 17000;
        // }
        // return true;
    // }
}
