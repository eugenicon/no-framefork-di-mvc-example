package com.conference.data.dao;

import com.conference.Component;
import com.conference.data.entity.Report;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportDao {
    private List<Report> data = new ArrayList<>();

    public void save(Report entity) {
        if (entity.getId() == null) {
            Integer id = data.stream().map(Report::getId).max(Integer::compareTo).orElse(0) + 1;
            entity.setId(id);
        }
        removeById(entity.getId());
        data.add(entity);
    }

    public List<Report> getAll() {
        return data;
    }

    public Report findById(Integer id) {
        return data.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);
    }

    public void removeById(Integer id) {
        Report existing = findById(id);
        if (existing != null) {
            data.remove(existing);
        }
    }
}
