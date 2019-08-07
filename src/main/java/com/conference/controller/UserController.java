package com.conference.controller;

import com.conference.Component;
import com.conference.dao.UserDao;
import com.conference.data.entity.Role;
import com.conference.data.entity.User;
import com.conference.servlet.View;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;
import com.conference.validation.annotation.Valid;

import java.util.Arrays;

@Component
@Controller
public class UserController {
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users")
    public View showListPage() {
        return View.of("user/user-list.jsp").with("list", userDao.getAll());
    }

    @GetMapping("/users/add")
    public View showAddPage() {
        return View.of("user/user-edit.jsp").with("roles", Arrays.asList(Role.values()));
    }

    @GetMapping("/users/{id}")
    public View showEditPage(Integer id) {
        return showAddPage().with("item", userDao.findById(id));
    }

    @PostMapping("/users/save")
    public String save(@Valid User entity) {
        userDao.save(entity);
        return "redirect:/users";
    }

    @PostMapping("/users/delete/{id}")
    public String delete(Integer id) {
        userDao.removeById(id);
        return "redirect:/users";
    }
}
