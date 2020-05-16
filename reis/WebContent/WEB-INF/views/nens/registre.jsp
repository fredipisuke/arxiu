<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ca">
	<head>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <meta name="description" content="">
	    <meta name="author" content="Reis d'Igualada">
	
	    <c:if test="${editMode == true}">
			<title>Reis d'Igualada :: Editar patge infantil</title>
		</c:if>
		<c:if test="${editMode == false}">
			<title>Reis d'Igualada :: Crear patge infantil</title>
		</c:if>
		<link rel="icon" href="${contextPath}/resources/images/favicon.ico" type="image/x-icon">
		<link href="${contextPath}/resources/css/common2.css" rel="stylesheet">
		<link href="${contextPath}/resources/css/jquery-ui.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/tokenfield-typeahead.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/bootstrap-tokenfield.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/thumbnail-gallery.css" rel="stylesheet">
	    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
		    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	<body>
		<%@ include file="../headers/login_header.jsp" %>
	    <c:if test="${pageContext.request.userPrincipal.name != null}">
	    	<sec:authorize access="!hasRole('ROLE_ADMIN') && !hasRole('ROLE_NENS')">
				<div class="container">
					<%@ include file="../headers/no_grants.jsp" %>
				</div>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN') || hasRole('ROLE_NENS')">
		    	<div class="container">
		    		<form:form method="POST" modelAttribute="nenForm" class="form-signin" action="${pageContext.request.contextPath}/nens/create?${_csrf.parameterName}=${_csrf.token}">
						<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<spring:bind path="id">
							<form:input type="hidden" path="id" class="form-control"></form:input>
						</spring:bind>
						
						<c:if test="${editMode == true}">
							<h2 class="form-signin-heading">
								Editar patge infantil
				        		<a href="${contextPath}/nens/registre" class="btn btn-success" style="float: right;">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
									Nou registre
								</a>
							</h2>
						</c:if>
						<c:if test="${editMode == false}">
							<h2 class="form-signin-heading">Crear patge infantil</h2>
						</c:if>
						
						<c:if test="${messageError!=null}">
							<div class="alert alert-danger" role="alert">
								<span class="glyphicon glyphicon-remove-sign" aria-hidden="true" style="font-size: 1.5em;"></span>
								<strong>${messageError}</strong>
							</div>
						</c:if>
						<c:if test="${messageOk!=null}">
							<div class="alert alert-success" role="alert" >
								<span class="glyphicon glyphicon-ok-sign" aria-hidden="true" style="font-size: 1.5em;"></span>
								<strong>${messageOk}</strong>
							</div>
						</c:if>
				        
				        <h4 class="form-signin-heading">Tipus document</h4>
						<spring:bind path="tipusDocument">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:select path="tipusDocument" class="form-control" required="true">
									<form:option value="">-- Seleccionar  --</form:option>
									<form:option value="1">DNI</form:option>
									<form:option value="2">Passaport</form:option>
									<form:option value="3">Llibre de familia</form:option>
								</form:select>
								<form:errors path="tipusDocument"></form:errors>
							</div>
						</spring:bind>
				        
				        <spring:bind path="document">
							<div class="form-group ${status.error ? 'has-error' : ''}" id="divDocument">
								<form:input type="text" path="document" class="form-control" placeholder="Document" autofocus="true" required="true"></form:input>
								<form:errors path="document"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="nom">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="nom" class="form-control" placeholder="Nom" autofocus="true" required="true"></form:input>
								<form:errors path="nom"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="dataNaixement">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="date" path="dataNaixement" class="form-control" placeholder="Data de naixement" autofocus="true" required="true"></form:input>
								<form:errors path="dataNaixement"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<h4 class="form-signin-heading">Sexe</h4>
						<spring:bind path="sexe">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:select path="sexe" class="form-control" required="true">
									<form:option value="">-- Seleccionar  --</form:option>
									<form:option value="H">Nen</form:option>
									<form:option value="D">Nena</form:option>
								</form:select>
								<form:errors path="sexe"></form:errors>
							</div>
						</spring:bind>
						
						<h4 class="form-signin-heading">Escola</h4>
						<spring:bind path="escola.id">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:select path="escola.id" class="form-control">
									<form:option value="">-- Seleccionar  --</form:option>
									<c:forEach var="esc" items="${escolaList}">
										<option value="${esc.id}" ${esc.id==nenForm.escola.id ? 'selected' : ''} >${esc.descripcio}</option>
									</c:forEach>
								</form:select>
								<form:errors path="escola.id"></form:errors>
							</div>
						</spring:bind>
						
						<h3 class="form-signin-heading">Tutor legal</h3>
						
				        <spring:bind path="tutor.document">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="tutor.document" class="form-control" placeholder="DNI" autofocus="true" required="true"></form:input>
								<form:errors path="tutor.document"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="tutor.nom">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="tutor.nom" class="form-control" placeholder="Nom" autofocus="true" required="true"></form:input>
								<form:errors path="tutor.nom"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="tutor.direccio">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="tutor.direccio" class="form-control" placeholder="Direccio" autofocus="true" required="true"></form:input>
								<form:errors path="tutor.direccio"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="tutor.codiPostal">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="Number" path="tutor.codiPostal" class="form-control" placeholder="Codi Postal" autofocus="true" required="true"></form:input>
								<form:errors path="tutor.codiPostal"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="tutor.poblacio">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="tutor.poblacio" class="form-control" placeholder="Població" autofocus="true" required="true"></form:input>
								<form:errors path="tutor.poblacio"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="tutor.telefon">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="tutor.telefon" class="form-control" placeholder="Telèfon contacte" autofocus="true" required="true"></form:input>
								<form:errors path="tutor.telefon"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="tutor.email">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="tutor.email" class="form-control" placeholder="E-mail contacte" autofocus="true" required="true"></form:input>
								<form:errors path="tutor.email"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<div align="center">
							<button class="btn btn-lg btn-default" type="button" onclick="javascript:window.location='consultaBack';">Tornar</button>
							<c:if test="${editMode == true}">
								<a href="#" class="btn btn-lg btn-danger" title="Eliminar" onclick="eliminarNen(${nenForm.id})">Eliminar</a>
								<button class="btn btn-lg btn-primary" type="submit">Modificar</button>
							</c:if>
							<c:if test="${editMode == false}">
								<button class="btn btn-lg btn-primary" type="submit">Crear</button>
							</c:if>
						</div>
						<br>
					</form:form>
				</div>
			</sec:authorize>
	    </c:if>
		
		<!-- /container -->
		
		<script src="${contextPath}/resources/js/jquery/1.9.1/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/jquery-ui.js"></script>
		<script src="${contextPath}/resources/js/moment.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap-tokenfield.min.js"></script>
	    <script src="${contextPath}/resources/js/nens/registre.js"></script>
	</body>
</html>
