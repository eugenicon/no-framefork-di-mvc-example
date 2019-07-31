package com.conference.dao;

import com.conference.Component;
import com.conference.dao.datasource.DataSource;
import com.conference.data.entity.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class ReportDao implements Dao<Report> {
    private final DataSource dataSource;
    private final LocationDao locationDao;
    private final UserDao userDao;

    public ReportDao(DataSource dataSource, LocationDao locationDao, UserDao userDao) {
        this.dataSource = dataSource;
        this.locationDao = locationDao;
        this.userDao = userDao;
    }

    @Override
    public void save(Report entity) {
        if (entity.getId() == null) {
            add(entity);
        } else {
            update(entity);
        }
    }

    @Override
    public List<Report> getAll() {
        return dataSource.query("select * from reports order by id").list(this::convert);
    }

    @Override
    public Report findById(Integer id) {
        return dataSource.query("select * from reports where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .first(this::convert)
                .orElse(null);
    }

    @Override
    public void removeById(Integer id) {
        dataSource.query("delete from reports where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .execute();
    }

    private void add(Report entity) {
        dataSource.query("insert into reports (theme,place,reporter,startTime,endTime,description) values(?,?,?,?,?,?)")
                .prepare(ps -> prepare(entity, ps, false))
                .execute(rs -> entity.setId(rs.getInt(1)));
    }

    private void update(Report entity) {
        dataSource.query("update reports set theme = ?,place = ?,reporter = ?,startTime = ?,endTime = ?,description = ? where id = ?")
                .prepare(ps -> prepare(entity, ps, true))
                .execute();
    }

    private void prepare(Report entity, PreparedStatement ps, boolean isUpdate) throws SQLException {
        ps.setString(1, entity.getTheme());
        ps.setInt(2, entity.getPlace().getId());
        ps.setInt(3, entity.getReporter().getId());
        ps.setTimestamp(4, new Timestamp(entity.getStartTime().getTime()));
        ps.setTimestamp(5, new Timestamp(entity.getEndTime().getTime()));
        ps.setString(6, entity.getDescription());
        if (isUpdate) {
            ps.setInt(7, entity.getId());
        }
    }

    private Report convert(ResultSet rs) throws SQLException {
        Report entity = new Report();
        entity.setId(rs.getInt("id"));
        entity.setTheme(rs.getString("theme"));
        entity.setPlace(locationDao.findById(rs.getInt("place")));
        entity.setReporter(userDao.findById(rs.getInt("reporter")));
        entity.setDescription(rs.getString("description"));
        entity.setStartTime(rs.getTimestamp("startTime"));
        entity.setEndTime(rs.getTimestamp("endTime"));
        return entity;
    }
}
