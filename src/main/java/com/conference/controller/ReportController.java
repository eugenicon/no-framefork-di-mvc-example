package com.conference.controller;

import com.conference.data.dao.ReportDao;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;

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

    @GetMapping("/reports/{id}")
    public String showEditPage(HttpServletRequest request, String id) {
        request.setAttribute("list", reportDao.getAll());
        return "report/report-list.jsp";
    }

    @GetMapping("/reports/add")
    public String showAddPage() {
        return "redirect:/reports";
    }
}
