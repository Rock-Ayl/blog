package cn.anyongliang.spring.service;

import cn.anyongliang.db.jdbc.SqlTable;
import cn.anyongliang.db.redis.Redis;
import cn.anyongliang.json.JsonObject;
import cn.anyongliang.util.StringUtil;
import cn.anyongliang.util.UserUtil;
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
        return JsonObject.Success().append("cookieId", StringUtil.newId());
    }

    /**
     * 根据用户的cookieId获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "GetUserInfoByCookieId", headers = "cookieId")
    public JsonObject getUserInfoByCookieId(HttpServletRequest request) {
        //获取cookieId
        String cookieId = UserUtil.getUserCookieId(request);
        //判断是否失效
        if (UserUtil.validateCookieId(cookieId)) {
            //如果未失效,获取用户的信息
            return Redis.user.getObject(cookieId).success();
        } else {
            return JsonObject.Fail("登录失效或超时,请重新登录.");
        }

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
        if (StringUtil.isEmpty(userName)) {
            return JsonObject.Fail("用户名不能为null.");
        }
        if (StringUtil.isEmpty(password)) {
            return JsonObject.Fail("密码不能为null.");
        }
        //获取用户信息
        JsonObject userObject = SqlTable.use().queryObject("select a.*,c.`name` as role from user a INNER JOIN roleBinduser b ON a.id = b.userId INNER JOIN role c ON c.id= b.roleId where a.userName = ? and a.password = ?", new Object[]{userName, password});
        if (userObject == null) {
            return JsonObject.Fail("账号密码错误.");
        }
        //当前时间
        long thisTime = System.currentTimeMillis();
        //登录时间组装进对象中
        userObject.append("loginTime", thisTime);
        //获取用户的cookieId
        String cookieId = UserUtil.getUserCookieId(request);
        //将用户信息存储缓存
        Redis.user.set(cookieId, userObject.toJson());
        //组装并返回用户信息
        JsonObject result = JsonObject.Success();
        result.putAll(userObject);
        result.append("cookieId", cookieId);
        return result;
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "LoginOut", headers = "cookieId")
    public JsonObject loginOut(HttpServletRequest request) {
        String cookieId = UserUtil.getUserCookieId(request);
        Redis.user.delete(cookieId);
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
        if (StringUtil.isEmpty(userName)) {
            return JsonObject.Fail("用户名不能为空.");
        }
        if (StringUtil.isEmpty(password)) {
            return JsonObject.Fail("密码不能为空.");
        }
        if (StringUtil.isEmpty(email)) {
            return JsonObject.Fail("邮箱不能为空.");
        }
        //新增
        long userId = SqlTable.use().insert("INSERT INTO `user`(userName,`password`,email) VALUES (?,?,?)", new Object[]{userName, password, email});
        //给予用户一个最基本的角色
        JsonObject roleIdObject = SqlTable.use().queryObject("SELECT id FROM role WHERE `name` = '普通用户'");
        if (roleIdObject != null) {
            //获取默认的角色id
            long roleId = roleIdObject.getLong("id");
            //绑定
            UserUtil.userBindRole(userId, roleId);
        }
        return JsonObject.Success().append("id", userId);
    }

}