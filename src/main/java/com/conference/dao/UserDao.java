package com.conference.dao;

import com.conference.Component;
import com.conference.dao.datasource.DataSource;
import com.conference.data.entity.Role;
import com.conference.data.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao implements Dao<User> {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User entity) {
        if (entity.getId() == null) {
            add(entity);
        } else {
            update(entity);
        }
    }

    @Override
    public List<User> getAll() {
        return dataSource.query("select * from users order by id").list(this::convert);
    }

    @Override
    public User findById(Integer id) {
        return dataSource.query("select * from users where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .first(this::convert)
                .orElse(null);
    }

    @Override
    public void removeById(Integer id) {
        dataSource.query("delete from users where id = ?")
                .prepare(ps -> ps.setInt(1, id))
                .execute();
    }

    public Optional<User> findByName(String userName) {
        return dataSource.query("select * from users where name = ?")
                .prepare(ps -> ps.setString(1, userName))
                .first(this::convert);
    }

    private void add(User entity) {
        dataSource.query("insert into users (name,role,email,password) values(?,?,?,?)")
                .prepare(ps -> prepare(entity, ps, false))
                .execute(rs -> entity.setId(rs.getInt(1)));
    }

    private void update(User entity) {
        dataSource.query("update users set name = ?, role = ?,email = ?,password = ? where id = ?")
                .prepare(ps -> prepare(entity, ps, true))
                .execute();
    }

    private void prepare(User entity, PreparedStatement ps, boolean isUpdate) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setString(2, entity.getRole().name());
        ps.setString(3, entity.getEmail());
        ps.setString(4, entity.getPassword());
        if (isUpdate) {
            ps.setInt(5, entity.getId());
        }
    }

    private User convert(ResultSet rs) throws SQLException {
        User entity = new User();
        entity.setId(rs.getInt("id"));
        entity.setName(rs.getString("name"));
        entity.setRole(Role.valueOf(rs.getString("role")));
        entity.setEmail(rs.getString("email"));
        entity.setPassword(rs.getString("password"));
        return entity;
    }

    public Optional<User> findByEmail(String email) {
        return dataSource.query("select * from users where email = ?")
                .prepare(ps -> ps.setString(1, email))
                .first(this::convert);
    }
}
