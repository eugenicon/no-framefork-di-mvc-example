package com.conference.controller;

import com.conference.data.dao.ReportDao;
import com.conference.data.entity.Report;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReportController {
    private final ReportDao reportDao;

    public ReportController(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @GetMapping("/reports")
    public String showListPage(HttpServletRequest request) {
        request.setAttribute("list", reportDao.getAll());
        return "report/report-list.jsp";
    }

    @GetMapping("/reports/add")
    public String showAddPage() {
        return "report/report-edit.jsp";
    }

    @GetMapping("/reports/{id}")
    public String showEditPage(HttpServletRequest request, Integer id) {
        request.setAttribute("item", reportDao.findById(id));
        return "report/report-edit.jsp";
    }

    @PostMapping("/reports/save")
    public String save(Report report) {
        reportDao.save(report);
        return "redirect:/reports";
    }
}
