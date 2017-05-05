<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="book-filter">
	<form action="/books" method="get" enctype="application/x-www-form-urlencoded">
		<div class="form-group">
			<input type="text" name="title" placeholder="Book title" class="form-control" value="${filter.title}" />
		</div>
		<div class="form-group">
			<input type="text" name="author" placeholder="Author" class="form-control" value="${filter.author}" />
		</div>
		<div class="form-group">
			<select class="form-control" name="category">
				<option value="0">Select category</option>
				<c:forEach items="${categories}" var="category">
					<option value="${category.id}" <c:if test="${filter.category == category.id}">selected="selected"</c:if>>${category.name}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<input type="submit" value="Search" class="btn btn-success" />
		</div>
	</form>
</div>