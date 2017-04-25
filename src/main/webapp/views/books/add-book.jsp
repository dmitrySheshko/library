<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ex" uri="/WEB-INF/css-class-handler.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/views/header/header.jsp"%>
<body>
	<div>
		<%@ include file="/views/menu/main-menu.jsp"%>
		<div class="page add-book">
			<h1>Add new book</h1>
		    <c:if test="${message != null}">
				<div class="alert alert-success" role="alert">
					<div>${message}</div>
				</div>
			</c:if>
		    <form action="/add-book" method="post" class="form-horizontal">
		        <table class="table table-striped table-hover">
		            <tr>
		                <td>Title</td>
		                <td>
		                    <div class="<ex:CssClassHandler defaultClass="form-group" fieldName="title" errorList="${errors}" />">
		                        <input class="form-control" type="text" name="title" placeholder="Book title" value="${book.title}">
		                    </div>
		                </td>
		            </tr>
		            <tr>
		                <td>Author</td>
		                <td>
		                    <div class="<ex:CssClassHandler defaultClass="form-group" fieldName="author" errorList="${errors}" />">
		                        <input class="form-control" type="text" name="author" placeholder="Book author" value="${book.author}" />
		                    </div>
		                </td>
		            </tr>
		            <tr>
		                <td>Category</td>
		                <td>
		                    <div class="<ex:CssClassHandler defaultClass="form-group" fieldName="category" errorList="${errors}" />">
		                        <select class="form-control" name="category">
		                            <option value="0">Select category</option>
		                            <c:forEach items="${categories}" var="category">
		                            	<option value="${category.id}" <c:if test="${book.category.id == category.id}">selected="selected"</c:if>>${category.name}</option>
		                            </c:forEach>
		                        </select>
		                    </div>
		                </td>
		            </tr>
		            <tr>
		            	<td colspan="3">Books count</td>
		            </tr>
		            <tr class="exemplar">
		                <td>Exemplar</td>
		                <td>
		                    <div class="<ex:CssClassHandler defaultClass="form-group" fieldName="exemplars" errorList="${errors}" />">
		                        <div id="exemplars">
		                        	<c:if test="${book.exemplars != null}">
			                        	<c:forEach items="${book.exemplars}" var="exemplar">
				                        	<div>
				                        		<input class="form-control" type="text" name="exemplarNumber" placeholder="Exemplar number" value="${exemplar}" />
				                        		<a href="javascript:void(0)" class="remove-exemplar">Remove exemplar</a>
				                        	</div>
			                        	</c:forEach>
									</c:if>
		                        	<c:if test="${book.exemplars == null}">
			                        	<div>
			                        		<input class="form-control" type="text" name="exemplarNumber" placeholder="Exemplar number" />
			                        		<a href="javascript:void(0)" class="remove-exemplar">Remove exemplar</a>
			                        	</div>
		                        	</c:if>
		                        </div>		                        
		                        <a href="javascript:void(0)" id="add-exemplar">Add new exemplar</a>
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
		    <c:if test="${errors != null && errors.size() != 0}">
				<div class="alert alert-danger" role="alert">
					<c:forEach items="${errors}" var="error">
						<div>${ error.message }</div>
					</c:forEach>
				</div>
			</c:if>
		</div>
	</div>
	<script>
		var newExemplar = document.createElement('DIV');
		newExemplar.innerHTML = '<input class="form-control" type="text" name="exemplarNumber" placeholder="Exemplar number" /><a href="javascript:void(0)" class="remove-exemplar">Remove exemplar</a>';
		var exemplars = document.getElementById("exemplars");
		document.getElementById("add-exemplar").onclick = function(){
			exemplars.appendChild(newExemplar.cloneNode(true));
		}
		
		document.getElementById("exemplars").addEventListener('click', function(event){
			if(event.target.classList.contains('remove-exemplar')){
				event.target.parentNode.remove();
			}
		});
	</script>
</body>
</html>