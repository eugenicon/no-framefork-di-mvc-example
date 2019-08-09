<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fun" uri="/WEB-INF/tld/ctl" %>

<tag:page name="Error" hideNavBar="true">
    <div class="page-wrap d-flex flex-row align-items-center vertical-center">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-12 text-center">
                    <span class="display-1 d-block">500</span>
                    <div class="mb-4 lead">Oops! Something went really wrong...
                        <c:if test="${exception != null}">
                            <div >${exception.getMessage() == null ? exception : exception.getMessage()}</div>
                        </c:if>
                    </div>
                    <a href="${base}/" class="btn btn-link">Back to Home</a>
                    <c:if test="${exception != null}">
                        <a data-toggle="collapse" href="#exception-stack-trace" class="btn btn-link">Stacktrace</a>
                    </c:if>
                </div>
                <c:if test="${exception != null}">
                    <div id="exception-stack-trace" class="panel-collapse collapse">
                        <div class="card card-body overflow-auto pre-line" style="max-height: 315px;">${fun:stacktrace(exception)}</div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</tag:page>
