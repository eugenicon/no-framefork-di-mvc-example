package com.conference.servlet;

import com.conference.config.ComponentConfig;
import com.conference.servlet.annotation.Controller;

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
    private static final String REDIRECT_PARAMS = "REDIRECT_PARAMS";
    private RequestResolver requestResolver;

    @Override
    public void init() {
        ComponentConfig componentConfig = new ComponentConfig();
        List<?> controllers = componentConfig.getAnnotatedComponents(Controller.class);
        requestResolver = componentConfig.getComponent(RequestResolver.class);
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
            if (req.getSession().getAttribute(REDIRECT_PARAMS) != null) {
                readRedirectionAttributes(req);
            }
            String targetUrl = requestResolver.resolve(req);
            processUrl(targetUrl.isEmpty() ? "redirect:/404" : targetUrl, req, resp);
        } catch (Exception e) {
            req.setAttribute("exception", e.getCause() == null ? e : e.getCause());
            processUrl("redirect:/500", req, resp);
        }
    }

    private void processUrl(String targetUrl, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (targetUrl.startsWith("redirect:")) {
            writeRedirectionAttributes(req);
            String redirectUrl = targetUrl.substring(9);
            String location = redirectUrl.startsWith("/") ? req.getContextPath() : "";
            resp.sendRedirect(location + redirectUrl);
        } else {
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
