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
			<title>Reis d'Igualada :: Editar document</title>
		</c:if>
		<c:if test="${editMode == false}">
			<title>Reis d'Igualada :: Crear document</title>
		</c:if>
		
		<link href="${contextPath}/resources/css/common2.css" rel="stylesheet">
		<link href="//code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" rel="stylesheet">
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
	    	<sec:authorize access="!hasRole('ROLE_ADMIN') && !hasRole('ROLE_ARXIU')">
				<div class="container">
					<%@ include file="../headers/no_grants.jsp" %>
				</div>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN') || hasRole('ROLE_ARXIU')">
		    	<div class="container">
		    		<form:form method="POST" modelAttribute="fitxerForm" class="form-signin" action="${pageContext.request.contextPath}/arxiu/create?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
						<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<spring:bind path="id">
							<form:input type="hidden" path="id" class="form-control"></form:input>
						</spring:bind>
						<spring:bind path="fileName">
							<form:input type="hidden" path="fileName" class="form-control"></form:input>
						</spring:bind>
						<spring:bind path="format">
							<form:input type="hidden" path="format" class="form-control"></form:input>
						</spring:bind>
						
						<c:if test="${editMode == true}">
							<h2 class="form-signin-heading">
								Editar document
								
				        		<a href="${contextPath}/arxiu/registre" class="btn btn-success" style="float: right;">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
									Nou registre
								</a>
							</h2>
						</c:if>
						<c:if test="${editMode == false}">
							<h2 class="form-signin-heading">Crear document</h2>
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
				        
				        <spring:bind path="titol">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="titol" class="form-control" placeholder="Títol document" autofocus="true" required="true"></form:input>
								<form:errors path="titol"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="data">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="date" path="data" class="form-control" placeholder="Data" autofocus="true" required="true"></form:input>
								<form:errors path="data"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<h4 class="form-signin-heading">Tipologia</h4>
						<spring:bind path="typeDocument">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:select path="typeDocument" class="form-control" required="true">
									<form:option value="">-- Seleccionar  --</form:option>
									<form:option value="1">Imatge</form:option>
									<form:option value="2">Document</form:option>
								</form:select>
								<form:errors path="typeDocument"></form:errors>
							</div>
						</spring:bind>
						
						<spring:bind path="paraulesClau">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="paraulesClau" class="form-control" placeholder="Paraules clau" autofocus="true"></form:input>
								<form:errors path="paraulesClau"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="autor">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="autor" class="form-control" placeholder="Autor" autofocus="true"></form:input>
								<form:errors path="autor"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<!-- PARAMETRES PROPIS DE IMATGE -->
						<spring:bind path="ubicacio">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<c:if test="${fitxerForm.typeDocument == '1'}">
									<form:input type="text" path="ubicacio" class="form-control" placeholder="Ubicació" autofocus="true"></form:input>
								</c:if>
								<c:if test="${fitxerForm.typeDocument == '2'}">
									<form:input type="text" path="ubicacio" class="form-control" placeholder="Ubicació" autofocus="true" style="display: none;"></form:input>
								</c:if>
								<form:errors path="ubicacio"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
							
						<spring:bind path="procedencia">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<c:if test="${fitxerForm.typeDocument == '1'}">
									<form:input type="text" path="procedencia" class="form-control" placeholder="Procedencia" autofocus="true"></form:input>
								</c:if>
								<c:if test="${fitxerForm.typeDocument == '2'}">
									<form:input type="text" path="procedencia" class="form-control" placeholder="Procedencia" autofocus="true" style="display: none;"></form:input>
								</c:if>
								<form:errors path="procedencia"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<!-- PARAMETRES PROPIS DE DOCUMENT -->
						<spring:bind path="ubicacioArxiu">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<c:if test="${fitxerForm.typeDocument == '1'}">
									<form:input type="text" path="ubicacioArxiu" class="form-control" placeholder="Ubicació al arxiu" autofocus="true" style="display: none;"></form:input>
								</c:if>
								<c:if test="${fitxerForm.typeDocument == '2'}">
									<form:input type="text" path="ubicacioArxiu" class="form-control" placeholder="Ubicació al arxiu" autofocus="true"></form:input>
								</c:if>
								<form:errors path="ubicacioArxiu"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<spring:bind path="Observacions">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:textarea path="observacions" class="form-control" placeholder="Observacions" autofocus="true"></form:textarea>
								<form:errors path="observacions"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<c:if test="${editMode == false}">
							<div class="form-group">
								<label for="file">Fitxer</label>
							    <input type="file" id="file" name="file">
							</div>
						</c:if>
						<c:if test="${editMode == true}">
							<div class="form-group" style="display: none;">
								<label for="file" style="display: none;">Fitxer</label>
							    <input type="file" id="file" name="file" style="display: none;">
							</div>
							<div class="tz-gallery-edit">
								<div style="width: 265px; float: center;">
				                	<div class="thumbnail">
				                		<c:if test="${fitxerForm.typeDocument == 1}">
											<div style="background-image:url('/project/images/gd_reis1/${fitxerForm.fileName}'); position: relative; float: center width: 235px; height: 179px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
											<div class="caption">
												<h3>Imatge</h3>
												<a download="${fitxerForm.fileName}" href="/project/images/gd_reis1/${fitxerForm.fileName}" target="_blank" class="btn btn-sm btn-success">
													<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
													Descarregar
												</a>
											</div>
										</c:if>
										<c:if test="${fitxerForm.typeDocument == 2}">
											<c:choose>
											    <c:when test="${fitxerForm.format == 'doc' || fitxerForm.format == 'docx' || fitxerForm.format == 'xls' || fitxerForm.format == 'xlsx' || fitxerForm.format == 'pdf'}">
											        <div style="background-image:url('${contextPath}/resources/images/${fitxerForm.format}.png'); position: relative; float: center width: 235px; height: 179px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
											    </c:when>
											    <c:otherwise>
											        <div style="background-image:url('${contextPath}/resources/images/file.png'); position: relative; float: center width: 235px; height: 179px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
											    </c:otherwise>
											</c:choose>
											<div class="caption">
												<h3>Document</h3>
												<a href="/project/images/gd_reis1/${fitxerForm.fileName}.${fitxerForm.format}" target="_blank" class="btn btn-sm btn-success">
													<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
													Descarregar
												</a>
											</div>
										</c:if>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="file">Data creació: </label> ${fitxerForm.dataCreacio}
							</div>
						</c:if>
						
						<div align="center">
							<button class="btn btn-lg btn-default" type="button" onclick="javascript:window.location='consulta';">Tornar</button>
							<c:if test="${editMode == true}">
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
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script src="//code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
		<script src="${contextPath}/resources/js/moment.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap-tokenfield.min.js"></script>
	    <script type="text/javascript">
    		var elements = [${paraulesClauList}];
	    </script>
	    <script src="${contextPath}/resources/js/arxiu/registre.js"></script>
	</body>
</html>
