package com.conference.controller;


import com.conference.Component;
import com.conference.data.dto.UserLoginDto;
import com.conference.service.AuthenticationService;
import com.conference.service.ServiceException;
import com.conference.servlet.View;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.ExceptionMapping;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;
import com.conference.validation.Valid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Controller
public class LoginController {
    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String login() {
        return "login/login.jsp";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDto userLogin, HttpSession session) throws ServiceException {
        authenticationService.login(session, userLogin.getUserName(), userLogin.getPassword());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        authenticationService.logout(session);
        return "redirect:/";
    }

    @ExceptionMapping(ServiceException.class)
    public View showUserAddErrors(ServiceException exception, HttpServletRequest request) {
        String refererUrl = request.getHeader("Referer");
        return View.of("redirect:" + refererUrl).with("message", exception.getMessage());
    }
}
