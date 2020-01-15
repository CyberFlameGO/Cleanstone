package rocks.cleanstone.net.minecraft.protocol.v1_12_2.outbound;

import io.netty.buffer.ByteBuf;
import rocks.cleanstone.net.minecraft.packet.outbound.OutPlayerPositionAndLookPacket;
import rocks.cleanstone.net.protocol.Codec;
import rocks.cleanstone.net.protocol.OutboundPacketCodec;
import rocks.cleanstone.net.utils.ByteBufUtils;

@Codec
public class OutPlayerPositionAndLookCodec implements OutboundPacketCodec<OutPlayerPositionAndLookPacket> {

    @Override
    public ByteBuf encode(ByteBuf byteBuf, OutPlayerPositionAndLookPacket packet) {

        byteBuf.writeDouble(packet.getPosition().getX());
        byteBuf.writeDouble(packet.getPosition().getY());
        byteBuf.writeDouble(packet.getPosition().getZ());
        byteBuf.writeFloat(packet.getPosition().getRotation().getYaw());
        byteBuf.writeFloat(packet.getPosition().getRotation().getPitch());
        byteBuf.writeByte(packet.getFlags());
        ByteBufUtils.writeVarInt(byteBuf, packet.getTeleportID());

        return byteBuf;
    }
}
