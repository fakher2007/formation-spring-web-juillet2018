<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous"/>
	<title>${entity.title}</title>
</head>

<body>
	Title : ${entity.title }<br/>
	Author : ${entity.author.fullname }<br/>
	
	<hr />

	<!-- formulaire de réservation du livre -->
	<form:form modelAttribute="reservation-command" action="/reservations?bookId=${entity.id}">
		
		
		Username : <form:input path="username" cssClass="form-control"/>
		<form:errors path="username"/><br/>
		
		Pickup date : <form:input path="pickupDate" cssClass="form-control"/>
		<form:errors path="pickupDate"/><br/>

		Return date : <form:input path="returnDate" cssClass="form-control"/>
		<form:errors path="returnDate"/><br/>
		
		<input type="submit" class="btn btn-primary" value="Borrow"/>
	</form:form>
	<!-- 
	L'utilisateur doit saisir les informations suivante : 
	* nom d'utilisateur
	* date de récupération du livre
	* date de retour du livre
	 -->
</body>
</html>
