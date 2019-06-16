package cn.anyongliang.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 页面跳转控制层
 * create by anyongliang 2019-6-16
 */
@RequestMapping(value = "Page")
@Controller
public class PageController {

    @RequestMapping(value = "/Index", method = RequestMethod.GET)
    public String getIndex() {
        return "/Page/index/index";
    }

    @RequestMapping(value = "/UserLogin")
    public String getUserLogin() {
        return "/Page/login/login";
    }

    @RequestMapping(value = "/Error")
    public String getError() {
        return "/Page/error/error";
    }
}
