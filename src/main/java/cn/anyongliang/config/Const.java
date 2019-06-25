package cn.anyongliang.config;

/**
 * 常量
 * create by Rock-Ayl 2019-6-13
 */
public class Const {

    /**
     * Redis-pool
     * Redisson-pool
     */
    public static String RedisHost = "127.0.0.1";
    public static int RedisPort = 6379;
    public static int RedisTimeOut = 10000;
    public static String RedisAuth = "";
    public static int RedisDatabase = 0;

    /**
     * jdbc-mysql/MariaDB
     */
    public static String JdbcDriver = "com.mysql.cj.jdbc.Driver";
    public static String JdbcHost = "127.0.0.1";
    public static String JdbcPort = "3306";
    public static String JdbcUser = "root";
    public static String JdbcDBName = "blog";
    public static String JdbcPassword = "123456";
    public static String SPACE = " ";

    /**
     * mongoDB
     */
    public static String MongoHost = "16.16.11.1:27017";
    public static String MongoDBName = "file";

}
