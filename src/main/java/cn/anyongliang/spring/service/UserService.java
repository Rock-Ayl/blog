package cn.anyongliang.spring.service;

import cn.anyongliang.json.JsonObject;
import cn.anyongliang.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "UserService")
public class UserService {

    @RequestMapping("GetCookieId")
    public JsonObject getCookieId() {
        //获得一个唯一性的cookieId
        return JsonObject.Success().append("cookieId", StringUtil.newId());
    }

}