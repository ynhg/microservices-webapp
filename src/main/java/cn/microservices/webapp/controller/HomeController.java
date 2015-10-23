package cn.microservices.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class HomeController {
    @RequestMapping(name = "index", value="/")
    public String index() {
        return "index";
    }

    @RequestMapping(name = "welcome", value="/welcome")
    public String welcome() {
        return "index";
    }
}
