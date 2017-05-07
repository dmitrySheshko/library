<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="book-filter">
	<form action="/orders" method="get" enctype="application/x-www-form-urlencoded">
		<div class="form-group">
			<input type="text" name="readerFirstName" placeholder="Reader first name" class="form-control" value="${filter.readerFirstName}" />
		</div>
		<div class="form-group">
			<input type="text" name="readerLastName" placeholder="Reader last name" class="form-control" value="${filter.readerLastName}" />
		</div>
		<div class="form-group">
			<input type="text" name="title" placeholder="Book title" class="form-control" value="${filter.title}" />
		</div>
		<div class="form-group">
			<input type="text" name="author" placeholder="Author" class="form-control" value="${filter.author}" />
		</div>
		<div class="form-group">
			<select class="form-control" name="status">
				<option value="0">Select status</option>
				<option value="1" <c:if test="${filter.status == 1}">selected="selected"</c:if>>Open</option>
				<option value="2" <c:if test="${filter.status == 2}">selected="selected"</c:if>>Close</option>
			</select>
		</div>
		<div class="form-group">
			<input type="submit" value="Search" class="btn btn-success" />
		</div>
	</form>
</div>