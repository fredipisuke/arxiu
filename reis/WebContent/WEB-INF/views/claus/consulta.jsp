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
	
	    <title>Arxiu Reis d'Igualada :: Consulta claus</title>
		<link rel="icon" href="${contextPath}/resources/images/favicon.ico" type="image/x-icon">
	    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/dataTables.bootstrap.min.css" rel="stylesheet">
	    <style>
	    	/* Ocultem el cercador superior */
	    	div.dataTables_filter {
			    visibility: hidden;
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
				<input type="hidden" id="orderBy" name="orderBy" value="idBtn">
				<input type="hidden" id="typeOrder" name="typeOrder" value="A">
		    	<div class="container">
					<div class="row">
						<div class="col">
					        <h2>
					        	Consulta
					        </h2>
				        	<div align="right">
								<a href="${contextPath}/claus/registrationClau" class="btn btn-success">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
									Crear
								</a>
								<a href="${contextPath}/claus/downloadExcel" class="btn btn-success">
									<span class="glyphicon glyphicon-book" aria-hidden="true"></span>
									Excel
								</a>
								<a href="${contextPath}/claus/downloadPDF" class="btn btn-success">
									<span class="glyphicon glyphicon-book" aria-hidden="true"></span>
									PDF
								</a>
							</div>
							
							<table id="tableClaus" class="table table-striped table-bordered" style="width:100%;">
						        <thead>
							        <tr class="info">
							        	<th width="10%" class="info">
							        		 Id
							        	</th>
							        	<th width="30%" class="info">
							        		 Tipus
							        	</th>
							        	<th width="35%" class="info"> 
							        		Clau
							        	</th>
							        	<th width="25%" class="info"> 
							        		Accions
							        	</th>
							        </tr>
							    </thead>
							    <tbody>
							        <c:forEach var="clau" items="${claus}">
								    	<tr>
								    		<td> ${clau.id} </td>
								    		<td> 
									    		<c:if test = "${clau.type == 1}">
									    			Imatge
									    			<span class='glyphicon glyphicon-camera' aria-hidden='true' style='font-size: 1.15em; vertical-align: middle;'></span>
									    		</c:if>
									    		<c:if test = "${clau.type == 2}">
									    			Document
									    			<span class='glyphicon glyphicon-book' aria-hidden='true' style='font-size: 1.15em; vertical-align: middle;'></span>
									    		</c:if>
								    		</td>
								    		<td> ${clau.name} </td>
								    		<td>
								    			<div class="btn-group-xs" role="group">
									    			<a href="${contextPath}/claus/registrationClau?id=${clau.id}" class="btn btn-primary">Editar</a>
									    			<a href="#" class="btn btn-danger" onclick="eliminarClaus(${clau.id})">Eliminar</a>
									    		</div>
								    		</td>
								    	</tr>
								    </c:forEach>
							    </tbody>
							    <tfoot>
									<tr>
										<th>&nbsp;</th>
										<th>
											<input type="text" placeholder="Buscar per tipologia">
										</th>
										<th>
											<input type="text" placeholder="Buscar per clau">
										</th>
										<th>&nbsp;</th>
									</tr>
								</tfoot>
						    </table>
						</div>
					</div>
				</div>
			</sec:authorize>
	    </c:if>
		
		<!-- /container -->
		<script src="${contextPath}/resources/js/jquery/3.3.1/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/jquery.dataTables.min.js"></script>
		<script src="${contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/claus/consulta.js"></script>
	</body>
</html>
