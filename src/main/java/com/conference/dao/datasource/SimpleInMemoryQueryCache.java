package com.conference.dao.datasource;

import com.conference.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SimpleInMemoryQueryCache implements QueryCache {
    private static final Logger LOGGER = LogManager.getLogger(SimpleInMemoryQueryCache.class);
    private final Map<String, List> queryCache = new HashMap<>();

    @Override
    public <T> List<T> getQueryResult(String query) {
        boolean isSelectQuery = query.toUpperCase().startsWith("SELECT");

        if (isSelectQuery) {
            if (queryCache.containsKey(query)) {
                LOGGER.trace("got [{}] from cache", query);
                return queryCache.get(query);
            }
        } else {
            queryCache.clear();
            LOGGER.trace("Cache cleared");
        }
        return null;
    }

    @Override
    public <T> void cacheQueryResult(String query, List<T> list) {
        queryCache.put(query, list);
    }
}
