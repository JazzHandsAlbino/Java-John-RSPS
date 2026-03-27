package com.johnrsps.packet;

import io.netty.buffer.ByteBuf;

public class GamePacket {
    private final byte opcode;
    private final ByteBuf payload;

    public GamePacket(byte opcode, ByteBuf payload) {
        this.opcode = opcode;
        this.payload = payload;
    }

    public byte getOpcode() {
        return opcode;
    }

    public ByteBuf getPayload() {
        return payload;
    }
}