<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fun" uri="/WEB-INF/tld/ctl" %>

<jsp:useBean id="list" scope="request" type="java.util.List<com.conference.data.entity.Report>"/>

<tag:page name="Reports">
    Report list

    <table class="table data-table" id="reports">
        <thead>
        <tr>
            <th>#</th>
            <th>Theme</th>
            <th>Place</th>
            <th>Reporter</th>
            <th>Start</th>
            <th>End</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list}">
            <tr>
                <td>${item.id}</td>
                <td>${item.theme}</td>
                <td>${item.place.name}</td>
                <td>${item.reporter.name}
                <td>${fun:formatWith(item.startTime, 'HH:mm')}
                <td>${fun:formatWith(item.endTime, 'HH:mm')}
                    <tag:if-role is="ADMIN">
                        <tag:table-actions edit="${base}/reports/${item.id}"
                                           delete="${base}/reports/delete/${item.id}"/>
                    </tag:if-role>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <tag:if-role is="ADMIN,MODERATOR">
        <a class="data-table-menu dropdown-item" href="${base}/reports/add">Add</a>
    </tag:if-role>
</tag:page>
