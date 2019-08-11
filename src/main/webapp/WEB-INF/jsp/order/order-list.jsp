<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fun" uri="/WEB-INF/tld/ctl" %>

<jsp:useBean id="list" scope="request" type="java.util.List<com.conference.data.entity.Order>"/>

<tag:page name="My Orders">
    My Orders

    <table class="table data-table" id="orders" order="2-desc">
        <thead>
        <tr>
            <th>#</th>
            <th>Conference</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list}">
            <tr>
                <td>${item.id}</td>
                <td>
                    <a href="${base}/conferences/view-${item.conference.id}">${item.conference.name}</a>
                </td>
                <td>${fun:format(item.date)}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</tag:page>
