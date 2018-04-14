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
			<title>Reis d'Igualada :: Editar impresora</title>
		</c:if>
		<c:if test="${editMode == false}">
			<title>Reis d'Igualada :: Crear impresora</title>
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
					<c:if test="${editMode == true}">
						<form id="printTicketForm" method="GET" action="${contextPath}/configuration/printTest">
				            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				            <input type="hidden" name="printerid" value="${printerForm.id}"/>
				            <input type="hidden" name="printername" value="${printerForm.printername}"/>
				        </form>
			        </c:if>
					<form:form method="POST" modelAttribute="printerForm" class="form-signin">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<spring:bind path="id">
							<form:input type="hidden" path="id" class="form-control"></form:input>
						</spring:bind>
						
						<c:if test="${editMode == true}">
							<h2 class="form-signin-heading">Editar impresora</h2>
						</c:if>
						<c:if test="${editMode == false}">
							<h2 class="form-signin-heading">Crear impresora</h2>
						</c:if>
						<spring:bind path="printername">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="printername" class="form-control" placeholder="Perfil" autofocus="true"></form:input>
								<form:errors path="printername"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<c:if test="${editMode == true}">
							<h4>Impressió de prova</h4>
		 					<a onclick="document.forms['printTicketForm'].submit()" class="btn btn-success">Imprimir</a>
							<br><br>
						</c:if>
						
						<div align="center">
							<button class="btn btn-lg btn-default" type="button" onclick="javascript:window.location='config_printers';">Tornar</button>
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
