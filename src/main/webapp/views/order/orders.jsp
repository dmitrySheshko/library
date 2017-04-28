<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/views/header/header.jsp"%>
<body>
	<div>
		<%@ include file="/views/menu/main-menu.jsp"%>
		<div class="page">
			<h1>Orders</h1>
			<%@ include file="/views/order/filter.jsp"%>
			<table class="table table-striped table-hover">
				<tr>
					<th>Reader</th>
					<th>Book title</th>
					<th>Book author</th>
					<th>Order date</th>
					<th>Status</th>
					<th>Details</th>
				</tr>
				<c:forEach items="${orders}" var="order">
					<tr>
						<td>${order.reader.firstName} ${order.reader.lastName}</td>
						<td>${order.book.title}</td>
						<td>${order.book.author}</td>
						<td>${order.createDate}</td>
						<td>
							<c:if test="${order.status == 0}">
								<span class="label label-default">Close</span>
							</c:if>
							<c:if test="${order.status == 1}">
								<span class="label label-success">Open</span>
							</c:if>
						</td>
						<td>
							<a href="/order/${order.id}" class="btn btn-info">see more</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>