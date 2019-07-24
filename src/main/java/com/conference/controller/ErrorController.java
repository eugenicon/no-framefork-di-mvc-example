package com.conference.controller;

import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/404")
    public String show404() {
        return "error/404.jsp";
    }

    @GetMapping("/500")
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
