package net.samagames.api.games;

public abstract class GameHook
{
    public enum Type { START, DISCONNECT, RECONNECT, END }

    private final Type type;

    public GameHook(Type type)
    {
        this.type = type;
    }

    public abstract void run(Game game, Object... arguments);

    public Type getType()
    {
        return this.type;
    }
}
