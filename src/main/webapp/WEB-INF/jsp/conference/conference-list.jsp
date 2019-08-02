<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fun" uri="/WEB-INF/tld/ctl" %>

<jsp:useBean id="list" scope="request" type="java.util.List<com.conference.data.entity.Conference>"/>

<tag:page name="Conferences">
    Conferences list

    <table class="table" id="conferences">
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
                    <tag:table-actions edit="${base}/conferences/${item.id}"
                                       delete="${base}/conferences/delete/${item.id}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <tag:data-table table="conferences" menu="true">
        <a class="dropdown-item" href="${base}/conferences/add">Add</a>
    </tag:data-table>
</tag:page>
