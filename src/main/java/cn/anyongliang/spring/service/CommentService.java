package cn.anyongliang.spring.service;

import cn.anyongliang.common.StringCommons;
import cn.anyongliang.db.jdbc.SqlTable;
import cn.anyongliang.json.JsonObject;
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
        //基础Sql
        StringBuffer sql = new StringBuffer("SELECT a.*,b.userName as realName,b.email as realEmail,d.`name` as role FROM `comment` a LEFT JOIN `user` b ON a.userId=b.id LEFT JOIN role_bind_user c ON b.id = c.userId LEFT JOIN role d ON c.roleId = d.id ORDER BY a.`timestamp` DESC");
        //查询并返回
        return StringCommons.queryItemsAndTotalCount(sql, new Object[]{}, pageIndex, pageSize);
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
        //获取用户已登录的信息
        JsonObject userObject = UserCommons.getUserLoginInfo(cookieId);
        //判空
        if (userObject != null) {
            //使用用户登录信息
            userId = userObject.getLong("id", -1L);
            userName = userObject.getString("userName", "匿名用户");
            email = userObject.getString("email", "-");
        }
        //插入信息
        SqlTable.use().insert(" INSERT INTO `comment` (userId,userName,content,timestamp,email) VALUES (?,?,?,?,?)", new Object[]{userId, userName, commentInfo, System.currentTimeMillis(), email});
        //返回
        return JsonObject.Success("成功了");
    }

}
