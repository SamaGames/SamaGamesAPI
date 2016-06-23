package net.samagames.tools.teamspeak;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.pubsub.IPacketsReceiver;
import org.apache.commons.lang3.tuple.MutablePair;
import redis.clients.jedis.Jedis;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by Rigner for project SamaGamesAPI.
 */
@SuppressWarnings("ALL")
public class TeamSpeakAPI
{
    private static final int TIMEOUT = 20000;
    private static int generator = 0;
    private static Map<Integer, MutablePair<ResultType, Object>> results = new HashMap<>();

    private TeamSpeakAPI()
    {
    }

    static
    {
        SamaGamesAPI.get().getPubSub().subscribe("tsbotresponse", new TeamSpeakConsumer());
    }

    private static void publish(int id, String string)
    {
        Jedis jedis = null;
        try
        {
            jedis = SamaGamesAPI.get().getBungeeResource();
            if (jedis != null)
                jedis.publish("tsbot", SamaGamesAPI.get().getServerName() + "/" + id + ":" + string);
        }
        catch (Exception exception)
        {
            SamaGamesAPI.get().getPlugin().getLogger().log(Level.SEVERE, "Jedis error", exception);
        }
        finally
        {
            if (jedis != null)
                jedis.close();
        }
    }

    public static int createChannel(@Nonnull String name, @Nullable Map<ChannelProperty, String> channelProperties, @Nullable Map<ChannelPermission, Integer> permissions)
    {
        MutablePair<ResultType, Object> pair = MutablePair.of(ResultType.INTEGER, -1);
        int id = generator++;
        TeamSpeakAPI.results.put(id, pair);
        final String[] msg = {"createchannel:" + name};
        if (channelProperties != null)
            channelProperties.forEach(((channelProperty, s) -> msg[0] += ":" + channelProperties.toString().toUpperCase() + "=" + s));
        if (permissions != null)
            permissions.forEach(((channelPermission, integer) -> msg[0] += ":" + channelPermission.toString().toLowerCase() + "-" + integer));
        TeamSpeakAPI.publish(id, msg[0]);
        try
        {
            synchronized (pair)
            {
                pair.wait(TeamSpeakAPI.TIMEOUT);
            }
        } catch (Exception ignored) {}
        return (int)pair.getRight();
    }

    public static boolean deleteChannel(int channelId)
    {
        MutablePair<ResultType, Object> pair = MutablePair.of(ResultType.BOOLEAN, false);
        int id = generator++;
        TeamSpeakAPI.results.put(id, pair);
        TeamSpeakAPI.publish(id, "deletechannel:" + channelId);
        try
        {
           synchronized (pair)
           {
               pair.wait(TeamSpeakAPI.TIMEOUT);
           }
        } catch (Exception ignored) {}
        return (boolean)pair.getRight();
    }

    public static List<UUID> movePlayers(@Nonnull List<UUID> uuids, int channelId)
    {
        MutablePair<ResultType, Object> pair = MutablePair.of(ResultType.UUID_LIST, new ArrayList<>());
        int id = generator++;
        TeamSpeakAPI.results.put(id, pair);
        final String[] msg = {"move:" + channelId};
        uuids.forEach(uuid -> msg[0] += ":" + uuid);
        TeamSpeakAPI.publish(id, msg[0]);
        try
        {
            synchronized (pair)
            {
                pair.wait(TeamSpeakAPI.TIMEOUT);
            }
        } catch (Exception ignored) {}
        return (List<UUID>) pair.getRight();
    }

    public static boolean isLinked(@Nonnull UUID uuid)
    {
        MutablePair<ResultType, Object> pair = MutablePair.of(ResultType.BOOLEAN, false);
        int id = generator++;
        TeamSpeakAPI.results.put(id, pair);
        TeamSpeakAPI.publish(id, "linked:" + uuid);
        try
        {
            synchronized (pair)
            {
                pair.wait(TeamSpeakAPI.TIMEOUT);
            }
        } catch (Exception ignored) {}
        return (boolean)pair.getRight();
    }

    private static class TeamSpeakConsumer implements IPacketsReceiver
    {
        @Override
        public void receive(String channel, String packet)
        {
            String[] args = packet.split(":");
            String[] prefix = args[0].split("/");
            if (!prefix[0].equals(SamaGamesAPI.get().getServerName()))
                return ;
            int id = Integer.parseInt(prefix[1]);
            MutablePair<ResultType, Object> result = TeamSpeakAPI.results.get(id);
            TeamSpeakAPI.results.remove(id);
            boolean ok = args.length > 1 && !args[1].equals("ERROR");
            if (!ok)
                SamaGamesAPI.get().getPlugin().getLogger().severe("[TeamSpeakAPI] Error : " + (args.length > 2 ? args[2] : "Unknown") + "(packet = " + packet + ")");
            else
                switch (result.getLeft())
                {
                    case UUID_LIST:
                        List<UUID> uuid = (List<UUID>) result.getRight();
                        for (int i = 1; i < args.length; i++)
                            uuid.add(UUID.fromString(args[i]));
                        break;
                    case INTEGER:
                        result.setRight(Integer.parseInt(args[1]));
                        break;
                    case BOOLEAN:
                        result.setRight(args[1].equalsIgnoreCase("OK") || args[1].equalsIgnoreCase("true"));
                        break ;
                    default:
                        break ;
                }
            synchronized (result)
            {
                result.notifyAll();
            }
        }
    }

    private enum ResultType
    {
        UUID_LIST,
        BOOLEAN,
        INTEGER
    }
}
