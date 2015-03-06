package net.samagames.samagamesapi.database;

import redis.clients.jedis.*;

import java.util.*;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class FakeSherdedRedis extends ShardedJedis {
	public FakeSherdedRedis() {
		super(new ArrayList<JedisShardInfo>());
	}

	@Override
	public String set(String key, String value) {
		return value;
	}

	@Override
	public String set(String key, String value, String nxxx, String expx, long time) {
		return value;
	}

	@Override
	public String get(String key) {
		return null;
	}

	@Override
	public String echo(String string) {
		return string;
	}

	@Override
	public Boolean exists(String key) {
		return false;
	}

	@Override
	public String type(String key) {
		return "none";
	}

	@Override
	public Long expire(String key, int seconds) {
		return System.currentTimeMillis() + (seconds * 1000);
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		return unixTime * 1000;
	}

	@Override
	public Long ttl(String key) {
		return 0L;
	}

	@Override
	public String getSet(String key, String value) {
		return value;
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return set(key, value);
	}

	@Override
	public Long decrBy(String key, long integer) {
		return 0L;
	}

	@Override
	public Long decr(String key) {
		return 0L;
	}

	@Override
	public Long incrBy(String key, long integer) {
		return integer;
	}

	@Override
	public Double incrByFloat(String key, double integer) {
		return integer;
	}

	@Override
	public Long incr(String key) {
		return 1L;
	}

	@Override
	public Long append(String key, String value) {
		return (long) value.length();
	}

	@Override
	public Long hset(String key, String field, String value) {
		return 1L;
	}

	@Override
	public String hget(String key, String field) {
		return null;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return null;
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return null;
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		return value;
	}

	@Override
	public Double hincrByFloat(String key, String field, double value) {
		return value;
	}

	@Override
	public Boolean hexists(String key, String field) {
		return false;
	}

	@Override
	public Long del(String key) {
		return 0L;
	}

	@Override
	public Long hdel(String key, String... fields) {
		return 0L;
	}

	@Override
	public Long hlen(String key) {
		return 0L;
	}

	@Override
	public Set<String> hkeys(String key) {
		return new HashSet<>();
	}

	@Override
	public List<String> hvals(String key) {
		return new ArrayList<>();
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return new HashMap<>();
	}

	@Override
	public Long rpush(String key, String... strings) {
		return (long) strings.length;
	}

	@Override
	public Long lpush(String key, String... strings) {
		return (long) strings.length;
	}

	@Override
	public Long lpushx(String key, String... string) {
		return (long) string.length;
	}

	@Override
	public Long strlen(String key) {
		return 0L;
	}

	@Override
	public Long rpushx(String key, String... string) {
		return (long) string.length;
	}

	@Override
	public Long llen(String key) {
		return 0L;
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		return new ArrayList<>();
	}

	@Override
	public String ltrim(String key, long start, long end) {
		return null;
	}

	@Override
	public String lindex(String key, long index) {
		return null;
	}

	@Override
	public String lset(String key, long index, String value) {
		return value;
	}

	@Override
	public Long lrem(String key, long count, String value) {
		return 0L;
	}

	@Override
	public String lpop(String key) {
		return null;
	}

	@Override
	public String rpop(String key) {
		return null;
	}

	@Override
	public Long sadd(String key, String... members) {
		return (long) members.length;
	}

	@Override
	public Set<String> smembers(String key) {
		return new HashSet<>();
	}

	@Override
	public Long srem(String key, String... members) {
		return 0L;
	}

	@Override
	public String spop(String key) {
		return null;
	}

	@Override
	public Long scard(String key) {
		return 0L;
	}

	@Override
	public Boolean sismember(String key, String member) {
		return false;
	}

	@Override
	public String srandmember(String key) {
		return null;
	}

	@Override
	public Long zadd(String key, double score, String member) {
		return 1L;
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		return (long) scoreMembers.size();
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		return new HashSet<>();
	}

	@Override
	public Long zrem(String key, String... members) {
		return 0L;
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		return score;
	}

	@Override
	public Long zrank(String key, String member) {
		return 0L;
	}

	@Override
	public Long zrevrank(String key, String member) {
		return 0L;
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		return new HashSet<>();
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		return new HashSet<>();
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return new HashSet<>();
	}

	@Override
	public Long zcard(String key) {
		return 0L;
	}

	@Override
	public Double zscore(String key, String member) {
		return 0D;
	}

	@Override
	public List<String> sort(String key) {
		return new ArrayList<>();
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		return new ArrayList<>();
	}

	@Override
	public Long zcount(String key, double min, double max) {
		return 0L;
	}

	@Override
	public Long zcount(String key, String min, String max) {
		return 0L;
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		return new HashSet<>();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return new HashSet<>();
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		return new HashSet<>();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		return new HashSet<>();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		return new HashSet<>();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		return new HashSet<>();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		return new HashSet<>();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		return new HashSet<>();
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		return new HashSet<>();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		return new HashSet<>();
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		return new HashSet<>();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		return new HashSet<>();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		return new HashSet<>();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		return new HashSet<>();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		return new HashSet<>();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		return new HashSet<>();
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		return 0L;
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		return 0L;
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		return 0L;
	}

	@Override
	public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
		return 0L;
	}

	@Override
	public void close() {
		return;
	}

	@Override
	public Jedis getShard(byte[] key) {
		return new FakeJedis();
	}

	@Override
	public Jedis getShard(String key) {
		return new FakeJedis();
	}

	@Override
	public Collection<JedisShardInfo> getAllShardInfo() {
		return new ArrayList<>();
	}

	@Override
	public Collection<Jedis> getAllShards() {
		return new ArrayList<>();
	}
}
