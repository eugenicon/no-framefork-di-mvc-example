<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="list" scope="request" type="java.util.List<com.conference.data.entity.Report>"/>

<tag:page name="Reports">
    Report list

    <table class="table" id="reports">
        <thead>
        <tr>
            <th>#</th>
            <th>Theme</th>
            <th>Place</th>
            <th>Reporter</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list}">
            <tr>
                <td>${item.id}</td>
                <td>${item.theme}</td>
                <td>${item.place.name}</td>
                <td>${item.reporter.name}
                    <tag:table-actions edit="${base}/reports/${item.id}"
                                       delete="${base}/reports/delete/${item.id}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <tag:data-table table="reports" menu="true">
        <a class="dropdown-item" href="${base}/reports/add">Add</a>
    </tag:data-table>
</tag:page>
