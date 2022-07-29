package com.tisawesomeness.notoasts;

import com.comphenix.tinyprotocol.Reflection;
import com.comphenix.tinyprotocol.Reflection.FieldAccessor;
import com.comphenix.tinyprotocol.TinyProtocol;
import io.netty.channel.Channel;
import org.bukkit.entity.Player;

public class ServerDataProtocol extends TinyProtocol {

    private final Class<?> serverDataClass = Reflection.getMinecraftClass("ClientboundServerDataPacket", "network.protocol.game");
    private final FieldAccessor<Boolean> disableToast = Reflection.getField(serverDataClass, boolean.class, 1);

    public ServerDataProtocol(NoToasts plugin) {
        super(plugin);
    }

    @Override
    public Object onPacketOutAsync(Player receiver, Channel channel, Object packet) {
        if (serverDataClass.isInstance(packet)) {
            disableToast.set(packet, true);
        }
        return super.onPacketOutAsync(receiver, channel, packet);
    }

}
