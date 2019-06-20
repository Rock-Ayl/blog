package cn.anyongliang.spring.service;

import cn.anyongliang.db.jdbc.SqlTable;
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
        String sql = "";
        return JsonObject.Success().append("items", SqlTable.use().queryObjects(sql, new Object[]{}));
    }

    /**
     * 写评论
     *
     * @return
     */
    @RequestMapping(value = "WriteComment")
    public JsonObject writeComment(HttpServletRequest request, String commentEmail, String commentName, String commentInfo) {
        //获取cookieId
        String cookieId = UserUtil.getUserCookieId(request);
        //验证身份
        if (UserUtil.validateCookieId(cookieId)) {
            //todo 该逻辑为登录的用户
        } else {
            //todo 该逻辑为匿名的用户
        }
        return JsonObject.Success("成功了");
    }

}
