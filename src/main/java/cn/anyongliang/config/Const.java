package cn.anyongliang.config;

/**
 * 常量
 * create by Rock-Ayl 2019-6-13
 */
public class Const {

    /**
     * 系统配置
     */

    //用户CookieId登过期时间:2个小时
    public static int CookieIdExpiredTime = 3600 * 2;

    /**
     * 通用connect配置
     */
    public static String PublishHost = "127.0.0.1";

    /* Redis-pool,Redisson-pool */
    public static String RedisHost = PublishHost;
    public static int RedisPort = 6379;
    public static int RedisTimeOut = 10000;
    public static String RedisAuth = "";
    public static int RedisDatabase = 0;

    /*jdbc-mysql/MariaDB*/
    public static String JdbcDriver = "com.mysql.cj.jdbc.Driver";
    public static String JdbcHost = PublishHost;
    public static String JdbcPort = "3306";
    public static String JdbcUser = "root";
    public static String JdbcDBName = "blog";
    public static String JdbcPassword = "vijoA.vijsd-d.cDkc";

    /*mongoDB*/
    public static String MongoHost = PublishHost;
    public static String MongoPort = "27017";
    public static String MongoDBName = "file";


    /**
     * 常用魔法变量
     */

    public final static String SPACE = " ";
    public final static String Message = "message";
    public final static String Items = "items";
    public final static String CookieId = "cookieId";
    public final static String Data = "data";
    public final static String IsSuccess = "isSuccess";
    public final static String TotalCount = "totalCount";
    public final static String Type = "type";
    public final static String FileId = "fileId";
    public final static String CtxUserId = "ctxUserId";
    public final static String FileName = "fileName";
    public final static String FileExt = "fileExt";
    public final static String FilePath = "filePath";
    public final static String FileSize = "fileSize";
    public final static String FileMD5 = "fileMD5";
    public final static String FileUploadTime = "fileUploadTime";

}
