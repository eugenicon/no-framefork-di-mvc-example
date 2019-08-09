package com.conference.controller;

import com.conference.Component;
import com.conference.dao.ConferenceDao;
import com.conference.dao.ReportDao;
import com.conference.dao.UserDao;
import com.conference.data.entity.Conference;
import com.conference.servlet.View;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;
import com.conference.validation.annotation.Valid;

@Component
@Controller
public class ConferenceController {
    private final ConferenceDao conferenceDao;
    private final ReportDao reportDao;
    private final UserDao userDao;

    public ConferenceController(ConferenceDao conferenceDao, ReportDao reportDao, UserDao userDao) {
        this.conferenceDao = conferenceDao;
        this.reportDao = reportDao;
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

    @GetMapping("/conferences/view-{id}")
    public View showViewPage(Integer id) {
        return View.of("conference/conference-view.jsp")
                .with("users", userDao.getAll())
                .with("reports", reportDao.getAll())
                .with("item", conferenceDao.findById(id));
    }

    @GetMapping("/conferences/order/{id}")
    public String orderTicket(Integer id) {
        Conference conference = conferenceDao.findById(id);
        conference.setTotalTickets(conference.getTotalTickets() - 1);
        conferenceDao.save(conference);
        return "redirect:/conferences";
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
}
