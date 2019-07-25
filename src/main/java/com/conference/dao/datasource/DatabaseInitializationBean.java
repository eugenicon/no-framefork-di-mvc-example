package com.conference.dao.datasource;

import com.conference.Component;
import com.conference.util.ResourceReader;

import java.util.Properties;

@Component
public class DatabaseInitializationBean {
    public DatabaseInitializationBean(Properties properties, DataSource dataSource) {
        try {
            String initBdScript = ResourceReader.getResourceAsString(properties.getProperty("jdbc.init-script"));
            if (!initBdScript.isEmpty()) {
                dataSource.query(initBdScript).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
