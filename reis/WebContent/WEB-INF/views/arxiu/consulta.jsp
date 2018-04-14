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
	
	    <title>Reis d'Igualada :: Consulta fitxers</title>
	    <link href="${contextPath}/resources/css/common2.css" rel="stylesheet">
		<link href="//code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/dataTables.bootstrap.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/tokenfield-typeahead.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/bootstrap-tokenfield.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/lightbox.css" rel="stylesheet">
	    <style>
	    	/* Ocultem el cercador superior */
	    	div.dataTables_filter {
			    visibility: hidden;
			}
			td.text-center {
				text-align: -webkit-center;
			}
			td.text-middle {
				vertical-align: middle !important;
			}
		</style>
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
				<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
		    	<div class="container">
					<div class="row">
						<div class="col">
					        <h2>
					        	Consulta
					        </h2>
					        
				         	<div class="form-group">
								<input id="titol" name="titol" type="text" class="form-control" placeholder="Títol document" autofocus="true" required="true"></input>
							</div>
							
							<div class="form-group">
								<input id="year" name="year" type="text" class="form-control" placeholder="Data" autofocus="true" required="true"></input>
							</div>
							
							<h4 class="form-signin-heading">Tipologia</h4>
							<div class="form-group">
								<select id="typeDocument" name="typeDocument" class="form-control" required="true">
									<option value="">-- Seleccionar  --</option>
									<option value="1">Imatge</option>
									<option value="2">Document</option>
								</select>
							</div>
							
							<div class="form-group">
								<input type="text" id="paraulesClau" name="paraulesClau" class="form-control" placeholder="Paraules clau" autofocus="true"></input>
							</div>
							<div align="center">
				        		<a href="#" class="btn btn-success" id="btnSearch" name="btnSearch">
									<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
									Buscar
								</a>
							</div>
					        
				        	<div align="right">
				        		<a href="${contextPath}/arxiu/registre" class="btn btn-success">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
									Crear
								</a>
								<a href="#" class="btn btn-success" id="btnExcel" name="btnExcel">
									<span class="glyphicon glyphicon-book" aria-hidden="true"></span>
									Excel
								</a>
								<a href="#" class="btn btn-success" id="btnPDF" name="btnPDF">
									<span class="glyphicon glyphicon-book" aria-hidden="true"></span>
									PDF
								</a>
							</div>
							
							<table id="tableFitxers" class="table table-striped table-bordered" style="width:100%;">
						        <thead>
							        <tr class="info">
							        	<th width="10%" class="info">
							        		Document
							        	</th>
							        	<th width="75%" class="info">
							        		Titol/Descripció
							        	</th>
							        	<th width="15%" class="info"> 
							        		Accions
							        	</th>
							        </tr>
							    </thead>
							    <tbody>
							        <c:forEach var="entry" items="${listImages}">
								    	<tr>
								    		<td align="center">
												<c:if test="${entry.typeDocument == 1}">
							                    	<a href="/project/images/gd_reis1/${entry.fileName}" data-lightbox="images" data-title="${entry.titol}" title="Veure">
								                    	<div style="background-image:url('/project/images/gd_reis1/${entry.fileName}'); position: relative; float: center; width: 50px; height: 50px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover;">
								                    		<span class="glyphicon glyphicon-search" aria-hidden="true" style="margin-top: 30px; margin-left: 55px"></span>
								                    	</div>
								                    </a>
							                    </c:if>
							                    <c:if test="${entry.typeDocument == 2}">
							                    	<c:choose>
													    <c:when test="${entry.format == 'doc' || entry.format == 'docx' || entry.format == 'xls' || entry.format == 'xlsx' || entry.format == 'ppt' || entry.format == 'pdf'}">
													        <div style="background-image:url('${contextPath}/resources/images/${entry.format}.png'); position: relative; float: center; width: 50px; height: 50px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover;"></div>
													    </c:when>
													    <c:otherwise>
													        <div style="background-image:url('${contextPath}/resources/images/file.png'); position: relative; float: center; width: 50px; height: 50px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover;"></div>
													    </c:otherwise>
													</c:choose>
							                    </c:if>
											</td>
								    		<td> 
									    		<strong>${entry.titol}</strong>
									    		<br>
									    		${entry.observacionsResum}
								    		</td>
								    		<td>
												<a download="${entry.fileName}" href="/project/images/gd_reis1/${entry.fileName}" target="_blank" class="btn btn-success" title="Descarregar">
													<span class="glyphicon glyphicon-download" aria-hidden="true" style="font-size: 1.6em;vertical-align: middle;"></span>
												</a>
												<a href="${contextPath}/arxiu/registre?id=${entry.id}" class="btn btn-primary" title="Editar">
													<span class="glyphicon glyphicon-pencil" aria-hidden="true" style="font-size: 1.6em;vertical-align: middle;"></span>
												</a>
								    		</td>
								    	</tr>
								    </c:forEach>
							    </tbody>
						    </table>
						</div>
					</div>
				</div>
			</sec:authorize>
	    </c:if>
		
		<!-- /container -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script src="//code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
		<script src="${contextPath}/resources/js/moment.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap-tokenfield.min.js"></script>
		<script src="${contextPath}/resources/js/jquery.dataTables.min.js"></script>
		<script src="${contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/lightbox.js"></script>
		<script type="text/javascript">
    		var elements = [];
	    </script>
		<script src="${contextPath}/resources/js/arxiu/consulta.js"></script>
	</body>
</html>
