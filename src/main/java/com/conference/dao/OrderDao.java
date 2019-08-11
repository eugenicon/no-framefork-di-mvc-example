package com.conference.dao;

import com.conference.Component;
import com.conference.dao.datasource.DataSource;
import com.conference.data.entity.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class OrderDao implements Dao<Order> {
    private final DataSource dataSource;
    private final UserDao userDao;
    private final ConferenceDao conferenceDao;

    public OrderDao(DataSource dataSource, UserDao userDao, ConferenceDao conferenceDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
        this.conferenceDao = conferenceDao;
    }

    @Override
    public void save(Order entity) {
        if (entity.getId() == null) {
            add(entity);
        } else {
            update(entity);
        }
    }

    @Override
    public List<Order> getAll() {
        return dataSource.query("select * from orders order by id").list(this::convert);
    }

    @Override
    public Order findById(Integer id) {
        return dataSource.query("select * from orders where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .first(this::convert)
                .orElse(null);
    }

    @Override
    public void removeById(Integer id) {
        dataSource.query("delete from orders where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .execute();
    }

    private void add(Order entity) {
        dataSource.query("insert into orders (owner,conference,date) values(?,?,?)")
                .prepare(ps -> prepare(entity, ps, false))
                .execute(rs -> entity.setId(rs.getInt(1)));
    }

    private void update(Order entity) {
        dataSource.query("update orders set owner = ?, conference = ?, date = ? where id = ?")
                .prepare(ps -> prepare(entity, ps, true))
                .execute();
    }

    private void prepare(Order entity, PreparedStatement ps, boolean isUpdate) throws SQLException {
        ps.setInt(1, entity.getOwner().getId());
        ps.setInt(2, entity.getConference().getId());
        ps.setTimestamp(3, new Timestamp(entity.getDate().getTime()));
        if (isUpdate) {
            ps.setInt(4, entity.getId());
        }
    }

    private Order convert(ResultSet rs) throws SQLException {
        Order entity = new Order();
        entity.setId(rs.getInt("id"));
        entity.setOwner(userDao.findById(rs.getInt("owner")));
        entity.setConference(conferenceDao.findById(rs.getInt("conference")));
        entity.setDate(rs.getTimestamp("date"));
        return entity;
    }

    public List<Order> findAllByConferenceId(Integer id) {
        return dataSource.query("select * from orders where conference = ? order by date desc")
                .prepare(ps -> ps.setInt(1, id))
                .list(this::convert);
    }

    public List<Order> findAllByUserId(Integer id) {
        return dataSource.query("select * from orders where owner = ? order by date desc")
                .prepare(ps -> ps.setInt(1, id))
                .list(this::convert);
    }
}
