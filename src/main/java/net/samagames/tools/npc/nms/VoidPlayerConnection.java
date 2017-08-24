package net.samagames.tools.npc.nms;

import net.minecraft.server.v1_12_R1.*;
import net.samagames.tools.Reflection;
import org.bukkit.Location;

import java.lang.reflect.Field;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class VoidPlayerConnection extends PlayerConnection
{
    public VoidPlayerConnection(MinecraftServer minecraftserver, EntityPlayer entityplayer)
    {
        super(minecraftserver, new NPCNetworkManager(), entityplayer);
    }

    @Override
    public void e() {

    }

    @Override
    public void disconnect(String s) {

    }

    @Override
    public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) {
    }

    @Override
    public void a(PacketPlayInFlying packetplayinflying) {

    }

    @Override
    public void a(double d0, double d1, double d2, float f, float f1) {
    }

    @Override
    public void teleport(Location dest) {

    }

    @Override
    public void a(PacketPlayInBlockDig packetplayinblockdig) {
    }

    @Override
    public void a(PacketPlayInBlockPlace packetplayinblockplace) {
    }

    @Override
    public void a(PacketPlayInSpectate packetplayinspectate) {

    }

    @Override
    public void a(PacketPlayInResourcePackStatus packetplayinresourcepackstatus) {
    }

    @Override
    public void a(IChatBaseComponent ichatbasecomponent) {

    }

    @Override
    public void sendPacket(final Packet packet) {
    }

    @Override
    public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {

    }

    @Override
    public void a(PacketPlayInChat packetplayinchat) {

    }

    @Override
    public void chat(String s, boolean async) {
    }

    @Override
    public void a(PacketPlayInArmAnimation packetplayinarmanimation) {

    }

    @Override
    public void a(PacketPlayInEntityAction packetplayinentityaction) {

    }

    @Override
    public void a(PacketPlayInUseEntity packetplayinuseentity) {
    }

    @Override
    public void a(PacketPlayInClientCommand packetplayinclientcommand) {

    }

    @Override
    public void a(PacketPlayInCloseWindow packetplayinclosewindow) {
    }

    @Override
    public void a(PacketPlayInWindowClick packetplayinwindowclick) {
    }

    @Override
    public void a(PacketPlayInEnchantItem packetplayinenchantitem) {

    }

    @Override
    public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {
    }

    @Override
    public void a(PacketPlayInTransaction packetplayintransaction) {
    }

    @Override
    public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
    }

    @Override
    public void a(PacketPlayInKeepAlive packetplayinkeepalive) {

    }

    @Override
    public void a(PacketPlayInAbilities packetplayinabilities) {

    }

    @Override
    public void a(PacketPlayInTabComplete packetplayintabcomplete) {
    }

    @Override
    public void a(PacketPlayInSettings packetplayinsettings) {
    }

    @Override
    public void a(PacketPlayInCustomPayload packetplayincustompayload) {

    }

    public static class NPCNetworkManager extends NetworkManager
    {
        public NPCNetworkManager()
        {
            super(EnumProtocolDirection.CLIENTBOUND); //MCP = isClientSide ---- SRG=field_150747_h

            Field channel = Reflection.makeField(NetworkManager.class, "channel"); //MCP = channel ---- SRG=field_150746_k
            Field address = Reflection.makeField(NetworkManager.class, "l"); //MCP = address ---- SRG=field_77527_e

            Reflection.setField(channel, this, new NullChannel());
            Reflection.setField(address, this, new NullSocketAddress());
        }
    }
}
