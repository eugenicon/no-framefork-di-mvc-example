package com.conference.servlet;

import com.conference.ComponentResolver;
import com.conference.servlet.annotation.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(DispatcherServlet.URL + "/*")
public class DispatcherServlet extends HttpServlet {
    public static final String URL = "/mvc";
    private static final Logger LOGGER = LogManager.getLogger(DispatcherServlet.class);
    private static final String REDIRECT_PARAMS = "REDIRECT_PARAMS";
    private RequestResolver requestResolver;

    @Override
    public void init() {
        List<?> controllers = ComponentResolver.getAnnotatedComponents(Controller.class);
        requestResolver = ComponentResolver.getComponent(RequestResolver.class);
        requestResolver.register(controllers, getServletContext().getContextPath() + URL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resolve(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resolve(req, resp);
    }

    private void resolve(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            LOGGER.info("{} {}, parameters: {}", req.getMethod(), req.getRequestURI(), req.getParameterMap());
            if (req.getSession().getAttribute(REDIRECT_PARAMS) != null) {
                readRedirectionAttributes(req);
            }
            String targetUrl = requestResolver.resolve(req);
            processUrl(targetUrl.isEmpty() ? "redirect:/404" : targetUrl, req, resp);
        } catch (Exception e) {
            Throwable cause = e.getCause() == null ? e : e.getCause();
            LOGGER.error(cause.getMessage(), cause);
            req.setAttribute("exception", cause);
            processUrl("redirect:/500", req, resp);
        }
    }

    private void processUrl(String url, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String targetUrl = (url.endsWith(".jsp") ? "/WEB-INF/jsp/" : "") + url;
        if (targetUrl.startsWith("redirect:")) {
            writeRedirectionAttributes(req);
            String redirectUrl = targetUrl.substring(9);
            String location = redirectUrl.startsWith("/") ? req.getContextPath() : "";
            LOGGER.info("Redirecting to {}{}", location, redirectUrl);
            resp.sendRedirect(location + redirectUrl);
        } else {
            LOGGER.info("Forwarding to {}", targetUrl);
            req.getRequestDispatcher(targetUrl).forward(req, resp);
        }
    }

    private void writeRedirectionAttributes(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        Enumeration<String> names = request.getAttributeNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            params.put(key, request.getAttribute(key));
        }
        request.getSession().setAttribute(REDIRECT_PARAMS, params);
    }

    @SuppressWarnings("unchecked")
    private void readRedirectionAttributes(HttpServletRequest request) {
        Map<String, Object> params = (Map<String, Object>) request.getSession().getAttribute(REDIRECT_PARAMS);
        params.forEach(request::setAttribute);
        request.getSession().removeAttribute(REDIRECT_PARAMS);
    }
}
