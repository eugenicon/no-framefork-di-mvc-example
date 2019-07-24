package com.conference.config;

import com.conference.controller.ErrorController;
import com.conference.controller.IndexController;
import com.conference.controller.ReportController;
import com.conference.data.dao.ReportDao;
import com.conference.data.entity.Report;
import com.conference.servlet.RequestResolver;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class ComponentConfig {

    private final Map<Class, Object> instances = new HashMap<>();

    public ComponentConfig() {
        ReportDao reportDao = new ReportDao();
        reportDao.save(new Report("Java", "Room 404", "Usernamis Adminos"));

        RequestResolver requestResolver = new RequestResolver();
        ReportController reportController = new ReportController(reportDao);
        IndexController indexController = new IndexController();
        ErrorController errorController = new ErrorController();

        register(requestResolver);
        register(indexController);
        register(errorController);
        register(reportController);
    }

    private <T> void register(T requestResolver) {
        instances.put(requestResolver.getClass(), requestResolver);
    }

    public <T> T getComponent(Class<T> type) {
        return (T) instances.get(type);
    }

    public <T extends Annotation> List getAnnotatedComponents(Class<T> annotation) {
        return instances.keySet().stream()
                .filter(c -> c.isAnnotationPresent(annotation))
                .map(instances::get)
                .collect(Collectors.toList());
    }
}
