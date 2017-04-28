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
			<%@ include file="/views/books/filter.jsp"%>

			<c:if test="${sessionUser != null && sessionUser.role != 3}">
				<a href="/add-book">Add book</a>
			</c:if>

			<form action="/order" method="post">
				<table class="table table-striped table-hover">
					<tr>
						<th></th>
						<th>Title</th>
						<th>Author</th>
						<th>Category</th>
						<th>Available</th>
					</tr>
					<c:forEach items="${books}" var="book">
						<tr>
							<td><input type="checkbox" name="bookIds" value="${book.id}" />
							</td>
							<td>
								<c:if test="${sessionUser == null || (sessionUser != null && sessionUser.role == 3)}">
									${book.title}
								</c:if>
								<c:if test="${sessionUser != null && sessionUser.role != 3}">
									<a href="/book/${book.id}">${book.title}</a>
								</c:if>
								
							</td>
							<td>${book.author}</td>
							<td>${book.category.name}</td>
							<td>
								<c:if test="${book.count == 0}">
									not available!
								</c:if>
								<c:if test="${book.count != 0}">
									<c:if test="${sessionUser != null && sessionUser.role == 3}">
										${book.count}
									</c:if>
									<c:if test="${sessionUser != null && sessionUser.role != 3}">
										${book.count}
									</c:if>
									<c:if test="${sessionUser == null}">
										available
									</c:if>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${sessionUser != null && sessionUser.role == 3}">
					<div>
						<button type="submit" class="btn btn-success">Order</button>
					</div>
				</c:if>
			</form>
		</div>
	</div>
</body>
</html>