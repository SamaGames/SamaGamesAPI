package net.samagames.core.database;

public class RedisServer
{
    private final String ip;
    private final int port;
    private final String password;

    public RedisServer(String ip, int port, String password)
    {
        this.ip = ip;
        this.port = port;
        this.password = password;
    }

    public String getIp()
    {
        return this.ip;
    }

    public int getPort()
    {
        return this.port;
    }

    public String getPassword()
    {
        return this.password;
    }
}
