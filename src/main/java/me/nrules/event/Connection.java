package me.nrules.event;

import io.netty.channel.*;
import net.minecraft.client.Minecraft;

public class Connection extends ChannelDuplexHandler {


    public static enum Side
    {
        IN, OUT;
    }

    Minecraft mc = Minecraft.getMinecraft();
    private final EventHandler eventHandler;

    public Connection(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
        EventRegister.register(eventHandler);
        try
        {
            ChannelPipeline pipeline = mc.getConnection().getNetworkManager().channel().pipeline();
            pipeline.addBefore("packet_handler", "PacketHandler",  (ChannelHandler)this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception
        {
            if (!eventHandler.onPacket(packet, Connection.Side.IN)) return;
            super.channelRead(ctx, packet);
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception
        {
            if (!eventHandler.onPacket(packet, Side.OUT)) return;
            super.write(ctx, packet, promise);
        }
    }