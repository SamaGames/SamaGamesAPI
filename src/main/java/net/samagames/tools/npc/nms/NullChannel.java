package net.samagames.tools.npc.nms;

import io.netty.channel.*;

import java.net.SocketAddress;

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
public class NullChannel extends AbstractChannel {

    public NullChannel() {
        super(null);
    }

    @Override
    public ChannelConfig config() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public ChannelMetadata metadata() {
        return null;
    }

    @Override
    protected void doBeginRead() throws Exception {
    }

    @Override
    protected void doBind(SocketAddress arg0) throws Exception {
    }

    @Override
    protected void doClose() throws Exception {
    }

    @Override
    protected void doDisconnect() throws Exception {
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer arg0) throws Exception {
    }

    @Override
    protected boolean isCompatible(EventLoop arg0) {
        return false;
    }

    @Override
    protected SocketAddress localAddress0() {
        return null;
    }

    @Override
    protected AbstractUnsafe newUnsafe() {
        return null;
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }

}
