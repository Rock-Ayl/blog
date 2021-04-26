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
     * 获取用户登录信息
     *
     * @return
     */
    public static JsonObject getUserLoginInfo(String cookieId) {
        //获取用户信息
        return Redis.user.getObject(cookieId);
    }

    /**
     * 单个用户与单个角色之间的绑定
     *
     * @return
     */
    public static void userBindRole(long userId, long roleId) {
        //如果没有插入,有就不插入
        SqlTable.use().insert("INSERT role_bind_user (userId,roleId) SELECT ?,? FROM role_bind_user WHERE NOT EXISTS (SELECT id FROM role_bind_user WHERE userId=? AND roleId=?) LIMIT 1", new Object[]{userId, roleId, userId, roleId});
    }

}
