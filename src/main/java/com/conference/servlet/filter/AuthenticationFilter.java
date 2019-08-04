package com.conference.servlet.filter;

import com.conference.ComponentResolver;
import com.conference.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("*")
public class AuthenticationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);
    private final AuthenticationService authenticationService = ComponentResolver.getComponent(AuthenticationService.class);

    @Override
    public void init(FilterConfig filterConfig) {
        authenticationService.init(filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final HttpSession session = httpRequest.getSession();

        if (authenticationService.isLoginRequest(httpRequest)) {
            String userId = authenticationService.getUserId(httpRequest);
            LOGGER.debug("verify auth on {} for {}", httpRequest.getRequestURI(), userId.isEmpty() ? "[UNKNOWN USER]" : userId);
            if (authenticationService.isUserHasOtherOpenSessions(userId, session)) {
                authenticationService.finishUserSessions(userId, session);
            }
            if (!userId.isEmpty() && !authenticationService.isUserSessionStarted(userId, session)) {
                authenticationService.startUserSession(httpRequest);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
