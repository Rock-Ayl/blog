package cn.anyongliang.db.redis;

import cn.anyongliang.config.Const;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import cn.anyongliang.util.StringUtils;

/**
 * redisson-pool模式
 * redisson是一个高并发的锁,确保唯一性，数据存储在redis中
 * create by Rock-Ayl 2019-6-13
 */
public class Redisson {

    public static RedissonClient client;
    protected static Config config;

    static {
        config = new Config();
        if (StringUtils.isEmpty(Const.RedisAuth)) {
            config.useSingleServer().setAddress("redis://" + Const.RedisHost + ":" + Const.RedisPort).setDatabase(Const.RedisDatabase);
        } else {
            config.useSingleServer().setAddress("redis://" + Const.RedisHost + ":" + Const.RedisPort).setPassword(Const.RedisAuth).setDatabase(Const.RedisDatabase);
        }
        client = org.redisson.Redisson.create(config);
    }

    public static RLock lock(String name) {
        RLock lock = client.getLock(name);
        return lock;
    }

    public static long nextId(String name) {
        RAtomicLong atomicLong = client.getAtomicLong(name);
        atomicLong.incrementAndGet();
        return atomicLong.get();
    }

    public static long getId(String name) {
        RAtomicLong atomicLong = client.getAtomicLong(name);
        return atomicLong.get();
    }

    public static void setNextId(String name, long value) {
        RAtomicLong atomicLong = client.getAtomicLong(name);
        atomicLong.set(value);
    }

    public static void main(String[] args) {
        System.out.println(Redisson.nextId("ayl"));
    }

}
