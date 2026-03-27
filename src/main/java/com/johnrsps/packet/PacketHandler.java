package com.johnrsps.packet;

import io.netty.channel.ChannelHandlerContext;

public class PacketHandler {

    public void handle(ChannelHandlerContext ctx, GamePacket packet) {
        switch (packet.getOpcode()) {
            case 0:
                // login
                break;
            case 1:
                // move
                break;
            default:
                // unknown
        }
    }
}
