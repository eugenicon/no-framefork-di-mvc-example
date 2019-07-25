package com.conference.controller;

import com.conference.Component;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;

@Component
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
