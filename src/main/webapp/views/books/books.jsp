<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="/views/header/header.jsp" %>
<body>
    <%@ include file="/views/menu/main-menu.jsp" %>
    <div class="page">
    	<h1>Books</h1>
    	<%@ include file="/views/books/filter/filter.jsp" %>
    	
    	<c:if test="${sessionUser != null && sessionUser.role != 'READER'}">
    		<a href="/add-book">Add book</a>
    	</c:if>
    	
    	<form action="/order" method="post">
	        <table class="table table-striped table-hover">
	            <tr>
	                <th></th>
	                <th>Title</th>
	                <th>Author</th>
	                <th>Catalogue</th>
	                <th>Available count</th>
	            </tr>
	            <c:forEach items="${books}" var="book">
	                <tr>
	                    <td>
	                        <input type="checkbox" name="bookIds" value="${book.id}" />
	                    </td>
	                    <td>${book.title}</td>
	                    <td>${book.author}</td>
	                    <td>${book.catalogue}</td>
	                    <td>
	                        ${book.count}
	                        <br/>
	                        Заказать/Книги нет в наличии
	                        <br/>
	                        если библиотекарь - редактировать и инфу по книге
	                    </td>
	                </tr>
	            </c:forEach>
	        </table>
	    </form>
    </div>
</body>
</html>