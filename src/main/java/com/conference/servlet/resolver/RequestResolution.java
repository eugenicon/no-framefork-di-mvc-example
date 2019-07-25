package com.conference.servlet.resolver;

import javax.servlet.http.HttpServletRequest;

public class RequestResolution {
    private final HttpServletRequest req;
    private Exception exception;
    private String url = "";
    private Object[] arguments;

    public RequestResolution(HttpServletRequest req) {
        this.req = req;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public boolean isResolved() {
        return !url.isEmpty();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpServletRequest getRequest() {
        return req;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public boolean hasException() {
        return exception != null;
    }
}
