<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test=""><jsp:useBean id="item" class="com.conference.data.entity.Location" /></c:if>

<tag:page name="Edit Location">
    Edit

    <form method="post" action="${base}/locations/save" class="form" role="form">
        <input type="hidden" id="id" name="id" value="${item.id}">
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Specify name" value="${item.name}">
            <tag:validation field="name"/>
        </div>
        <div class="form-group">
            <label for="places">Place:</label>
            <input type="number" class="form-control" id="places" name="places" placeholder="Set total places" value="${item.places}">
            <tag:validation field="places"/>
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</tag:page>
