<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fun" uri="/WEB-INF/tld/ctl" %>

<jsp:useBean id="list" scope="request" type="java.util.List<com.conference.data.entity.Conference>"/>

<tag:page name="Conferences">
    Conferences list

    <table class="table data-table" id="conferences">
        <thead>
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Date</th>
            <th>Moderator</th>
            <th>Tickets</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list}">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${fun:formatWith(item.date, 'MM/dd/yyyy')}</td>
                <td>${item.moderator.name}
                <td>${item.totalTickets}
                    <tag:if-role is="ADMIN">
                        <tag:table-actions edit="${base}/conferences/${item.id}"
                                           delete="${base}/conferences/delete/${item.id}"/>
                    </tag:if-role>
                </td>
            </tr>
        </c:forEach>
        </tbody>

        <tag:if-role is="ADMIN,MODERATOR">
            <a class="data-table-menu dropdown-item" href="${base}/conferences/add">Add</a>
        </tag:if-role>
    </table>
</tag:page>
