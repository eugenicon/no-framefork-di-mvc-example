<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:page name="Forbidden" hideNavBar="true">
    <div class="page-wrap d-flex flex-row align-items-center vertical-center">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-12 text-center">
                    <span class="display-1 d-block">403</span>
                    <div class="mb-4 lead">Access Forbidden
                        <div >I'm sorry buddy...</div>
                    </div>
                    <a href="${base}/" class="btn btn-link">Back to Home</a>
                </div>
            </div>
        </div>
    </div>
</tag:page>
