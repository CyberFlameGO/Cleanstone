package rocks.cleanstone.net.minecraft.login;

import org.springframework.context.event.EventListener;

import rocks.cleanstone.net.event.InboundPacketEvent;
import rocks.cleanstone.net.minecraft.packet.data.Chat;
import rocks.cleanstone.net.minecraft.packet.inbound.EncryptionResponsePacket;
import rocks.cleanstone.net.minecraft.packet.inbound.HandshakePacket;

public class EncryptionResponseListener {

    private final LoginManager loginManager;

    public EncryptionResponseListener(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @EventListener
    public void onReceive(InboundPacketEvent event) {
        if (event.getPacket() instanceof HandshakePacket) {
            EncryptionResponsePacket packet = (EncryptionResponsePacket) event.getPacket();

            try {
                loginManager.onEncryptionResponse(event.getConnection(), packet);
            } catch (Exception e) {
                e.printStackTrace();
                loginManager.stopLogin(event.getConnection(), new Chat("TODO: JSON reason"));
            }
        }
    }
}