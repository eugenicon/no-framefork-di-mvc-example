<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fun" uri="/WEB-INF/tld/ctl" %>

<c:if test="">
    <jsp:useBean id="item" type="com.conference.data.entity.Conference" />
    <jsp:useBean id="users" type="java.util.List<com.conference.data.entity.User>" />
</c:if>

<tag:page name="Edit Conference">
    Edit

    <form method="post" action="${base}/conferences/save" class="form" role="form" >
        <input type="hidden" id="id" name="id" value="${item.id}">
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Specify name" value="${item.name}">
            <tag:validation field="name"/>
        </div>
        <div class="form-group">
            <label for="totalTickets">Total tickets:</label>
            <input type="number" class="form-control" id="totalTickets" name="totalTickets" placeholder="Specify the number of available tickets" value="${item.totalTickets}">
            <tag:validation field="totalTickets"/>
        </div>
        <div class="form-group">
            <label for="date">Start:</label>
            <input id="date" name="date" class="form-control date-picker" date value="${fun:formatWith(item.date, 'MM/dd/yyyy')}"/>
            <tag:validation field="date"/>
        </div>
        <div class="form-group">
            <label for="moderator">Reporter:</label><%--@declare id="moderator"--%>
            <tag:select property="moderator" item="${item}" propertyKey="id" propertyView="name" list="${users}"/>
            <tag:validation field="moderator"/>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <textarea class="form-control" rows="5" id="description" name="description">${item.description}</textarea>
            <tag:validation field="description"/>
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</tag:page>
