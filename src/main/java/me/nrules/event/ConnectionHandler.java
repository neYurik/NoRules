package me.nrules.event;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.client.Minecraft;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.UUID;

public class ConnectionHandler {

    public ConnectionHandler(EventHandler eventHandler, NetHandlerPlayClient clientHandler) {
        if (eventHandler != null && clientHandler != null) {
            clientHandler.getNetworkManager().channel().pipeline().addBefore("packet_handler", DigestUtils.md5Hex(UUID.randomUUID().toString()), new ChannelDuplexHandler() {

                @Override
                public void channelRead(ChannelHandlerContext ctx, Object packet) {
                    try {
                        if (eventHandler.enableModule().stream().allMatch(m ->
                                m.onPacketReceive((Packet<?>) packet))) {
                            super.channelRead(ctx, packet);
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) {
                    try {
                        if (eventHandler.enableModule().stream().allMatch(m -> m.onPacketSent((Packet<?>) packet))) {
                            super.write(ctx, packet, promise);
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}

