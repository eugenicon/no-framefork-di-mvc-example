package com.conference.servlet.filter;

import com.conference.ComponentResolver;
import com.conference.data.entity.Role;
import com.conference.service.AuthenticatedUser;
import com.conference.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@WebFilter("*")
public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);
    private final Map<Role, Function<HttpServletRequest, String>> rolePageRestrictions = new HashMap<>();
    private final AuthenticationService authenticationService = ComponentResolver.getComponent(AuthenticationService.class);
    private List<String> allowedForAll;

    @Override
    public void init(FilterConfig filterConfig) {
        String base = filterConfig.getServletContext().getContextPath();
        Properties properties = ComponentResolver.getComponent(Properties.class);
        allowedForAll = Arrays.stream(properties.getProperty("url.allowed.all", "/.*").split(",")).map(base::concat).collect(Collectors.toList());
        for (Role role : Role.values()) {
            String target = base + (role == Role.UNKNOWN ? "/login" : "/error/403");
            String value = properties.getProperty("url.allowed." + role.name().toLowerCase(), target);
            List<String> allowedUrls = Arrays.stream(value.split(",")).map(base::concat).collect(Collectors.toList());
            rolePageRestrictions.put(role, req -> isAllowedRequest(allowedUrls, req) ? "" : target);
        }
    }

    private boolean isAllowedRequest(List<String> allowedUrls, HttpServletRequest req) {
        return allowedUrls.stream().anyMatch(req.getRequestURI()::matches);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        if (isAllowedRequest(allowedForAll, httpRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            Role role = authenticationService.getAuthentication(httpRequest.getSession()).map(AuthenticatedUser::getRole).orElse(Role.UNKNOWN);
            String redirectUrl = rolePageRestrictions.get(role).apply(httpRequest);

            if (redirectUrl.isEmpty()) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                LOGGER.debug("user with role {} redirected to {}", role, redirectUrl);
                ((HttpServletResponse) servletResponse).sendRedirect(redirectUrl);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
