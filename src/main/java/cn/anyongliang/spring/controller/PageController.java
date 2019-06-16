package cn.anyongliang.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = "/pageIndex", method = RequestMethod.GET)
    public String page() {
        return "/index/index";
    }

    @RequestMapping("/pageLogin")
    public String login() {
        return "login/login";
    }
}
