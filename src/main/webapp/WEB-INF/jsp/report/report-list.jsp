<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="list" scope="request" type="java.util.List<com.conference.data.entity.Report>"/>

<tag:page name="Reports">
    Report list

    <table class="table">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Theme</th>
            <th scope="col">Place</th>
            <th scope="col">Reporter</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list}">
            <tr ondblclick="location.href+='/${item.id}'">
                <td>${item.id}</td>
                <td>${item.theme}</td>
                <td>${item.place}</td>
                <td>${item.reporter}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</tag:page>
