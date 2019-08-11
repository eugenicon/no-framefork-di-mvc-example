package com.conference.dao.datasource;

import java.util.List;

public interface QueryCache {
    <T> List<T> getQueryResult(String queryKey);
    <T> void cacheQueryResult(String queryKey, List<T> list);
}
