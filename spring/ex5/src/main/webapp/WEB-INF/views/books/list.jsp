<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous"/>
	<title>Recherche de livres</title>
</head>

<body>

	<div style="display: flex; flex: column">
		<div style="flex: 1; padding: 15px">
			<!--  formulaire de recherche -->
			<form:form modelAttribute="filter" method="get">
				Titre : 
				<form:input path="title" cssClass="form-control"/> 
				<form:errors path="title"/>
				
				<br/>
				
				Author : <form:input path="authorName" cssClass="form-control"/>
				<input type="submit" class="btn btn-primary"/>
			</form:form>
		</div>
		<div style="flex: 2; padding: 15px">
			<!-- liste de résultats -->
			<ul class="list-group">
				<c:forEach var="b" items="${results.items}">
					<li class="list-group-item">
						<a href="/books/${b.id}">${b.title}</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>
