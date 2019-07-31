<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="">
    <jsp:useBean id="item" type="com.conference.data.entity.User" />
    <jsp:useBean id="roles" type="java.util.List<com.conference.data.entity.Role>" />
</c:if>

<tag:page name="Edit User">
    Edit

    <form method="post" action="${base}/users/save" class="form" role="form">
        <input type="hidden" id="id" name="id" value="${item.id}">
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Specify name" value="${item.name}">
            <tag:validation field="name"/>
        </div>
        <div class="form-group">
            <label for="role">Role:</label><%--@declare id="role"--%>
            <tag:select property="role" item="${item}" list="${roles}"/>
            <tag:validation field="role"/>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Set total places" value="${item.email}">
            <tag:validation field="email"/>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="Set total places" value="${item.password}">
            <tag:validation field="password"/>
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</tag:page>
