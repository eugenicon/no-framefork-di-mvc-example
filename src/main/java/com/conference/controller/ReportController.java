package com.conference.controller;

import com.conference.Component;
import com.conference.dao.ReportDao;
import com.conference.data.entity.Report;
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
    public String save(@Valid Report report) {
        reportDao.save(report);
        return "redirect:/reports";
    }

    @PostMapping("/reports/delete/{id}")
    public String delete(Integer id) {
        reportDao.removeById(id);
        return "redirect:/reports";
    }

    @ExceptionMapping(ValidationException.class)
    public View onFailedValidation(HttpServletRequest request, ValidationException exception) {
        String refererUrl = request.getHeader("Referer");
        return View.of("redirect:" + refererUrl)
                .with("item", exception.getValue())
                .with("validationResult", exception.getValidationResult());
    }
}
