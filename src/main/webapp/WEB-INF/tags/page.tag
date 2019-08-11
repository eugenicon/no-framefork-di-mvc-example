<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@attribute name="name" required="true" %>
<%@attribute name="hideNavBar" %>

<c:set var="base" value="${pageContext.request.contextPath}" scope="application"/>
<c:if test="">
    <jsp:useBean id="auth" type="com.conference.service.AuthenticatedUser" />
</c:if>

<html>
<head>
    <title>${name}</title>
    <link rel="icon" href="${base}/static/img/favicon.png" />

    <link rel="stylesheet" href="${base}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/webjars/font-awesome/5.9.0/css/all.css">
    <link rel="stylesheet" href="${base}/static/css/styles.css">
    <script src="${base}/static/js/scripts.js"></script>
    <script src="${base}/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="${base}/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="${base}/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <tag:date-time-picker/>
    <tag:data-table/>
</head>
<body style="padding-top: 70px;">

<c:if test="${!hideNavBar}">
    <nav class="navbar navbar-expand navbar-light bg-light fixed-top">
        <a class="navbar-brand" href="#">Navbar</a>
        <div class="navbar-nav">
            <tag:nav-item url="${base}/" label="Home" fullMatch="true"/>
            <tag:nav-item url="${base}/conferences" label="Conferences"/>
            <tag:if-role is="ADMIN,MODERATOR">
                <tag:nav-item url="${base}/reports" label="Reports"/>
            </tag:if-role>
            <tag:if-role is="ADMIN">
                <tag:nav-item url="${base}/users" label="Users"/>
                <tag:nav-item url="${base}/locations" label="Locations"/>
            </tag:if-role>
            <tag:if-role isNot="UNKNOWN">
                <tag:nav-item url="${base}/orders" label="My Orders"/>
            </tag:if-role>
        </div>
        <div class="navbar-nav ml-auto">

            <tag:if-role isNot="UNKNOWN">
                <div style="margin: 7px;">${auth.name}</div>
            </tag:if-role>

            <div class="btn-group" >
                <tag:if-role is="UNKNOWN">
                    <a class="btn btn-outline-secondary" href="${base}/register">Sign up</a>
                    <a class="btn btn-outline-primary" href="${base}/login">Sign in</a>
                </tag:if-role>
                <tag:if-role isNot="UNKNOWN">
                    <a class="btn btn-outline-primary" href="${base}/logout">Sign out</a>
                </tag:if-role>
            </div>
        </div>
    </nav>
</c:if>

<div class="container">
    <jsp:doBody/>
</div>

<tag:confirm-modal/>

</body>
</html>
