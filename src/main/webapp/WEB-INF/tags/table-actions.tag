<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="edit" %>
<%@ attribute name="view" %>
<%@ attribute name="delete" %>

<div class="table-options">
    <c:if test="${not empty view}">
        <i class="fas fa-eye zoom" onclick="get('${view}')" rel="tooltip" title="View"></i>
    </c:if>
    <c:if test="${not empty edit}">
        <i class="fas fa-pen zoom" onclick="get('${edit}')" rel="tooltip" title="Edit"></i>
    </c:if>
    <c:if test="${not empty delete}">
        <i class="fas fa-times confirm-action zoom" onclick="post('${delete}')" rel="tooltip" title="Delete"></i>
    </c:if>
</div>