package com.tisawesomeness.notoasts;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;

public class ViaVersionInit {

    private static final int SERVER_DATA_1_19 = 0x3F;
    private static final int SERVER_DATA_1_19_1 = 0x42;

    public ViaVersionInit() {
        var lib = Via.getManager().getProtocolManager();
        Protocol<?, ?, ?, ?> protocol = lib.getProtocol(
                ProtocolVersion.v1_19_1, ProtocolVersion.v1_19);
        if (protocol == null) {
            throw new RuntimeException("Could not find protocol 1.19-1.19.1");
        }
        protocol.registerClientbound(State.PLAY, SERVER_DATA_1_19, SERVER_DATA_1_19_1,
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
