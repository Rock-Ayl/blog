package cn.anyongliang.spring.service;

import cn.anyongliang.db.jdbc.SqlTable;
import cn.anyongliang.db.redis.Redis;
import cn.anyongliang.json.JsonObject;
import cn.anyongliang.json.JsonObjects;
import cn.anyongliang.common.UserCommons;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户评论服务
 */
@RestController
@RequestMapping(value = "CommentService")
public class CommentService {

    /**
     * 读评论
     *
     * @return
     */
    @RequestMapping(value = "ReadComment")
    public JsonObject readComment(int pageIndex, int pageSize) {
        JsonObjects items = SqlTable.use().queryObjects("SELECT a.*,b.userName as realName,b.email as realEmail,d.`name` as role FROM `comment` a LEFT JOIN `user` b ON a.userId=b.id LEFT JOIN roleBinduser c ON b.id = c.userId LEFT JOIN role d ON c.roleId = d.id ORDER BY a.`timestamp` DESC LIMIT " + (pageIndex * pageSize - pageSize) + "," + (pageSize), new Object[]{});
        long count = SqlTable.use().queryObject("SELECT count(*) as count FROM `comment` a LEFT JOIN `user` b ON a.userId=b.id LEFT JOIN roleBinduser c ON b.id=c.userId LEFT JOIN role d ON c.roleId=d.id ORDER BY a.`timestamp` DESC").getLong("count");
        return JsonObject.Success().append("items", items).append("count", count);
    }

    /**
     * 写评论
     *
     * @return
     */
    @RequestMapping(value = "WriteComment")
    public JsonObject writeComment(HttpServletRequest request, String commentEmail, String commentName, String commentInfo) {
        //todo 验证输入的参数的合法性
        //初始化要插入的信息
        long userId = -1L;
        String userName = commentName;
        String email = commentEmail;
        //获取cookieId
        String cookieId = UserCommons.getUserCookieId(request);
        //验证身份,如果是登录用户,覆盖身份参数，如果不是，直接写入
        if (UserCommons.validateCookieId(cookieId)) {
            //获取登录用户的信息
            JsonObject userObject = Redis.user.getObject(cookieId);
            //判空
            if (userObject != null) {
                userId = userObject.getLong("id");
                userName = userObject.getString("userName");
                email = userObject.getString("email");
            } else {
                return JsonObject.Fail("登录信息失效,请重新登录.");
            }
        }
        //插入信息
        SqlTable.use().insert(" INSERT INTO `comment` (userId,userName,content,timestamp,email) VALUES (?,?,?,?,?)", new Object[]{userId, userName, commentInfo, System.currentTimeMillis(), email});
        //返回
        return JsonObject.Success("成功了");
    }

}
