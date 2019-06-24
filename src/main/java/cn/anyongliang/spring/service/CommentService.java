package cn.anyongliang.spring.service;

import cn.anyongliang.db.jdbc.SqlTable;
import cn.anyongliang.db.redis.Redis;
import cn.anyongliang.json.JsonObject;
import cn.anyongliang.util.UserUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "CommentService")
public class CommentService {

    /**
     * 读评论
     *
     * @return
     */
    @RequestMapping(value = "ReadComment")
    public JsonObject readComment() {
        //todo 暂时注释掉
        //.append("items", SqlTable.use().queryObjects("SELECT * FROM `comment` ORDER BY `timestamp` DESC", new Object[]{}));
        return JsonObject.Success();
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
        String cookieId = UserUtil.getUserCookieId(request);
        //验证身份,如果是登录用户,覆盖身份参数，如果不是，直接写入
        if (UserUtil.validateCookieId(cookieId)) {
            //获取登录用户的信息
            JsonObject userObject = Redis.user.getObject(cookieId);
            if (userObject != null) {
                userId = userObject.getLong("userId");
                userName = userObject.getString("userName");
                email = userObject.getString("email");
            } else {
                return JsonObject.Fail("登录信息失效,请重新登录");
            }
        }
        //插入信息
        SqlTable.use().insert(" INSERT INTO `comment` (userId,userName,content,timestamp,email) VALUES (?,?,?,?,?)", new Object[]{userId, userName, commentInfo, System.currentTimeMillis(), email});
        return JsonObject.Success("成功了");
    }

}
