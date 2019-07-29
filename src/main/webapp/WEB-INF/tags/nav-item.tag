<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="url" required="true" %>
<%@attribute name="label" required="true" %>
<%@attribute name="fullMatch" %>

<c:set var="currentUrl" value="${base}${requestScope['javax.servlet.forward.servlet_path']}" />
<c:set var="isActive" value="${fullMatch ? currentUrl.endsWith(url): currentUrl.contains(url)}"/>
<a class="nav-item nav-link ${isActive ? 'active' : ''}" href="${url}">${label}</a>