<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="">
    <jsp:useBean id="item" type="com.conference.data.dto.UserLoginDto" />
</c:if>

<tag:page name="Login" hideNavBar="true">
    <div class="row text-center justify-content-center vertical-padding">
        <form class="form-group col-4" method="post" action="">
            <img class="mb-4" src="${base}/static/img/accountlogin-icon.png" alt="" width="72" height="72">
            <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>

            <div class="form-group">
                <label for="userName" class="sr-only">Username</label>
                <input type="email" id="userName" name="userName" class="form-control" placeholder="Email address" required autofocus value="${item.userName}">
                <label for="password" class="sr-only">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Password" required value="${item.password}">
            </div>

            <div class="form-group">
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </div>
        </form>
    </div>
</tag:page>