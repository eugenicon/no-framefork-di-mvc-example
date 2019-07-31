<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="list" scope="request" type="java.util.List<com.conference.data.entity.User>"/>

<tag:page name="Users">
    User list

    <table class="table" id="users">
        <thead>
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list}">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.email}</td>
                <td>${item.role}
                    <tag:table-actions edit="${base}/users/${item.id}"
                                       delete="${base}/users/delete/${item.id}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <tag:data-table table="users" menu="true">
        <a class="dropdown-item" href="${base}/users/add">Add</a>
    </tag:data-table>
</tag:page>
