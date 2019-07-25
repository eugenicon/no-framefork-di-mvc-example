<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="field" required="true" %>

<c:if test=""><jsp:useBean id="validationResult" class="com.conference.validation.ValidationResult" /></c:if>

<c:if test="${validationResult.hasError(field)}">
    <div class="alert alert-danger" role="alert">
        ${validationResult.getError(field)}
    </div>
</c:if>