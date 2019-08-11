<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:page name="Welcome">

    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner" role="listbox">
            <div class="carousel-item active" style="background-image: url('${base}/static/img/welcome-carousel/events.jpg')">
                <div class="carousel-caption d-none d-md-block">
                    <h2 class="display-4">Have Fun</h2>
                    <p class="lead">on any type of events you can imagine</p>
                </div>
            </div>
            <div class="carousel-item" style="background-image: url('${base}/static/img/welcome-carousel/friends.jpg')">
                <div class="carousel-caption d-none d-md-block">
                    <h2 class="display-4">Meet Friends</h2>
                    <p class="lead">within great social communities</p>
                </div>
            </div>
            <div class="carousel-item" style="background-image: url('${base}/static/img/welcome-carousel/inspire.jpg')">
                <div class="carousel-caption d-none d-md-block">
                    <h2 class="display-4">Inspire People</h2>
                    <p class="lead">and make the whole world better</p>
                </div>
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

</tag:page>