package com.conference.dao;

import com.conference.Component;
import com.conference.dao.datasource.DataSource;
import com.conference.data.entity.Conference;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class ConferenceDao implements Dao<Conference> {
    private final DataSource dataSource;
    private final UserDao userDao;

    public ConferenceDao(DataSource dataSource, UserDao userDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
    }

    @Override
    public void save(Conference entity) {
        if (entity.getId() == null) {
            add(entity);
        } else {
            update(entity);
        }
    }

    @Override
    public List<Conference> getAll() {
        return dataSource.query("select * from conferences order by id").list(this::convert);
    }

    @Override
    public Conference findById(Integer id) {
        return dataSource.query("select * from conferences where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .first(this::convert)
                .orElse(null);
    }

    @Override
    public void removeById(Integer id) {
        dataSource.query("delete from conferences where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .execute();
    }

    private void add(Conference entity) {
        dataSource.query("insert into conferences (name,date,moderator,totalTickets,description) values(?,?,?,?,?)")
                .prepare(ps -> prepare(entity, ps, false))
                .execute(rs -> entity.setId(rs.getInt(1)));
    }

    private void update(Conference entity) {
        dataSource.query("update conferences set name = ?,date = ?,moderator = ?,totalTickets = ?,description = ? where id = ?")
                .prepare(ps -> prepare(entity, ps, true))
                .execute();
    }

    private void prepare(Conference entity, PreparedStatement ps, boolean isUpdate) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setTimestamp(2, new Timestamp(entity.getDate().getTime()));
        ps.setInt(3, entity.getModerator().getId());
        ps.setInt(4, entity.getTotalTickets());
        ps.setString(5, entity.getDescription());
        if (isUpdate) {
            ps.setInt(6, entity.getId());
        }
    }

    private Conference convert(ResultSet rs) throws SQLException {
        Conference entity = new Conference();
        entity.setId(rs.getInt("id"));
        entity.setName(rs.getString("name"));
        entity.setDate(rs.getDate("date"));
        entity.setModerator(userDao.findById(rs.getInt("moderator")));
        entity.setTotalTickets(rs.getInt("totalTickets"));
        entity.setDescription(rs.getString("description"));
        return entity;
    }
}
