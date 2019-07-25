<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test=""><jsp:useBean id="item" class="com.conference.data.entity.Report" /></c:if>

<tag:page name="Reports">
    Edit

    <form method="post" action="${base}/reports/save" class="form" role="form">
        <input type="hidden" id="id" name="id" value="${item.id}">
        <div class="form-group">
            <label for="theme">Theme:</label>
            <input type="text" class="form-control" id="theme" name="theme" placeholder="Specify theme" value="${item.theme}">
            <tag:validation field="theme"/>
        </div>
        <div class="form-group">
            <label for="place">Place:</label>
            <input type="text" class="form-control" id="place" name="place" placeholder="Choose place" value="${item.place}">
            <tag:validation field="place"/>
        </div>
        <div class="form-group">
            <label for="reporter">Reporter:</label>
            <input type="text" class="form-control" id="reporter" name="reporter" placeholder="Choose reporter" value="${item.reporter}">
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
