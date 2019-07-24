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
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${list}">
            <tr>
                <td>${item.id}</td>
                <td>${item.theme}</td>
                <td>${item.place}</td>
                <td>${item.reporter}</td>

                <td class="tr-table-options">
                    <div class="table-options">
                        <i class="fas fa-pen" onclick="get('${base}/reports/${item.id}')"></i>
                        <tag:confirm-button onConfirm="post('${base}/reports/delete/${item.id}')">
                            <i class="fas fa-times"></i>
                        </tag:confirm-button>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <tag:data-table table="reports" options="true">
        <a class="dropdown-item" href="${base}/reports/add">Add</a>
    </tag:data-table>
</tag:page>
