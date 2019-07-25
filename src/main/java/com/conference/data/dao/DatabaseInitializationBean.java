package com.conference.data.dao;

import com.conference.Component;
import com.conference.converter.ConversionService;
import com.conference.converter.Converter;
import com.conference.data.entity.Report;

import java.util.List;

@Component
public class DatabaseInitializationBean {
    public DatabaseInitializationBean(ReportDao reportDao) {
        reportDao.save(new Report("Java", "Room 404", "Usernamis Adminos"));
        reportDao.save(new Report("Kotlin", "Room 666", "Kthulhu Ibragimovich"));
        reportDao.save(new Report("Groovy", "Room 418", "Iam Ateapot"));
    }
}
