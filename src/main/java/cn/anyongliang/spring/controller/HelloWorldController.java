package cn.anyongliang.spring.controller;

import cn.anyongliang.db.jdbc.SqlTable;
import cn.anyongliang.json.JsonObject;
import cn.anyongliang.json.JsonObjects;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用来测试页面返回json
 *
 * @author Administrator
 */
@RestController
@RequestMapping(value = "hello")
public class HelloWorldController {

    @RequestMapping("world")
    public JsonObject index() {
        JsonObjects asd = SqlTable.use().queryObjects("select user_id,name from user", new Object[]{});
        return JsonObject.Success().append("items", asd);
    }

}