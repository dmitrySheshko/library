<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ex" uri="/WEB-INF/css-class-handler.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/views/header/header.jsp"%>
<body>
	<div>
		<%@ include file="/views/menu/main-menu.jsp"%>
		<div id="registration">
			<h2 class="text-center">Sign in</h2>
			<form action="/auth/sign-in" method="post" class="form-horizontal">
				<div
					class="<ex:CssClassHandler defaultClass="form-group" fieldName="login" errorList="${errors}" />">
					<label for="login" class="col-sm-4 control-label">Login</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="login"
							placeholder="Login" name="login" value="${login}">
					</div>
				</div>
				<div
					class="<ex:CssClassHandler defaultClass="form-group" fieldName="password" errorList="${errors}" />">
					<label for="password" class="col-sm-4 control-label">Password</label>
					<div class="col-sm-8">
						<input type="password" class="form-control" id="password"
							placeholder="Password" name="password">
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-10 col-lg-offset-2">
						<button type="submit" class="btn btn-primary">Sign in</button>
					</div>
				</div>
			</form>
			<c:if test="${errors != null && errors.size() != 0}">
				<div class="alert alert-danger" role="alert">
					<c:forEach items="${errors}" var="error">
						<div>${ error.message }</div>
					</c:forEach>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>