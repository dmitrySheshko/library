<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/views/header/header.jsp"%>
<body>
	<div>
		<%@ include file="/views/menu/main-menu.jsp"%>
		<div class="page">
			<h1>Office</h1>
			<%@ include file="/views/office/office-menu.jsp"%>
			<div class="profile">
				<h2>Current orders</h2>
				<table class="table table-striped table-hover">
					<tr>
						<td>Book title</td>
						<td>Book author</td>
						<td>Date</td>
						<td>Status</td>
						<td></td>
					</tr>
					<c:forEach items="${orders}" var="order">
						<tr>
							<td>${order.book.title}</td>
							<td>${order.book.author}</td>
							<td>${order.createDate}</td>
							<td>
								<c:if test="${order.orderType == 1}">Wait</c:if>
								<c:if test="${order.orderType == 2}">Issue to a reading room</c:if>
								<c:if test="${order.orderType == 3}">Issue to home</c:if>
							</td>
							<td>
								<c:if test="${order.orderType == 1}">
									<a href="/order-handler?id=${order.id}&type=deleteByUser" class="btn btn-warning">Cancel order</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				
			</div>
		</div>
	</div>
</body>
</html>