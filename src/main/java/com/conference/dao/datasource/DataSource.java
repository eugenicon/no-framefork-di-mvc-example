package com.conference.dao.datasource;


import com.conference.Component;
import com.conference.util.function.SafeBiConsumer;
import com.conference.util.function.SafeConsumer;
import com.conference.util.function.SafeFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

@Component
public class DataSource {
    private static final Logger LOGGER = LogManager.getLogger(DataSource.class);
    private final QueryCache queryCache;
    private final String userName;
    private final String password;
    private final String url;

    public DataSource(Properties properties, QueryCache queryCache) throws ClassNotFoundException {
        Class.forName(properties.getProperty("jdbc.driver"));

        url = properties.getProperty("jdbc.url");
        userName = properties.getProperty("jdbc.user");
        password = properties.getProperty("jdbc.password");
        this.queryCache = queryCache;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    private <T> List<T> executeQuery(Connection connection, QueryData queryData) throws SQLException {
        boolean useBatch = queryData.data.size() > 1;
        List<T> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(queryData.query, Statement.RETURN_GENERATED_KEYS)) {
            if (queryData.parameters != null) {
                for (Object dataObject : queryData.data) {
                    queryData.parameters.accept(ps, dataObject);
                    if (useBatch) {
                        ps.addBatch();
                    }
                }
            }
            boolean isSelectQuery = queryData.query.toUpperCase().startsWith("SELECT");
            String query = ps.toString();

            List<T> cachedResult = queryCache.getQueryResult(query);
            if (cachedResult != null) {
                return cachedResult;
            }

            LOGGER.debug(query);
            if (isSelectQuery) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add((T) queryData.converter.apply(rs));
                    }
                }
                queryCache.cacheQueryResult(query, list);
            } else {
                if (useBatch) {
                    ps.executeBatch();
                } else {
                    ps.executeUpdate();
                }
                if (queryData.resultProcessor != null) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        while (rs.next()) {
                            queryData.resultProcessor.accept(rs);
                        }
                    }
                }
            }
        }
        return list;
    }

    public QueryBuilder query(String sql) {
        return new QueryBuilder().and(sql);
    }

    private class QueryData {
        private String query;
        private SafeBiConsumer<PreparedStatement, Object> parameters;
        private SafeFunction<ResultSet, ?> converter;
        private SafeConsumer<ResultSet> resultProcessor;
        private List<?> data = Collections.singletonList(null);
    }

    public class QueryBuilder {
        private final Deque<QueryData> queries = new LinkedList<>();

        public QueryBuilder and(String sql) {
            QueryData queryData = new QueryData();
            queryData.query = sql;
            queries.add(queryData);
            return this;
        }

        public <T> Optional<T> first(SafeFunction<ResultSet, T> converter) {
            return list(converter).stream().findFirst();
        }

        public <T> List<T> list(SafeFunction<ResultSet, T> converter) {
            queries.peekLast().converter = converter;
            return execute();
        }

        public <T> QueryBuilder prepare(SafeConsumer<PreparedStatement> prepared) {
            return prepare(Collections.singletonList(null), (PreparedStatement ps, T data) -> prepared.accept(ps));
        }

        public <T> QueryBuilder prepare(List<T> data, SafeBiConsumer<PreparedStatement, T> prepared) {
            QueryData query = queries.peekLast();
            query.data = data;
            query.parameters = (SafeBiConsumer<PreparedStatement, Object>) prepared;
            return this;
        }

        public <T> List<T> execute() {
            List<T> list = new ArrayList<>();
            boolean useTransactions = queries.size() > 1;
            try (Connection connection = getConnection()) {
                if (useTransactions) {
                    LOGGER.debug("begin transaction");
                    connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    connection.setAutoCommit(false);
                }
                for (QueryData queryData : queries) {
                    list.addAll(executeQuery(connection, queryData));
                }
                if (useTransactions) {
                    LOGGER.debug("commit transaction");
                    connection.commit();
                }
            } catch (SQLException e) {
                LOGGER.error(e);
                throw new RuntimeException(e);
            }
            return list;
        }

        public void execute(SafeConsumer<ResultSet> processor) {
            queries.peekLast().resultProcessor = processor;
            execute();
        }
    }
}
