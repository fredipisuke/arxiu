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
			<title>Arxiu Reis d'Igualada :: Editar document</title>
		</c:if>
		<c:if test="${editMode == false}">
			<title>Arxiu Reis d'Igualada :: Crear document</title>
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
	    	<sec:authorize access="!hasRole('ROLE_ADMIN') && !hasRole('ROLE_ARXIU')">
				<div class="container">
					<%@ include file="../headers/no_grants.jsp" %>
				</div>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN') || hasRole('ROLE_ARXIU')">
		    	<div class="container">
		    		<form:form method="POST" modelAttribute="fitxerForm" class="form-signin" action="${pageContext.request.contextPath}/arxiu/create?${_csrf.parameterName}=${_csrf.token}">
						<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<spring:bind path="pk.id">
							<form:input type="hidden" path="pk.id" class="form-control"></form:input>
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
									Nou registre (F3)
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
				        
				        <c:if test="${editMode == true}">
							<h4 class="form-signin-heading"><b>Referència:</b> ${fitxerForm.fileName} </h4>
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
						
						
						<c:if test="${editMode == false}">
							<h4 class="form-signin-heading">Tipologia</h4>
							<spring:bind path="pk.typeDocument">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:select path="pk.typeDocument" class="form-control" required="true">
										<form:option value="">-- Seleccionar  --</form:option>
										<form:option value="1">Imatge</form:option>
										<form:option value="2">Document</form:option>
										<form:option value="3">Digital</form:option>
									</form:select>
									<form:errors path="pk.typeDocument"></form:errors>
								</div>
							</spring:bind>
						</c:if>
						<c:if test="${editMode == true}">
							<h4 class="form-signin-heading">
								Tipologia:
								<c:if test="${fitxerForm.pk.typeDocument == '1'}">
									<b>Imatge</b>
								</c:if>
								<c:if test="${fitxerForm.pk.typeDocument == '2'}">
									<b>Document</b>
								</c:if>
								<c:if test="${fitxerForm.pk.typeDocument == '3'}">
									<b>Digital</b>
								</c:if>
							</h4>
							<spring:bind path="pk.typeDocument">
								<form:input type="hidden" path="pk.typeDocument" class="form-control"></form:input>
							</spring:bind>
						</c:if>
						
						<spring:bind path="paraulesClau">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="paraulesClau" class="form-control" placeholder="Paraules clau" autofocus="true"></form:input>
								<form:errors path="paraulesClau"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<h4 class="form-signin-heading">Autor</h4>
						<spring:bind path="autor_id">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:select path="autor_id" class="form-control">
									<form:option value="">-- Seleccionar  --</form:option>
									<c:forEach var="autor" items="${autorList}">
										<option value="${autor.id}" ${autor.id==fitxerForm.autor_id ? 'selected' : ''} >${autor.name}</option>
									</c:forEach>
								</form:select>
								<form:errors path="autor_id"></form:errors>
							</div>
						</spring:bind>
							
						<spring:bind path="referencia">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<c:if test="${fitxerForm.pk.typeDocument == '1'}">
									<form:input type="text" path="referencia" class="form-control" placeholder="Referència antic arxiu" autofocus="true"></form:input>
								</c:if>
								<c:if test="${fitxerForm.pk.typeDocument == '2'}">
									<form:input type="text" path="referencia" class="form-control" placeholder="Referència antic arxiu" autofocus="true" style="display: none;"></form:input>
								</c:if>
								<c:if test="${fitxerForm.pk.typeDocument == '3'}">
									<form:input type="text" path="referencia" class="form-control" placeholder="Referència antic arxiu" autofocus="true" style="display: none;"></form:input>
								</c:if>
								<form:errors path="referencia"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<!-- PARAMETRES PROPIS DE IMATGE -->
						<spring:bind path="ubicacio">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<c:if test="${fitxerForm.pk.typeDocument == '1'}">
									<form:input type="text" path="ubicacio" class="form-control" placeholder="Ubicació" autofocus="true"></form:input>
								</c:if>
								<c:if test="${fitxerForm.pk.typeDocument == '2'}">
									<form:input type="text" path="ubicacio" class="form-control" placeholder="Ubicació" autofocus="true" style="display: none;"></form:input>
								</c:if>
								<c:if test="${fitxerForm.pk.typeDocument == '3'}">
									<form:input type="text" path="ubicacio" class="form-control" placeholder="Ubicació" autofocus="true" style="display: none;"></form:input>
								</c:if>
								<form:errors path="ubicacio"></form:errors>
								<!-- cssClass="error" -->
							</div>
						</spring:bind>
						
						<!-- PARAMETRES PROPIS DE DOCUMENT -->
						<spring:bind path="ubicacioArxiu">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<c:if test="${fitxerForm.pk.typeDocument == '1'}">
									<form:input type="text" path="ubicacioArxiu" class="form-control" placeholder="Ubicació a l'arxiu" autofocus="true" style="display: none;"></form:input>
								</c:if>
								<c:if test="${fitxerForm.pk.typeDocument == '2'}">
									<form:input type="text" path="ubicacioArxiu" class="form-control" placeholder="Ubicació a l'arxiu" autofocus="true"></form:input>
								</c:if>
								<c:if test="${fitxerForm.pk.typeDocument == '3'}">
									<form:input type="text" path="ubicacioArxiu" class="form-control" placeholder="Ubicació a l'arxiu" autofocus="true"></form:input>
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
							<c:if test="${fitxerForm.pk.typeDocument != 3}">
								<h4 id="titleFile" class="form-signin-heading">Fitxer</h4>
								<div id="divFile" name="divFile" class="form-group">
									<label for="file">Selecciona un fitxer (C:/reis/load/)</label>
									<input type="file" id="file" name="file">
								</div>
							</c:if>
						</c:if>
						<c:if test="${editMode == true}">
							<div class="form-group" style="display: none;">
								<label for="file" style="display: none;">Fitxer</label>
							    <input type="file" id="file" name="file" style="display: none;">
							</div>
							<div class="tz-gallery-edit">
								<div style="width: 310px; float: center;">
				                	<div class="thumbnail">
				                		<c:if test="${fitxerForm.pk.typeDocument == 1}">
											<div style="background-image:url('/project/images/gd_reis1/thumbnails/${fitxerForm.fileName}.${fitxerForm.format}'); position: relative; float: center width: 265px; height: 214px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
											<div class="caption">
												<h3>Imatge</h3>
												<a href="/reis/arxiu/downloadImage?id=${fitxerForm.pk.id}&typeDocument=${fitxerForm.pk.typeDocument}" target="_blank" class="btn btn-sm btn-success">
													<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
													Descarregar
												</a>
												<a href="#" data-toggle="modal" data-target="#miModal" class="btn btn-sm btn-danger">
													<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
													Modificar imatge
												</a>
											</div>
										</c:if>
										<c:if test="${fitxerForm.pk.typeDocument == 2}">
											<c:choose>
											    <c:when test="${fitxerForm.format == 'doc' || fitxerForm.format == 'docx' || fitxerForm.format == 'xls' || fitxerForm.format == 'xlsx' || fitxerForm.format == 'pdf'}">
											        <div style="background-image:url('${contextPath}/resources/images/${fitxerForm.format}.png'); position: relative; float: center width: 265px; height: 214px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
											    </c:when>
											    <c:otherwise>
											        <div style="background-image:url('${contextPath}/resources/images/file.png'); position: relative; float: center width: 265px; height: 214px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
											    </c:otherwise>
											</c:choose>
											<div class="caption">
												<h3>Document ${fitxerForm.fileName}.${fitxerForm.format}</h3>
												<a href="/reis/arxiu/downloadImage?id=${fitxerForm.pk.id}&typeDocument=${fitxerForm.pk.typeDocument}" target="_blank" class="btn btn-sm btn-danger">
													<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
													Descarregar
												</a>
												<a href="#" data-toggle="modal" data-target="#miModal" class="btn btn-sm btn-danger">
													<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
													Modificar document
												</a>
											</div>
										</c:if>
										<c:if test="${fitxerForm.pk.typeDocument == 3}">
											<div style="background-image:url('${contextPath}/resources/images/digital.png'); position: relative; float: center width: 265px; height: 214px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
											<div class="caption">
												<h3>Digitalització</h3>
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
							<button class="btn btn-lg btn-default" type="button" onclick="javascript:window.location='consultaBack';">Tornar</button>
							<c:if test="${editMode == true}">
								<a href="#" class="btn btn-lg btn-danger" title="Eliminar" onclick="eliminarFitxer(${fitxerForm.pk.id}, ${fitxerForm.pk.typeDocument})">Eliminar</a>
								<button class="btn btn-lg btn-primary" type="submit">Modificar (F4)</button>
							</c:if>
							<c:if test="${editMode == false}">
								<button class="btn btn-lg btn-primary" type="submit">Crear (F4)</button>
							</c:if>
						</div>
						<br>
					</form:form>
				</div>
				
				<c:if test="${editMode == true}">
					<div class="modal fade" id="miModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h3 class="modal-title" id="myModalLabel">
										Modificar fitxer
									</h3>
								</div>
								<div class="modal-body">
									<form:form method="POST" modelAttribute="fitxerForm" class="form-signin" action="${pageContext.request.contextPath}/arxiu/updateFile?${_csrf.parameterName}=${_csrf.token}">
										<!--  HIDDENS -->
										<spring:bind path="pk.id">
											<form:input type="hidden" path="pk.id" class="form-control"></form:input>
										</spring:bind>
										<spring:bind path="pk.typeDocument">
											<form:input type="hidden" path="pk.typeDocument" class="form-control"></form:input>
										</spring:bind>
										<spring:bind path="fileName">
											<form:input type="hidden" path="fileName" class="form-control"></form:input>
										</spring:bind>
										<spring:bind path="format">
											<form:input type="hidden" path="format" class="form-control"></form:input>
										</spring:bind>
										
										<div class="form-group">
											<label for="file">Fitxer (C:/reis/load/)</label>
										    <input type="file" id="file" name="file">
										</div>
										<button class="btn btn-sm btn-primary" type="submit">Modificar (F4)</button>
									</form:form>
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</sec:authorize>
	    </c:if>
		
		<!-- /container -->
		
		<script src="${contextPath}/resources/js/jquery/1.9.1/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/jquery-ui.js"></script>
		<script src="${contextPath}/resources/js/moment.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap-tokenfield.min.js"></script>
	    <script type="text/javascript">
    		var elements = [${paraulesClauList}];
	    </script>
	    <script src="${contextPath}/resources/js/arxiu/registre.js"></script>
	</body>
</html>
