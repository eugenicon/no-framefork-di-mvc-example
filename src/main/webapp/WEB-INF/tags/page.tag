<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@attribute name="name" required="true" %>
<%@attribute name="hideNavBar" %>

<c:set var="base" value="${pageContext.request.contextPath}" scope="application"/>

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
</head>
<body>

<c:if test="${!hideNavBar}">
    <nav class="navbar navbar-expand navbar-light bg-light">
        <a class="navbar-brand" href="#">Navbar</a>
        <div class="navbar-nav">
            <tag:nav-item url="${base}/" label="Home" fullMatch="true"/>
            <tag:nav-item url="${base}/users" label="Users"/>
            <tag:nav-item url="${base}/locations" label="Locations"/>
            <tag:nav-item url="${base}/reports" label="Reports"/>
            <tag:nav-item url="${base}/test-400" label="404"/>
            <tag:nav-item url="${base}/test-500" label="500"/>
        </div>
    </nav>
</c:if>

<div class="container">
    <jsp:doBody/>
</div>

<tag:confirm-modal/>

</body>
</html>
