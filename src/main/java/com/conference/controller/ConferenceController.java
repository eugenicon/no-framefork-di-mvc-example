package com.conference.controller;

import com.conference.Component;
import com.conference.dao.ConferenceDao;
import com.conference.dao.UserDao;
import com.conference.data.entity.Conference;
import com.conference.servlet.View;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.ExceptionMapping;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;
import com.conference.validation.annotation.Valid;
import com.conference.validation.ValidationException;

import javax.servlet.http.HttpServletRequest;

@Component
@Controller
public class ConferenceController {
    private final ConferenceDao conferenceDao;
    private final UserDao userDao;

    public ConferenceController(ConferenceDao conferenceDao, UserDao userDao) {
        this.conferenceDao = conferenceDao;
        this.userDao = userDao;
    }

    @GetMapping("/conferences")
    public View showListPage() {
        return View.of("conference/conference-list.jsp").with("list", conferenceDao.getAll());
    }

    @GetMapping("/conferences/add")
    public View showAddPage() {
        return View.of("conference/conference-edit.jsp")
                .with("users", userDao.getAll());
    }

    @GetMapping("/conferences/{id}")
    public View showEditPage(Integer id) {
        return showAddPage().with("item", conferenceDao.findById(id));
    }

    @PostMapping("/conferences/save")
    public String save(@Valid Conference entity) {
        conferenceDao.save(entity);
        return "redirect:/conferences";
    }

    @PostMapping("/conferences/delete/{id}")
    public String delete(Integer id) {
        conferenceDao.removeById(id);
        return "redirect:/conferences";
    }

    @ExceptionMapping(ValidationException.class)
    public View onFailedValidation(HttpServletRequest request, ValidationException exception) {
        String refererUrl = request.getHeader("Referer");
        return View.of("redirect:" + refererUrl)
                .with("item", exception.getValue())
                .with("validationResult", exception.getValidationResult());
    }
}
