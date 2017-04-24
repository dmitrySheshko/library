<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/views/header/header.jsp"%>
<body>
	<div>
		<%@ include file="/views/menu/main-menu.jsp"%>
		<div class="page add-book">
			<h1>Add new book</h1>
		    <form action="/add-book" method="post" class="form-horizontal">
		        <table class="table table-striped table-hover">
		            <tr>
		                <td>Title</td>
		                <td>
		                    <div class="form-group">
		                        <input class="form-control" type="text" name="title" placeholder="Book title" value="${book.title}">
		                    </div>
		                </td>
		            </tr>
		            <tr>
		                <td>Author</td>
		                <td>
		                    <div class="form-group">
		                        <input class="form-control" type="text" name="author" placeholder="Book author" />
		                    </div>
		                </td>
		            </tr>
		            <tr>
		                <td>Category</td>
		                <td>
		                    <div class="form-group">
		                        <select class="form-control" name="category">
		                            <option value="">Select category</option>
		                            <c:forEach items="${categories}" var="category">
		                            	<option value="${category.id}">${category.name}</option>
		                            </c:forEach>
		                        </select>
		                    </div>
		                </td>
		            </tr>
		            <tr>
		                <td>Books count</td>
		                <td>
		                    <div class="form-group">
		                        <input class="form-control" type="text" name="count" placeholder="Books count" />
		                    </div>
		                </td>
		            </tr>
		            <tr>
		                <td></td>
		                <td>
		                    <div class="form-group">
		                        <input type="submit" value="Save" class="btn btn-primary" />
		                    </div>
		                </td>
		            </tr>
		        </table>
		    </form>
		</div>
	</div>
	<script>
	
	</script>
</body>
</html>