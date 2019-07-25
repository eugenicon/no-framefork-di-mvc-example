package com.conference.controller;

import com.conference.Component;
import com.conference.data.dao.ReportDao;
import com.conference.data.entity.Report;
import com.conference.servlet.View;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;

@Component
@Controller
public class ReportController {
    private final ReportDao reportDao;

    public ReportController(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @GetMapping("/reports")
    public View showListPage() {
        return View.of("report/report-list.jsp").with("list", reportDao.getAll());
    }

    @GetMapping("/reports/add")
    public String showAddPage() {
        return "report/report-edit.jsp";
    }

    @GetMapping("/reports/{id}")
    public View showEditPage(Integer id) {
        return View.of("report/report-edit.jsp").with("item", reportDao.findById(id));
    }

    @PostMapping("/reports/save")
    public String save(Report report) {
        reportDao.save(report);
        return "redirect:/reports";
    }

    @PostMapping("/reports/delete/{id}")
    public String delete(Integer id) {
        reportDao.removeById(id);
        return "redirect:/reports";
    }
}
