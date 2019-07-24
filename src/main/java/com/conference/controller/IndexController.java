package com.conference.controller;

import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String showWelcomePage() {
        return "index.jsp";
    }

    @GetMapping("/welcome")
    public String showAddPage() {
        return "redirect:/";
    }
}
