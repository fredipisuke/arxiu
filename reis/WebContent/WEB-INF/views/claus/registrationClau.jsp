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
			<title>Arxiu Reis d'Igualada :: Editar clau</title>
		</c:if>
		<c:if test="${editMode == false}">
			<title>Arxiu Reis d'Igualada :: Crear clau</title>
		</c:if>
		<link rel="icon" href="${contextPath}/resources/images/favicon.ico" type="image/x-icon">
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
			<sec:authorize access="!hasRole('ROLE_ADMIN') && !hasRole('ROLE_ARXIU')">
				<div class="container">
					<%@ include file="../headers/no_grants.jsp" %>
				</div>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN') || hasRole('ROLE_ARXIU')">
				<div class="container">
					<form:form method="POST" modelAttribute="clauForm" class="form-signin">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<spring:bind path="id">
							<form:input type="hidden" path="id" class="form-control"></form:input>
						</spring:bind>
						
						<c:if test="${editMode == true}">
							<h2 class="form-signin-heading">Editar clau</h2>
						</c:if>
						<c:if test="${editMode == false}">
							<h2 class="form-signin-heading">Crear clau</h2>
						</c:if>
						<spring:bind path="name">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="name" class="form-control" placeholder="Clau" autofocus="true"></form:input>
								<form:errors path="name"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<h4 class="form-signin-heading">Tipologia</h4>
						<spring:bind path="type">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:select path="type" class="form-control">
									<form:option value="">-- Seleccionar  --</form:option>
									<form:option value="1">Imatge</form:option>
									<form:option value="2">Document</form:option>
								</form:select>
								<form:errors path="type"></form:errors>
							</div>
						</spring:bind>
						
						<div align="center">
							<button class="btn btn-lg btn-default" type="button" onclick="javascript:window.location='consulta';">Tornar</button>
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
		<script src="${contextPath}/resources/js/jquery/1.11.2/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
	</body>
</html>
