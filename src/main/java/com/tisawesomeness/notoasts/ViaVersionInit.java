package com.tisawesomeness.notoasts;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;

public class ViaVersionInit {

    public ViaVersionInit() {
        var lib = Via.getManager().getProtocolManager();
        @SuppressWarnings("unchecked")
        Protocol<ClientboundPackets1_19, ClientboundPackets1_19_1, ?, ?> protocol = lib.getProtocol(
                ProtocolVersion.v1_19_1, ProtocolVersion.v1_19);
        if (protocol == null) {
            throw new RuntimeException("Could not find protocol 1.19-1.19.1");
        }
        protocol.registerClientbound(ClientboundPackets1_19.SERVER_DATA, ClientboundPackets1_19_1.SERVER_DATA,
                new ServerDataRemapper(), true);
    }

    private static class ServerDataRemapper extends PacketRemapper {
        @Override
        public void registerMap() {
            map(Type.OPTIONAL_COMPONENT); // Motd
            map(Type.OPTIONAL_STRING); // Encoded icon
            map(Type.BOOLEAN); // Previews chat
            create(Type.BOOLEAN, true); // Enforces secure chat
        }
    }

}
