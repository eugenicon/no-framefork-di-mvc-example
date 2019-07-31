<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="list" scope="request" type="java.util.List<com.conference.data.entity.Location>"/>

<tag:page name="Locations">
    Locations list

    <table class="table" id="locations">
        <thead>
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Places</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list}">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.places}
                    <tag:table-actions edit="${base}/locations/${item.id}"
                                       delete="${base}/locations/delete/${item.id}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <tag:data-table table="locations" menu="true">
        <a class="dropdown-item" href="${base}/locations/add">Add</a>
    </tag:data-table>
</tag:page>
