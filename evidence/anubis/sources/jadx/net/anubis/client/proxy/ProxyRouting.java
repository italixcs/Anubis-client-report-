package net.anubis.client.proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.proxy.ProxyHandler;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
/* loaded from: classes-only.jar:net/anubis/client/proxy/ProxyRouting.class */
public final class ProxyRouting {
    private static final String HANDLER_NAME = "anubis_proxy";

    private ProxyRouting() {
    }

    public static ChannelHandler route(InetSocketAddress target, final ChannelHandler initializer) throws IOException, MatchException {
        if (target == null || isLocal(target)) {
            return initializer;
        }
        Proxy proxy = Proxies.get().enabledCopy();
        if (proxy == null || !proxy.isValid()) {
            return initializer;
        }
        final ProxyHandler handler = handler(proxy);
        return new ChannelInitializer<Channel>() { // from class: net.anubis.client.proxy.ProxyRouting.1
            protected void initChannel(Channel channel) {
                channel.pipeline().addFirst(ProxyRouting.HANDLER_NAME, handler);
                channel.pipeline().addLast(new ChannelHandler[]{initializer});
            }
        };
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    static ProxyHandler handler(Proxy proxy) throws MatchException {
        InetSocketAddress server = new InetSocketAddress(proxy.address.trim(), proxy.port);
        String username = (proxy.username == null || proxy.username.isEmpty()) ? null : proxy.username;
        String password = (proxy.password == null || proxy.password.isEmpty()) ? null : proxy.password;
        switch (proxy.type) {
            case SOCKS4:
                return new Socks4ProxyHandler(server, username);
            case SOCKS5:
                return new Socks5ProxyHandler(server, username, password);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    private static boolean isLocal(InetSocketAddress target) {
        InetAddress ip = target.getAddress();
        if (ip == null) {
            return "localhost".equalsIgnoreCase(target.getHostString());
        }
        if (ip.isLoopbackAddress() || ip.isAnyLocalAddress() || ip.isLinkLocalAddress() || ip.isSiteLocalAddress()) {
            return true;
        }
        byte[] raw = ip.getAddress();
        return raw.length == 16 && (raw[0] & 254) == 252;
    }
}
