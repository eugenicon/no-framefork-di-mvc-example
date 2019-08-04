package com.conference.servlet.filter;

import com.conference.servlet.DispatcherServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebFilter("*")
public class DispatcherFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(DispatcherFilter.class);
    private List<String> openResources;
    private String contextPath;

    @Override
    public void init(FilterConfig filterConfig) {
        contextPath = filterConfig.getServletContext().getContextPath();
        openResources = Stream.of("/static/.*", "/webjars/.*")
                .map(contextPath::concat).collect(Collectors.toList());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String url = ((HttpServletRequest) request).getRequestURI();
        if (openResources.stream().noneMatch(url::matches)) {
            String mvcUrl = DispatcherServlet.URL + url.substring(contextPath.length());
            LOGGER.info("Forwarding to {}", url);
            request.getRequestDispatcher(mvcUrl).forward(request, response);
        } else {
            LOGGER.info("Getting resource {}", url);
            chain.doFilter(request, response);
        }
    }
}
