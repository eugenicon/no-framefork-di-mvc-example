<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@attribute name="name" %>
<%@attribute name="hideNavBar" %>

<c:set var="base" value="${pageContext.request.contextPath}" scope="application"/>

<html>
<head>
    <title>${name}</title>

    <link rel="stylesheet" href="${base}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/webjars/font-awesome/5.9.0/css/all.css">
    <link rel="stylesheet" href="${base}/static/css/styles.css">
    <script src="${base}/static/js/scripts.js"></script>
    <script src="${base}/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="${base}/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="${base}/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</head>
<body>

<tag:confirm-modal/>

<c:if test="${!hideNavBar}">
    <nav class="navbar navbar-expand navbar-light bg-light">
        <a class="navbar-brand" href="#">Navbar</a>
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="${base}/">Home</a>
            <a class="nav-item nav-link" href="${base}/reports">Reports</a>
            <a class="nav-item nav-link" href="${base}/test-400">404</a>
            <a class="nav-item nav-link" href="${base}/test-500">500</a>
        </div>
    </nav>
</c:if>

<div class="container">
    <jsp:doBody/>
</div>

</body>
</html>
