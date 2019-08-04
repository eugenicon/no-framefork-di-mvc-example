package com.conference.controller;

import com.conference.Component;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;

@Component
@Controller
public class ErrorController {

    @GetMapping("/error/403")
    public String show403() {
        return "error/403.jsp";
    }

    @GetMapping("/error/404")
    public String show404() {
        return "error/404.jsp";
    }

    @GetMapping("/error/500")
    public String show500() {
        return "error/500.jsp";
    }

    @GetMapping("/test-404")
    public String test404() {
        return "unknown.jsp";
    }

    @GetMapping("/test-500")
    public String test500() {
        throw new RuntimeException("The test method has thrown an exception");
    }
}
