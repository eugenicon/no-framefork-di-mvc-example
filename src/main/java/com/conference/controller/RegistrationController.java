package com.conference.controller;


import com.conference.Component;
import com.conference.data.dto.UserRegistrationDto;
import com.conference.data.entity.Role;
import com.conference.data.entity.User;
import com.conference.service.UserService;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;
import com.conference.validation.annotation.Valid;

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
        user.setEmail(userLogin.getUserName());
        user.setName(userLogin.getUserName().split("@")[0]);
        user.setPassword(userLogin.getPassword());
        user.setRole(isFirstUser ? Role.ADMIN : Role.USER);
        userService.save(user);
        return "redirect:/";
    }
}
