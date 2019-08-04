package com.conference.servlet;

import com.conference.ComponentResolver;
import com.conference.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);

    private AuthenticationService authenticationService = ComponentResolver.getComponent(AuthenticationService.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        authenticationService.resetAuthentication(session);
        LOGGER.info("Created session {}", session.getId());
    }
}
