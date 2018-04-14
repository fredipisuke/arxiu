<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ca">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="Reis d'Igualada">
		
		<c:if test="${editMode == true}">
			<title>Reis d'Igualada :: Editar usuari</title>
		</c:if>
		<c:if test="${editMode == false}">
			<title>Reis d'Igualada :: Crear usuari</title>
		</c:if>
		
	    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
		<link href="${contextPath}/resources/css/common2.css" rel="stylesheet">
	    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
		    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	
	<body>
		<%@ include file="../headers/login_header.jsp" %>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<sec:authorize access="!hasRole('ROLE_ADMIN')">
				<div class="container">
					<%@ include file="../headers/no_grants.jsp" %>
				</div>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<div class="container">
					<form:form method="POST" modelAttribute="userForm" class="form-signin">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<spring:bind path="id">
							<form:input type="hidden" path="id" class="form-control"></form:input>
						</spring:bind>
						
						<c:if test="${editMode == true}">
							<h2 class="form-signin-heading">Editar usuari</h2>
						</c:if>
						<c:if test="${editMode == false}">
							<h2 class="form-signin-heading">Crear usuari</h2>
						</c:if>
						<spring:bind path="username">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="username" class="form-control" placeholder="Usuari" autofocus="true"></form:input>
								<form:errors path="username"></form:errors>
							</div>
						</spring:bind>
						<!-- NOMÉS PERMETEM L'INTRODUCCIÓ DEL PASSWORD A L'ALTA -->
						<c:if test="${editMode == false}">
							<spring:bind path="password">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input type="password" path="password" class="form-control" placeholder="Contrasenya"></form:input>
									<form:errors path="password"></form:errors>
								</div>
							</spring:bind>
							<spring:bind path="passwordConfirm">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input type="password" path="passwordConfirm" class="form-control" placeholder="Confirmar contrasenya"></form:input>
									<form:errors path="passwordConfirm"></form:errors>
								</div>
							</spring:bind>
						</c:if>
						<c:if test="${editMode == true}">
							<spring:bind path="cryptPassword">
								<form:input type="hidden" path="cryptPassword" class="form-control"></form:input>
							</spring:bind>
						</c:if>
						
						<h4 class="form-signin-heading">Perfils</h4>
						<spring:bind path="selectedRoles">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<c:forEach var="r" items="${roles}">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" id="selectedRoles${r.id}" name="selectedRoles" value="${r.id}" ${r.checked} }>${r.name}<br>
									<br>
								</c:forEach>
								<form:errors path="selectedRoles"></form:errors>
							</div>
						</spring:bind>
						
						<h4 class="form-signin-heading">Impresora</h4>
						<spring:bind path="printer">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:select path="printer.id" class="form-control">
									<form:option value="">-- Impresora --</form:option>
							 		<form:options items="${printers}" itemLabel="printername" itemValue="id" />
								</form:select>
								<form:errors path="printer"></form:errors>
							</div>
						</spring:bind>
						
						<div align="center">
							<button class="btn btn-lg btn-default" type="button" onclick="javascript:window.location='config_users';">Tornar</button>
							<c:if test="${editMode == true}">
								<button class="btn btn-lg btn-primary" type="submit">Modificar</button>
							</c:if>
							<c:if test="${editMode == false}">
								<button class="btn btn-lg btn-primary" type="submit">Crear</button>
							</c:if>
						</div>
						
					</form:form>
				</div>
			</sec:authorize>
		</c:if>
		<!-- /container -->
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
	</body>
</html>
