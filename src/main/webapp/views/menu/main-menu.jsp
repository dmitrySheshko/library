<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default">
    <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
            <li>
                <a href="/">Home</a>
            </li>
            <li>
                <a href="/books">Books</a>
            </li>
            <c:if test="${sessionUser != null && sessionUser.role != 3}">
                <li>
                    <a href="/orders">Orders</a>
                </li>
                <li>
                    <a href="/categories">Categories</a>
                </li>
                <li>
                    <a href="/add-book">Add book</a>
                </li>
                <li>
                    <a href="/add-category">Add category</a>
                </li>
            </c:if>
            <c:if test="${sessionUser != null && sessionUser.role == 1}">
                <li>
                    <a href="/auth/sign-up">Registration new user</a>
                </li>
            </c:if>
        </ul>
        <ul class="nav navbar-nav pull-right">
            <li>                 
                <c:if test="${sessionUser != null}">
                	<a href="/office">Hello, ${sessionUser.firstName} ${sessionUser.lastName}</a>
                </c:if>
                <c:if test="${sessionUser == null}">
					<a href="javascript:void(0);">Hello, Guest</a>
				</c:if>
            </li>
            <%-- If guest --%>
            <c:if test="${sessionUser == null}">
                <li>
                    <a href="/auth/sign-in">Sign in</a>
                </li>
                <li>
                    <a href="/auth/sign-up">Sign up</a>
                </li>
            </c:if>
            <%-- If user logined in --%>
            <c:if test="${sessionUser != null}">
                <li>
                    <a href="/auth/sign-out">Sign out</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>