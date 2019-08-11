package com.conference.dao;

import com.conference.dao.datasource.DataSource;
import com.conference.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserDaoTest {
    @Mock private DataSource dataSource;
    @Mock private DataSource.QueryBuilder queryBuilder;

    @Mock private User newUser;
    @Mock private User existingUser;
    private List<User> users;

    private String executedQuery = "";
    private Integer validId = 42;

    @InjectMocks
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        users = Collections.singletonList(existingUser);

        when(newUser.getId()).thenReturn(null);
        when(existingUser.getId()).thenReturn(validId);

        when(queryBuilder.prepare(any())).thenReturn(queryBuilder);
        when(queryBuilder.and(any())).thenReturn(queryBuilder);
        when(queryBuilder.list(any())).thenReturn(new ArrayList<>(users));
        when(queryBuilder.first(any())).thenReturn(Optional.of(existingUser));

        when(dataSource.query(anyString())).then(invocationOnMock -> {
            executedQuery = invocationOnMock.getArgument(0);
            return queryBuilder;
        });
    }

    @Test
    void save_new() {
        userDao.save(newUser);
        assertTrue(executedQuery.startsWith("insert"));
    }

    @Test
    void save_existing() {
        userDao.save(existingUser);
        assertTrue(executedQuery.startsWith("update"));
    }

    @Test
    void getAll() {
        List<User> actual = userDao.getAll();
        assertEquals(users, actual);
    }

    @Test
    void findById() {
        User actual = userDao.findById(validId);
        assertEquals(existingUser, actual);
    }

    @Test
    void removeById() {
        userDao.removeById(validId);
        assertEquals("delete from users where id = ?", executedQuery);
    }

    @Test
    void findByName() {
        Optional<User> found = userDao.findByName("someName");
        assertEquals("select * from users where name = ?", executedQuery);
        assertTrue(found.isPresent());
        assertEquals(existingUser, found.get());
    }

    @Test
    void findByEmail() {
        Optional<User> found = userDao.findByEmail("a@i.com");
        assertEquals("select * from users where email = ?", executedQuery);
        assertTrue(found.isPresent());
        assertEquals(existingUser, found.get());
    }
}