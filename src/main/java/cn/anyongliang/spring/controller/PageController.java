package cn.anyongliang.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制层
 * create by Rock-Ayl 2019-6-16
 */
@RequestMapping(value = "Page")
@Controller
public class PageController {

    @RequestMapping(value = "/Index")
    public String getIndex() {
        return "/Page/index/index";
    }

    @RequestMapping(value = "/Resume")
    public String getResume() {
        return "/Page/resume/index";
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
