package rocks.cleanstone.net.minecraft.protocol.v1_12_2.outbound;

import io.netty.buffer.ByteBuf;
import rocks.cleanstone.net.minecraft.packet.outbound.ResponsePacket;
import rocks.cleanstone.net.protocol.Codec;
import rocks.cleanstone.net.protocol.OutboundPacketCodec;
import rocks.cleanstone.net.utils.ByteBufUtils;

import java.io.IOException;

@Codec
public class ResponseCodec implements OutboundPacketCodec<ResponsePacket> {

    @Override
    public ByteBuf encode(ByteBuf byteBuf, ResponsePacket packet) throws IOException {
        ByteBufUtils.writeUTF8(byteBuf, packet.getJSONResponse());
        return byteBuf;
    }
}
