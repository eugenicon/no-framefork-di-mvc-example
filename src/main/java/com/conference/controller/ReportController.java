package com.conference.controller;

import com.conference.Component;
import com.conference.dao.LocationDao;
import com.conference.dao.ReportDao;
import com.conference.dao.UserDao;
import com.conference.data.entity.Report;
import com.conference.servlet.View;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;
import com.conference.validation.annotation.Valid;

@Component
@Controller
public class ReportController {
    private final ReportDao reportDao;
    private final LocationDao locationDao;
    private final UserDao userDao;

    public ReportController(ReportDao reportDao, LocationDao locationDao, UserDao userDao) {
        this.reportDao = reportDao;
        this.locationDao = locationDao;
        this.userDao = userDao;
    }

    @GetMapping("/reports")
    public View showListPage() {
        return View.of("report/report-list.jsp").with("list", reportDao.getAll());
    }

    @GetMapping("/reports/add")
    public View showAddPage() {
        return View.of("report/report-edit.jsp")
                .with("locations", locationDao.getAll())
                .with("users", userDao.getAll());
    }

    @GetMapping("/reports/{id}")
    public View showEditPage(Integer id) {
        return showAddPage().with("item", reportDao.findById(id));
    }

    @PostMapping("/reports/save")
    public String save(@Valid Report entity) {
        reportDao.save(entity);
        return "redirect:/reports";
    }

    @PostMapping("/reports/delete/{id}")
    public String delete(Integer id) {
        reportDao.removeById(id);
        return "redirect:/reports";
    }
}
