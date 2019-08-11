<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fun" uri="/WEB-INF/tld/ctl" %>

<c:if test="">
    <jsp:useBean id="conferenceId" type="java.lang.Integer" />
    <jsp:useBean id="item" type="com.conference.data.entity.Report" />
    <jsp:useBean id="locations" type="java.util.List<com.conference.data.entity.Location>" />
    <jsp:useBean id="users" type="java.util.List<com.conference.data.entity.User>" />
</c:if>

<tag:page name="Edit Report">
    Edit

    <form method="post" action="${base}/reports/save" class="form" role="form">
        <input type="hidden" id="id" name="id" value="${item.id}">
        <input type="hidden" id="conferenceId" name="conferenceId" value="${conferenceId}">
        <div class="form-group">
            <label for="theme">Theme:</label>
            <input type="text" class="form-control" id="theme" name="theme" placeholder="Specify theme" value="${item.theme}">
            <tag:validation field="theme"/>
        </div>
        <div class="form-group">
            <label for="startTime">Start:</label>
            <input id="startTime" name="startTime" class="form-control date-picker" time value="${fun:formatWith(item.startTime, 'HH:mm')}"/>
            <tag:validation field="startTime"/>
        </div>
        <div class="form-group">
            <label for="endTime">End:</label>
            <input id="endTime" name="endTime" class="form-control date-picker" time value="${fun:formatWith(item.endTime, 'HH:mm')}" />
            <tag:validation field="endTime"/>
        </div>
        <div class="form-group">
            <label for="place">Place:</label><%--@declare id="place"--%>
            <tag:select property="place" item="${item}" propertyKey="id" propertyView="name" list="${locations}"/>
            <tag:validation field="place"/>
        </div>
        <div class="form-group">
            <label for="reporter">Reporter:</label><%--@declare id="reporter"--%>
            <tag:select property="reporter" item="${item}" propertyKey="id" propertyView="name" list="${users}"/>
            <tag:validation field="reporter"/>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <textarea class="form-control" rows="5" id="description" name="description">${item.description}</textarea>
            <tag:validation field="description"/>
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</tag:page>
