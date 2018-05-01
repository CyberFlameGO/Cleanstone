package rocks.cleanstone.net.minecraft.protocol;

import com.google.common.base.Preconditions;

import rocks.cleanstone.net.Connection;
import rocks.cleanstone.net.minecraft.packet.MinecraftInboundPacketType;
import rocks.cleanstone.net.minecraft.packet.MinecraftOutboundPacketType;
import rocks.cleanstone.net.minecraft.protocol.v1_12_2.MinecraftProtocolLayer_v1_12_2;
import rocks.cleanstone.net.packet.PacketType;
import rocks.cleanstone.net.packet.PacketTypeRegistry;
import rocks.cleanstone.net.packet.SimplePacketTypeRegistry;
import rocks.cleanstone.net.packet.protocol.ClientProtocolLayer;
import rocks.cleanstone.net.packet.protocol.LayeredProtocol;
import rocks.cleanstone.net.packet.protocol.ProtocolState;

public class SimpleMinecraftProtocol extends LayeredProtocol {

    private PacketTypeRegistry registry;

    public void init() {
        registry = new SimplePacketTypeRegistry();
        registry.registerPacketType(MinecraftOutboundPacketType.values());
        registry.registerPacketType(MinecraftInboundPacketType.values());
        registerProtocolLayer(new MinecraftProtocolLayer_v1_12_2());
    }

    @Override
    public PacketTypeRegistry getPacketTypeRegistry() {
        return registry;
    }

    @Override
    public int translateInboundPacketID(int clientPacketID, Connection connection) {
        MinecraftServerProtocolLayer layer = ((MinecraftServerProtocolLayer) getServerLayerFromClientLayer
                (connection.getClientProtocolLayer()));
        Preconditions.checkNotNull(layer, "Cannot find ServerLayer by ClientLayer "
                + connection.getClientProtocolLayer().toString());
        PacketType packetType = layer.getPacketType(clientPacketID, connection.getProtocolState());
        Preconditions.checkNotNull(packetType, "Cannot find packetType by clientPacketID " + clientPacketID +
                " and protocolState " + connection.getProtocolState());
        return packetType.getTypeID();
    }

    @Override
    public int translateOutboundPacketID(int serverPacketID, Connection connection) {
        return ((MinecraftServerProtocolLayer) getServerLayerFromClientLayer(connection.getClientProtocolLayer()))
                .getProtocolPacketID(MinecraftOutboundPacketType.byTypeID(serverPacketID));
    }

    @Override
    public ClientProtocolLayer getDefaultClientLayer() {
        return MinecraftClientProtocolLayer.MINECRAFT_V1_12_2;
    }

    @Override
    public ProtocolState getDefaultState() {
        return VanillaProtocolState.HANDSHAKE;
    }
}