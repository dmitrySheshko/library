<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/views/header/header.jsp"%>
<body>
	<div>
		<%@ include file="/views/menu/main-menu.jsp"%>
		<div class="page">
			<h1>Order</h1>
			<table class="table table-striped table-hover">
				<tr>
					<td>Reader</td>
					<td>
						${order.reader.firstName} ${order.reader.lastName}
					</td>
				</tr>
				<tr>
					<td>Book</td>
					<td>
						<div>Author: ${order.book.author}</div>
						<div>Title: ${order.book.title}</div>
					</td>
				</tr>
				<tr>
					<td>Librarian (who has given the book) ???</td>
					<td>
						${order.librarian.firstName} ${order.librarian.lastName}
					</td>
				</tr>
				<tr>
					<td>Order type</td>
					<td>
						<c:if test="${order.orderType == 2}">Home</c:if>
						<c:if test="${order.orderType == 3}">Reading room</c:if>
					</td>
				</tr>
				<tr>
					<td>Order date</td>
					<td>${order.createDate}</td>
				</tr>
				<tr>
					<td>Issue date</td>
					<td>${order.outDate}</td>
				</tr>
				<tr>
					<td>Return date</td>
					<td>${order.returnDate}</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<c:if test="${order.status == 1 && order.orderType == 1}">
							<a href="/order-handler?id=${order.id}&type=issue&orderType=2" class="btn btn-success">Issue the book to home</a>
							<a href="/order-handler?id=${order.id}&type=issue&orderType=3" class="btn btn-success">Issue the book to the reading room</a>
							<a href="/order-handler?id=${order.id}&type=close" class="btn btn-warning">Close order</a>
						</c:if>
						<c:if test="${order.status == 1 && order.orderType != 1}">
							<a href="/order-handler?id=${order.id}&type=return" class="btn btn-primary">Return book</a>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>