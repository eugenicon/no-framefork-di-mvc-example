package com.conference.controller;

import com.conference.Component;
import com.conference.dao.LocationDao;
import com.conference.data.entity.Location;
import com.conference.servlet.View;
import com.conference.servlet.annotation.Controller;
import com.conference.servlet.annotation.GetMapping;
import com.conference.servlet.annotation.PostMapping;
import com.conference.validation.annotation.Valid;

@Component
@Controller
public class LocationController {
    private final LocationDao locationDao;

    public LocationController(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @GetMapping("/locations")
    public View showListPage() {
        return View.of("place/place-list.jsp").with("list", locationDao.getAll());
    }

    @GetMapping("/locations/add")
    public String showAddPage() {
        return "place/place-edit.jsp";
    }

    @GetMapping("/locations/{id}")
    public View showEditPage(Integer id) {
        return View.of("place/place-edit.jsp").with("item", locationDao.findById(id));
    }

    @PostMapping("/locations/save")
    public String save(@Valid Location entity) {
        locationDao.save(entity);
        return "redirect:/locations";
    }

    @PostMapping("/locations/delete/{id}")
    public String delete(Integer id) {
        locationDao.removeById(id);
        return "redirect:/locations";
    }
}
