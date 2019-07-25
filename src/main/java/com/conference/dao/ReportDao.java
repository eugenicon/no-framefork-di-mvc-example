package com.conference.dao;

import com.conference.Component;
import com.conference.dao.datasource.DataSource;
import com.conference.data.entity.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ReportDao implements Dao<Report> {
    private final DataSource dataSource;

    public ReportDao(DataSource dataSource) {
        this.dataSource = dataSource;
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
        dataSource.query("insert into reports (theme,place,reporter,description) values(?,?,?,?)")
                .prepare(ps -> prepare(entity, ps, false))
                .execute(rs -> entity.setId(rs.getInt(1)));
    }

    private void update(Report entity) {
        dataSource.query("update reports set theme = ?,place = ?,reporter = ?,description = ? where id = ?")
                .prepare(ps -> prepare(entity, ps, true))
                .execute();
    }

    private void prepare(Report entity, PreparedStatement ps, boolean isUpdate) throws SQLException {
        ps.setString(1, entity.getTheme());
        ps.setString(2, entity.getPlace());
        ps.setString(3, entity.getReporter());
        ps.setString(4, entity.getDescription());
        if (isUpdate) {
            ps.setInt(7, entity.getId());
        }
    }

    private Report convert(ResultSet rs) throws SQLException {
        Report entity = new Report();
        entity.setId(rs.getInt("id"));
        entity.setTheme(rs.getString("theme"));
        entity.setPlace(rs.getString("place"));
        entity.setReporter(rs.getString("reporter"));
        entity.setDescription(rs.getString("description"));
        return entity;
    }
}
