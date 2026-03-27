package com.johnrsps.network;

import com.johnrsps.game.Player;
import com.johnrsps.game.World;
import com.johnrsps.packet.GamePacket;
import com.johnrsps.packet.PacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameServerHandler extends ChannelInboundHandlerAdapter {
    private final PacketHandler packetHandler = new PacketHandler();
    private Player player;
    private boolean loggedIn = false;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof GamePacket) {
            GamePacket packet = (GamePacket) msg;
            if (!loggedIn) {
                // Minimal fake login: accept anything
                String fakeUser = "TestPlayer";
                player = new Player(fakeUser);
                World.addPlayer(player);
                loggedIn = true;
                // Respond with a simple login success message
                ByteBuf buf = ctx.alloc().buffer(1);
                buf.writeByte(2); // pretend opcode 2 = success
                ctx.writeAndFlush(buf);
                return;
            }
            packetHandler.handle(ctx, packet);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (player != null) {
            World.removePlayer(player);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}