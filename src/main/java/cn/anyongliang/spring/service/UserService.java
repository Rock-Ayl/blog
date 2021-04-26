package cn.anyongliang.spring.service;

import cn.anyongliang.config.Const;
import cn.anyongliang.db.jdbc.SqlTable;
import cn.anyongliang.db.redis.Redis;
import cn.anyongliang.json.JsonObject;
import cn.anyongliang.common.UserCommons;
import cn.anyongliang.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 */
@RestController
@RequestMapping(value = "UserService")
public class UserService {

    /**
     * 获取一个新的cookieId
     *
     * @return
     */
    @RequestMapping(value = "GetCookieId")
    public JsonObject getCookieId() {
        //获得一个唯一性的cookieId
        return JsonObject.Success().append("cookieId", StringUtils.newId());
    }

    /**
     * 根据用户的cookieId获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "GetUserInfoByCookieId", headers = "cookieId")
    public JsonObject getUserInfoByCookieId(HttpServletRequest request) {
        //获取cookieId
        String cookieId = UserCommons.getUserCookieId(request);
        //从Redis中获取已经登录的用户信息
        JsonObject user = UserCommons.getUserLoginInfo(cookieId);
        //判空
        if (user != null) {
            //返回信息
            return user.success();
        }
        //返回失效信息
        return JsonObject.Fail("登录失效或超时,请重新登录.");
    }

    /**
     * 输入用户名,密码,然后登录
     *
     * @param request
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "Login", headers = "cookieId")
    public JsonObject login(HttpServletRequest request, String userName, String password) {
        //判空
        if (StringUtils.isEmpty(userName)) {
            return JsonObject.Fail("用户名不能为空.");
        }
        if (StringUtils.isEmpty(password)) {
            return JsonObject.Fail("密码不能为空.");
        }
        //获取用户信息
        JsonObject userObject = SqlTable.use().queryObject("select a.*,c.`name` as role from user a INNER JOIN role_bind_user b ON a.id = b.userId INNER JOIN role c ON c.id= b.roleId where a.userName = ? and a.password = ?", new Object[]{userName, password});
        //判空
        if (userObject == null) {
            return JsonObject.Fail("账号密码错误.");
        }
        //当前时间
        long thisTime = System.currentTimeMillis();
        //登录时间组装进对象中
        userObject.append("loginTime", thisTime);
        //获取用户的cookieId
        String cookieId = UserCommons.getUserCookieId(request);
        //将用户信息存储缓存
        Redis.user.setExpire(cookieId, userObject.toJson(), Const.CookieIdExpiredTime);
        //补全并返回用户信息
        return userObject.success().append("cookieId", cookieId);
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "LoginOut", headers = "cookieId")
    public JsonObject loginOut(HttpServletRequest request) {
        //获取cookieId
        String cookieId = UserCommons.getUserCookieId(request);
        //删除登录信息
        Redis.user.delete(cookieId);
        //返回
        return JsonObject.Success();
    }

    /**
     * 新增用户
     *
     * @param userName
     * @param password
     * @param email
     * @return
     */
    @RequestMapping(value = "AddUser")
    public JsonObject addUser(String userName, String password, String email) {
        //todo 验证数据的有效性
        if (StringUtils.isEmpty(userName)) {
            return JsonObject.Fail("用户名不能为空.");
        }
        if (StringUtils.isEmpty(password)) {
            return JsonObject.Fail("密码不能为空.");
        }
        if (StringUtils.isEmpty(email)) {
            return JsonObject.Fail("邮箱不能为空.");
        }
        //新增
        long userId = SqlTable.use().insert("INSERT INTO `user`(userName,`password`,email) VALUES (?,?,?)", new Object[]{userName, password, email});
        //给予用户一个最基本的角色
        JsonObject roleIdObject = SqlTable.use().queryObject("SELECT id FROM role WHERE `name` = '普通用户'");
        //判空
        if (roleIdObject != null) {
            //获取默认的角色id
            long roleId = roleIdObject.getLong("id");
            //绑定
            UserCommons.userBindRole(userId, roleId);
        }
        return JsonObject.Success().append("id", userId);
    }

}