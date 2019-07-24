<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:page name="Not Found" hideNavBar="true">
    <div class="page-wrap d-flex flex-row align-items-center" style="padding-top: 15%;">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-12 text-center">
                    <span class="display-1 d-block">404</span>
                    <div class="mb-4 lead">The page you are looking for was not found.</div>
                    <a href="${base}/" class="btn btn-link">Back to Home</a>
                </div>
            </div>
        </div>
    </div>
</tag:page>
