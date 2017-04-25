<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/views/header/header.jsp"%>
<body>
	<div>
		<%@ include file="/views/menu/main-menu.jsp"%>
		<div class="page">
			<h1>Books</h1>
			<%@ include file="/views/books/filter/filter.jsp"%>

			<c:if test="${sessionUser != null && sessionUser.role != 3}">
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
							<td><input type="checkbox" name="bookIds" value="${book.id}" />
							</td>
							<td>
								<a href="/book/${book.id}">${book.title}</a>
							</td>
							<td>${book.author}</td>
							<td>${book.category.name}</td>
							<td>Count ???? <br /> Заказать/Книги нет в наличии <br />
								если библиотекарь - редактировать и инфу по книге
							</td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</div>
	</div>
</body>
</html>