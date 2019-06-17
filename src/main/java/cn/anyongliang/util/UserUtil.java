package cn.anyongliang.util;

import cn.anyongliang.db.redis.Redis;
import cn.anyongliang.json.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * create by Rock-Ayl 2019-6-16
 * 用户 工具类
 */
public class UserUtil {

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
        //不存在用户信息,失效
        if (user == null) {
            return false;
        }
        //获取登录时间
        long loginTime = user.getLong("loginTime");
        //登录后cookieId 30分钟后失效
        if ((loginTime + (1 * 1000 * 60 * 30)) < System.currentTimeMillis()) {
            return false;
        }
        return true;
    }
}
