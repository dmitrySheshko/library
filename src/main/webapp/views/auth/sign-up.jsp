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
			<h2 class="text-center">Sign up</h2>
			<form action="/auth/sign-up" method="post" class="form-horizontal">
				<div class="<ex:CssClassHandler defaultClass="form-group" fieldName="firstName" errorList="${errors}" />">
					<label for="firstName" class="col-sm-4 control-label">First name</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="firstName" placeholder="First name" name="firstName" value="${user.firstName}">
					</div>
				</div>
				<div
					class="<ex:CssClassHandler defaultClass="form-group" fieldName="lastName" errorList="${errors}" />">
					<label for="lastName" class="col-sm-4 control-label">Last name</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="lastName" placeholder="Last name" name="lastName" value="${user.lastName}">
					</div>
				</div>
				<div
					class="<ex:CssClassHandler defaultClass="form-group" fieldName="login" errorList="${errors}" />">
					<label for="login" class="col-sm-4 control-label">Login</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="login" placeholder="Login" name="login" value="${user.login}">
					</div>
				</div>
				<div
					class="<ex:CssClassHandler defaultClass="form-group" fieldName="password" errorList="${errors}" />">
					<label for="password" class="col-sm-4 control-label">Password</label>
					<div class="col-sm-8">
						<input type="password" class="form-control" id="password" placeholder="Password" name="password" value="${user.password}">
					</div>
				</div>
				<div class="form-group">
					<label for="confirmPassword" class="col-sm-4 control-label">Confirm
						password</label>
					<div class="col-sm-8">
						<input type="password" class="form-control" id="confirmPassword" placeholder="Confirm password" name="confirmPassword" value="${user.confirmPassword}">
					</div>
				</div>
				<c:if test="${sessionUser != null && sessionUser.role == 1}">
					<div class="<ex:CssClassHandler defaultClass="form-group" fieldName="role" errorList="${errors}" />">
						<label for="confirmPassword" class="col-sm-4 control-label">User role</label>
						<div class="col-lg-8">
							<div class="radio">
								<label> 
									<input type="radio" name="role" value="2" <c:if test = "${user.role == 2}">checked="checked"</c:if>>
									Librarian
								</label>
							</div>
							<div class="radio">
								<label> 
									<input type="radio" name="role" value="3" <c:if test = "${user.role == 3}">checked="checked"</c:if>>
									Reader
								</label>
							</div>
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<div class="col-lg-10 col-lg-offset-2">
						<button type="submit" class="btn btn-primary">Sign up</button>
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