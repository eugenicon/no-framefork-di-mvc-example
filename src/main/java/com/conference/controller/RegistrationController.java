package com.conference.controller;


import com.conference.Component;
import com.conference.data.dto.UserRegistrationDto;
import com.conference.data.entity.Role;
import com.conference.data.entity.User;
import com.conference.service.UserService;
import com.conference.servlet.View;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.ExceptionMapping;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;
import com.conference.validation.Valid;
import com.conference.validation.ValidationException;

import javax.servlet.http.HttpServletRequest;

@Component
@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "login/register.jsp";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDto userLogin) {
        boolean isFirstUser = userService.getAllUsers().isEmpty();
        User user = new User();
        user.setName(userLogin.getUserName());
        user.setPassword(userLogin.getPassword());
        user.setRole(isFirstUser ? Role.ADMIN : Role.USER);
        userService.save(user);
        return "redirect:/";
    }

    @ExceptionMapping(ValidationException.class)
    public View showUserAddErrors(ValidationException exception, HttpServletRequest request) {
        String refererUrl = request.getHeader("Referer");
        return View.of("redirect:" + refererUrl)
                .with("item", exception.getValue())
                .with("validationResult", exception.getValidationResult());
    }
}
