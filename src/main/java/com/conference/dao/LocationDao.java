package com.conference.dao;

import com.conference.Component;
import com.conference.dao.datasource.DataSource;
import com.conference.data.entity.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class LocationDao implements Dao<Location> {
    private final DataSource dataSource;

    public LocationDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Location entity) {
        if (entity.getId() == null) {
            add(entity);
        } else {
            update(entity);
        }
    }

    @Override
    public List<Location> getAll() {
        return dataSource.query("select * from locations order by id").list(this::convert);
    }

    @Override
    public Location findById(Integer id) {
        return dataSource.query("select * from locations where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .first(this::convert)
                .orElse(null);
    }

    @Override
    public void removeById(Integer id) {
        dataSource.query("delete from locations where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .execute();
    }

    private void add(Location entity) {
        dataSource.query("insert into locations (name,places) values(?,?)")
                .prepare(ps -> prepare(entity, ps, false))
                .execute(rs -> entity.setId(rs.getInt(1)));
    }

    private void update(Location entity) {
        dataSource.query("update locations set name = ?, places = ? where id = ?")
                .prepare(ps -> prepare(entity, ps, true))
                .execute();
    }

    private void prepare(Location entity, PreparedStatement ps, boolean isUpdate) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setInt(2, entity.getPlaces());
        if (isUpdate) {
            ps.setInt(3, entity.getId());
        }
    }

    private Location convert(ResultSet rs) throws SQLException {
        Location entity = new Location();
        entity.setId(rs.getInt("id"));
        entity.setName(rs.getString("name"));
        entity.setPlaces(rs.getInt("places"));
        return entity;
    }
}
