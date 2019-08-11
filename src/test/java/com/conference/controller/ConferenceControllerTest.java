package com.conference.controller;

import com.conference.dao.OrderDao;
import com.conference.dao.ReportDao;
import com.conference.dao.UserDao;
import com.conference.data.dto.ConferenceDto;
import com.conference.data.entity.Conference;
import com.conference.data.entity.Report;
import com.conference.data.entity.Role;
import com.conference.data.entity.User;
import com.conference.service.AuthenticatedUser;
import com.conference.service.ConferenceService;
import com.conference.service.ServiceException;
import com.conference.servlet.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConferenceControllerTest {
    @Mock private ConferenceService conferenceService;
    @Mock private ReportDao reportDao;
    @Mock private UserDao userDao;
    @Mock private OrderDao orderDao;

    @Mock private List<Report> reports;
    @Mock private List<ConferenceDto> conferences;
    @Mock private List<User> users;
    @Mock private Conference conference;
    @Mock private ConferenceDto conferenceDto;

    private int validId = 42;
    private String validEmail = "a@i.com";

    @InjectMocks
    private ConferenceController conferenceController;

    @BeforeEach
    void init() {
        when(conferenceService.getAll()).thenReturn(conferences);
        when(conferenceService.findById(validId)).thenReturn(conference);
        when(conferenceService.viewById(validId)).thenReturn(conferenceDto);
        when(reportDao.getAll()).thenReturn(reports);
        when(userDao.getAll()).thenReturn(users);
        when(userDao.findByEmail(validEmail)).thenReturn(Optional.ofNullable(mock(User.class)));
    }

    @Test
    void showListPage() {
        View actual = conferenceController.showListPage();
        assertEquals("conference/conference-list.jsp", actual.getUrl());
        assertEquals(conferences, actual.getAttributes().get("list"));
    }

    @Test
    void showAddPage() {
        View actual = conferenceController.showAddPage();
        assertEquals("conference/conference-edit.jsp", actual.getUrl());
        assertEquals(users, actual.getAttributes().get("users"));
    }

    @Test
    void showEditPage() {
        View actual = conferenceController.showEditPage(validId);
        assertEquals("conference/conference-edit.jsp", actual.getUrl());
        assertEquals(conference, actual.getAttributes().get("item"));
        assertEquals(users, actual.getAttributes().get("users"));
    }

    @Test
    void showViewPage() {
        View actual = conferenceController.showViewPage(validId);
        assertEquals("conference/conference-view.jsp", actual.getUrl());
        assertEquals(conferenceDto, actual.getAttributes().get("item"));
        assertEquals(reports, actual.getAttributes().get("reports"));
    }

    @Test
    void orderTicket() throws ServiceException {
        String actual = conferenceController.orderTicket(validId, new AuthenticatedUser(validEmail, Role.USER));
        assertEquals("redirect:/conferences", actual);
        verify(orderDao).save(any());
    }

    @Test
    void save() {
        String actual = conferenceController.save(conference);
        assertEquals("redirect:/conferences", actual);
        verify(conferenceService).save(conference);
    }

    @Test
    void delete() {
        String actual = conferenceController.delete(validId);
        assertEquals("redirect:/conferences", actual);
        verify(conferenceService).removeById(validId);
    }
}