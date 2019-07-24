package com.conference.data.dao;

import com.conference.data.entity.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportDao {
    private List<Report> data = new ArrayList<>();

    public void save(Report entity) {
        if (entity.getId() == null) {
            Integer id = data.stream().map(Report::getId).min(Integer::compareTo).orElse(0) + 1;
            entity.setId(id);
        }
        data.add(entity);
    }

    public List<Report> getAll() {
        return data;
    }
}
