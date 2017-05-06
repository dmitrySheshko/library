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
			<c:if test="${error != null}">
				<div class="alert alert-danger" role="alert">
					<div>${error.message}</div>
				</div>
			</c:if>
			<%@ include file="/views/office/office-menu.jsp"%>
			<div class="profile">
				<h2>Profile</h2>
				<form action="/edit-profile" method="post">
					<table class="table table-striped table-hover">
						<tr>
							<td>First name</td>
							<td>${user.firstName}</td>
						</tr>
						<tr>
							<td>Last name</td>
							<td>${user.lastName}</td>
						</tr>
						<tr>
							<td>Login</td>
							<td>${user.login}</td>
						</tr>
						<tr>
							<td>Old password</td>
							<td>
								<div class="form-group">
									<input type="password" name="oldPassword" class="form-control" placeholder="Old password" />
								</div>
							</td>
						</tr>
						<tr>
							<td>New password</td>
							<td>
							<div class="form-group">
									<input type="password" name="newPassword" class="form-control" placeholder="New password" />
								</div>
							</td>
						</tr>
						<tr>
							<td>Confirm new password</td>
							<td>
							<div class="form-group">
									<input type="password" name="confirmPassword" class="form-control" placeholder="Confirm password" />
								</div>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<button type="submit" class="btn btn-success">Save</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>