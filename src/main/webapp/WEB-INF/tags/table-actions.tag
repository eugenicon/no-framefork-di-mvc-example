<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ attribute name="edit" %>
<%@ attribute name="delete" %>

<div class="table-options">
    <i class="fas fa-pen" onclick="get('${edit}')"></i>
    <tag:confirm-button onConfirm="post('${delete}')">
        <i class="fas fa-times"></i>
    </tag:confirm-button>
</div>