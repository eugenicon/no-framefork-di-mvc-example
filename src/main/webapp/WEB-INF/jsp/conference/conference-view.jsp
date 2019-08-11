<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fun" uri="/WEB-INF/tld/ctl" %>

<c:if test="">
    <jsp:useBean id="item" type="com.conference.data.dto.ConferenceDto"/>
    <jsp:useBean id="users" type="java.util.List<com.conference.data.entity.User>"/>
    <jsp:useBean id="reports" type="java.util.List<com.conference.data.entity.Report>"/>
</c:if>

<tag:page name="View Conference">

    <div class="row">
        <div class="jumbotron col-12 row">
            <div class="col-8">
                <h1 class="display-3">${item.name}</h1>
            </div>

            <div class="card mb-4 box-shadow text-center col-4" style="margin-bottom: -100px !important;">
                <div class="card-header">
                    <h4 class="my-0 font-weight-normal">${item.date}</h4>
                </div>
                <div class="card-body">
                    <h1 class="card-title">${item.remainingTickets} <small class="text-muted"> tickets left</small></h1>
                    <ul class="list-unstyled mt-3 mb-4">
                        <li>${item.purchasedTickets} users already going</li>
                    </ul>
                    <c:if test="${item.totalTickets > item.purchasedTickets}">
                        <p><a class="btn btn-lg btn-success" href="${base}/conferences/order/${item.id}" role="button" >Order Ticket</a></p>
                    </c:if>
                    <c:if test="${item.totalTickets == item.purchasedTickets}">
                        <p><a class="btn btn-lg btn-block border-info" role="button" >Sold Out!</a></p>
                    </c:if>
                </div>
            </div>
        </div>

    </div>

    <p class="conference-title">About event</p>

    <p class="pre-line">${item.description}</p>

    <c:if test="${!reports.isEmpty()}">
        <p class="conference-title">Agenda</p>

        <c:forEach var="report" items="${reports}">
            <div class="media text-muted pt-3 row border-bottom" style="padding-left: 12px">
                <img data-src="holder.js/32x32?theme=thumb&amp;bg=007bff&amp;fg=007bff&amp;size=1" alt="32x32" class="mr-2 rounded" style="width: 32px; height: 32px;" src="data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%2232%22%20height%3D%2232%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%2032%2032%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_16c73181d79%20text%20%7B%20fill%3A%23007bff%3Bfont-weight%3Abold%3Bfont-family%3AArial%2C%20Helvetica%2C%20Open%20Sans%2C%20sans-serif%2C%20monospace%3Bfont-size%3A2pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_16c73181d79%22%3E%3Crect%20width%3D%2232%22%20height%3D%2232%22%20fill%3D%22%23007bff%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%2211.5390625%22%20y%3D%2216.9%22%3E32x32%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E" data-holder-rendered="true">

                <div class="col-3">
                    <p class="media-body pb-3 mb-0 small">
                            ${fun:formatWith(report.startTime, 'HH:mm')} - ${fun:formatWith(report.endTime, 'HH:mm')}
                        <strong class="d-block">${report.reporter.name}</strong>
                            ${report.theme}
                    </p>
                </div>
                <div class="col-7">
                        ${report.description}
                </div>

                <div class="col-1">
                    <tag:table-actions edit="${base}/reports/${item.id}"
                                       delete="${base}/reports/delete/${item.id}"/>
                </div>

            </div>
        </c:forEach>
    </c:if>

    <tag:if-role is="ADMIN,MODERATOR">
        <div class="floating-top-corner">

            <button class="btn navbar-light btn-circle border-info" data-toggle="dropdown" style="padding: 1px 3px;" aria-expanded="false">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="dropdown-menu dropdown-menu-right" x-placement="bottom-end"
                 style="position: absolute; transform: translate3d(305px, 28px, 0px); top: 0px; left: 0px; will-change: transform;">

                <a class="dropdown-item" href="${base}/conferences/${item.id}">Edit</a>
                <a class="dropdown-item" href="${base}/reports/add?conf=${item.id}">Add Report</a>

            </div>
        </div>
    </tag:if-role>

</tag:page>
