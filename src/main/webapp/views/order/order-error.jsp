<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/views/header/header.jsp"%>
<body>
	<div>
		<%@ include file="/views/menu/main-menu.jsp"%>
		<div class="page">
			<h1>Order error</h1>
			<c:if test="${orderError != null}">
				<div class="alert alert-danger" role="alert">
					<div>${ orderError.message }</div>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>