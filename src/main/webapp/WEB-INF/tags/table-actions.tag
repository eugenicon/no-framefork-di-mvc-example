<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ attribute name="edit" %>
<%@ attribute name="delete" %>

<div class="table-options">
    <i class="fas fa-pen" onclick="get('${edit}')"></i>
    <i class="fas fa-times confirm-action" onclick="post('${delete}')"></i>
</div>