<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fun" uri="/WEB-INF/tld/ctl" %>

<jsp:useBean id="list" scope="request" type="java.util.List<com.conference.data.dto.ConferenceDto>"/>

<tag:page name="Conferences">

    <div class="row d-flex justify-content-center">
    <c:forEach var="item" items="${list}">

        <div class="card item-card toggle-visibility">
            <div class="card-header jumbotron">

            </div>
            <div class="card-body">
                <small class="text-muted">${fun:formatWith(item.date, 'MM/dd/yyyy')}</small>
                <h4 class="card-title">${item.name}</h4>
                <h5 class="card-title">${item.remainingTickets} <small class="text-muted"> tickets left</small></h5>
            </div>

            <div class="overlay text-center fill">
                <div style="display: flex; flex-direction: column; padding: 10px; justify-content: center; height: 170px;">
                    <a href="${base}/conferences/view-${item.id}" class="btn btn-lg btn-white">View</a>
                    <tag:if-role is="ADMIN,MODERATOR">
                        <a href="${base}/conferences/${item.id}" class="btn btn-lg btn-white">Edit</a>
                        <a href="#" class="confirm-action btn btn-lg btn-white" onclick="post('${base}/conferences/delete/${item.id}')">Delete</a>
                    </tag:if-role>
                </div>

                <div style="color: wheat; text-align: left; position: absolute; top: 181px; left: 20px;">
                    <small >${fun:formatWith(item.date, 'MM/dd/yyyy')}</small>
                    <h4 class="card-title">${item.name}</h4>
                    <h5 class="card-title">${item.remainingTickets} <small > tickets left</small></h5>
                </div>
            </div>
        </div>

    </c:forEach>
    </div>

    <tag:if-role is="ADMIN,MODERATOR">
        <a class="floating-bottom-corner btn btn-primary" href="${base}/conferences/add" >Add</a>
    </tag:if-role>

</tag:page>
