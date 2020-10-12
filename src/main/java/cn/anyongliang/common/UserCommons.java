package cn.anyongliang.common;

import cn.anyongliang.db.jdbc.SqlTable;
import cn.anyongliang.db.redis.Redis;
import cn.anyongliang.json.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * create by Rock-Ayl 2019-6-16
 * 用户业务共有逻辑
 */
public class UserCommons {

    /**
     * 获取请求过来的代表user身份的cookieId
     *
     * @param request
     * @return
     */
    public static String getUserCookieId(HttpServletRequest request) {
        return request.getHeader("cookieId");
    }

    /**
     * 判断cookieId是否失效
     * 失效时间：登录后30分钟
     *
     * @param cookieId
     * @return true:有效  false:失效
     */
    public static boolean validateCookieId(String cookieId) {
        JsonObject user = Redis.user.getObject(cookieId);
        //不存在用户信息
        if (user == null) {
            //失效
            return false;
        }
        //获取登录时间
        long loginTime = user.getLong("loginTime");
        //登录后cookieId的30分钟后
        if ((loginTime + (1 * 1000 * 60 * 30)) < System.currentTimeMillis()) {
            //失效
            return false;
        }
        //成功
        return true;
    }

    /**
     * 单个用户与单个角色之间的绑定
     *
     * @return
     */
    public static JsonObject userBindRole(long userId, long roleId) {
        //如果没有绑定过,绑定
        if (SqlTable.use().queryObject("SELECT count(*) as count FROM roleBinduser WHERE userId = ? AND roleId = ?", new Object[]{userId, roleId}).getLong("count") == 0L) {
            //绑定
            SqlTable.use().insert("INSERT INTO roleBinduser (userId,roleId) VALUES (?,?)", new Object[]{userId, roleId});
        }
        //返回
        return JsonObject.Success();
    }
}
