package cn.anyongliang.config;

/**
 * 常量
 * create by Rock-Ayl 2019-6-13
 */
public class Const {

    /**
     * 通用connect配置
     */
    public static String publishHost = "127.0.0.1";

    /* Redis-pool,Redisson-pool */
    public static String RedisHost = publishHost;
    public static int RedisPort = 6379;
    public static int RedisTimeOut = 10000;
    public static String RedisAuth = "";
    public static int RedisDatabase = 0;

    /*jdbc-mysql/MariaDB*/
    public static String JdbcDriver = "com.mysql.cj.jdbc.Driver";
    public static String JdbcHost = publishHost;
    public static String JdbcPort = "3306";
    public static String JdbcUser = "root";
    public static String JdbcDBName = "blog";
    public static String JdbcPassword = "123456";
    public static String SPACE = " ";

    /*mongoDB*/
    public static String MongoHost = publishHost;
    public static String MongoPort = "27017";
    public static String MongoDBName = "file";

}
