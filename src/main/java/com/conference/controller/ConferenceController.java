package com.conference.controller;

import com.conference.Component;
import com.conference.dao.OrderDao;
import com.conference.dao.ReportDao;
import com.conference.dao.UserDao;
import com.conference.data.entity.Conference;
import com.conference.data.entity.Order;
import com.conference.data.entity.User;
import com.conference.service.AuthenticatedUser;
import com.conference.service.ConferenceService;
import com.conference.service.ServiceException;
import com.conference.servlet.View;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;
import com.conference.validation.annotation.Valid;

import java.util.Date;

@Component
@Controller
public class ConferenceController {
    private final ConferenceService conferenceService;
    private final ReportDao reportDao;
    private final UserDao userDao;
    private final OrderDao orderDao;

    public ConferenceController(ConferenceService conferenceService, ReportDao reportDao, UserDao userDao, OrderDao orderDao) {
        this.conferenceService = conferenceService;
        this.reportDao = reportDao;
        this.userDao = userDao;
        this.orderDao = orderDao;
    }

    @GetMapping("/conferences")
    public View showListPage() {
        return View.of("conference/conference-list.jsp").with("list", conferenceService.getAll());
    }

    @GetMapping("/conferences/add")
    public View showAddPage() {
        return View.of("conference/conference-edit.jsp")
                .with("users", userDao.getAll());
    }

    @GetMapping("/conferences/{id}")
    public View showEditPage(Integer id) {
        return showAddPage().with("item", conferenceService.findById(id));
    }

    @GetMapping("/conferences/view-{id}")
    public View showViewPage(Integer id) {
        return View.of("conference/conference-view.jsp")
                .with("reports", reportDao.findAllByConferenceId(id))
                .with("item", conferenceService.viewById(id));
    }

    @GetMapping("/conferences/order/{id}")
    public String orderTicket(Integer id, AuthenticatedUser authenticatedUser) throws ServiceException {
        Conference conference = conferenceService.findById(id);
        User user = userDao.findByEmail(authenticatedUser.getEmail()).orElseThrow(() -> new ServiceException("Unauthorized user"));
        Order order = new Order();
        order.setOwner(user);
        order.setConference(conference);
        order.setDate(new Date());
        orderDao.save(order);
        return "redirect:/conferences";
    }

    @PostMapping("/conferences/save")
    public String save(@Valid Conference entity) {
        conferenceService.save(entity);
        return "redirect:/conferences/view-" + entity.getId();
    }

    @PostMapping("/conferences/delete/{id}")
    public String delete(Integer id) {
        conferenceService.removeById(id);
        return "redirect:/conferences";
    }
}
