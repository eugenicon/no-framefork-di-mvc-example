<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="item" type="java.lang.Object" %>
<%@attribute name="property" %>
<%@attribute name="propertyKey" %>
<%@attribute name="propertyView" %>
<%@attribute name="list" type="java.util.List<java.lang.Object>"%>

<c:set var="valueKey" value="${propertyKey == null ? item[property] : item[property][propertyKey]}"/>
<c:set var="valueView" value="${propertyView == null ? item[property] : item[property][propertyView]}"/>

<select class="custom-select" id="${property}" name="${property}" >
    <c:if test="${valueKey == null}">
        <option disabled hidden selected>Select value...</option>
    </c:if>
    <c:if test="${valueKey != null}">
        <option selected value="${valueKey}" >${valueView}</option>
    </c:if>
    <c:forEach var="element" items="${list}">
        <c:set var="elementKey" value="${propertyKey == null ? element : element[propertyKey]}"/>
        <c:set var="elementView" value="${propertyView == null ? element : element[propertyView]}"/>

        <c:if test="${elementKey != valueKey}">
            <option value="${elementKey}" >${elementView}</option>
        </c:if>
    </c:forEach>
</select>