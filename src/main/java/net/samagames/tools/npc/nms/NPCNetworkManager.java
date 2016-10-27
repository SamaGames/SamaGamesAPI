package net.samagames.tools.npc.nms;

/**
 * Created by Silva on 19/12/2015.
 */

import net.minecraft.server.v1_10_R1.EnumProtocolDirection;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.samagames.tools.Reflection;

import java.lang.reflect.Field;

public class NPCNetworkManager extends NetworkManager {

    public NPCNetworkManager() {
        super(EnumProtocolDirection.CLIENTBOUND); //MCP = isClientSide ---- SRG=field_150747_h
        Field channel = Reflection.makeField(NetworkManager.class, "channel"); //MCP = channel ---- SRG=field_150746_k
        Field address = Reflection.makeField(NetworkManager.class, "l"); //MCP = address ---- SRG=field_77527_e

        Reflection.setField(channel, this, new NullChannel());
        Reflection.setField(address, this, new NullSocketAddress());

    }

}