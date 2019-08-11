package com.conference.service;

import com.conference.converter.ConversionService;
import com.conference.data.dto.UserLoginDto;
import com.conference.data.entity.Role;
import com.conference.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationServiceTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpSession session;
    @Mock private ServletContext servletContext;
    @Mock private ConversionService conversionService;
    @Mock private UserService userService;

    private AuthenticatedUser authenticatedUser = new AuthenticatedUser("a@i.com", Role.USER);
    private AuthenticatedUser newUser = new AuthenticatedUser("new@.i.com", Role.USER);
    private AuthenticatedUser emptyUser = new AuthenticatedUser("UNKNOWN", Role.UNKNOWN);

    private Map<String, Object> sessionAttributes = new HashMap<>();
    private Map<String, AuthenticatedUser> allAuth = new HashMap<>();
    private String validSessionId = "42";
    private String validPassword = "12345";

    @InjectMocks
    private AuthenticationService service;

    @BeforeEach
    void setUp() {
        allAuth.put(validSessionId, authenticatedUser);

        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getRequestURI()).thenReturn("/some-url");
        when(session.getServletContext()).thenReturn(servletContext);
        when(session.getId()).thenReturn(validSessionId);
        doAnswer(invocation -> {
            sessionAttributes.put(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).when(session).setAttribute(anyString(), any());
        when(servletContext.getAttribute("usersAuth")).thenReturn(allAuth);
        when(servletContext.getContextPath()).thenReturn("");

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUserName(newUser.getEmail());

        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setRole(newUser.getRole());

        when(conversionService.convert(request, UserLoginDto.class)).thenReturn(userLoginDto);
        when(userService.findMatchingCredentials(newUser.getEmail(), validPassword)).thenReturn(Optional.of(user));

        service.init(servletContext);
    }

    @Test
    void getAuthentication() {
        Optional<AuthenticatedUser> authentication = service.getAuthentication(session);
        assertTrue(authentication.isPresent());
        assertEquals(authenticatedUser, authentication.get());
    }

    @Test
    void verifyUserId() {
        String userId = service.verifyUserId(request);
        assertEquals(authenticatedUser.getEmail(), userId);
    }

    @Test
    void isUserHasOtherOpenSessions_False() {
        boolean actual = service.isUserHasOtherOpenSessions(authenticatedUser.getEmail(), session);
        assertFalse(actual);
    }

    @Test
    void isUserHasOtherOpenSessions_True() {
        allAuth.put("43", new AuthenticatedUser("a@i.com", Role.USER));

        boolean actual = service.isUserHasOtherOpenSessions(authenticatedUser.getEmail(), session);
        assertTrue(actual);
    }

    @Test
    void finishUserSessions() {
        service.finishUserSessions(validSessionId, session);
        assertFalse(allAuth.containsKey(validSessionId));
    }

    @Test
    void resetAuthentication() {
        service.resetAuthentication(session);
        assertEquals(emptyUser, sessionAttributes.get("auth"));
    }

    @Test
    void isUserSessionStarted() {
        assertTrue(service.isUserSessionStarted(authenticatedUser.getEmail(), session));
        assertFalse(service.isUserSessionStarted(emptyUser.getEmail(), session));
    }

    @Test
    void startUserSession() {
        allAuth.clear();
        service.startUserSession(request);
        AuthenticatedUser authenticatedUser = allAuth.get(validSessionId);
        assertEquals(newUser.getEmail(), authenticatedUser.getEmail());
        assertEquals(Role.UNKNOWN, authenticatedUser.getRole());
    }

    @Test
    void login() throws ServiceException {
        service.login(session, newUser.getEmail(), validPassword);
        AuthenticatedUser authenticatedUser = allAuth.get(validSessionId);
        assertEquals(newUser, authenticatedUser);
    }

    @Test
    void logout() {
        assertTrue(allAuth.containsKey(validSessionId));
        service.logout(session);
        assertFalse(allAuth.containsKey(validSessionId));
    }

    @Test
    void isLoginRequest() {
        assertFalse(service.isLoginRequest(request));
        when(request.getRequestURI()).thenReturn("/login");
        assertTrue(service.isLoginRequest(request));
    }
}