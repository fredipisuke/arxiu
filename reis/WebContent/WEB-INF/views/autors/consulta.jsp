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
	
	    <title>Arxiu Reis d'Igualada :: Consulta autors</title>
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
								<a href="${contextPath}/autors/registrationAutor" class="btn btn-success">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
									Crear
								</a>
							</div>
							
							<table id="tableAutors" class="table table-striped table-bordered" style="width:100%;">
						        <thead>
							        <tr class="info">
							        	<th width="25%" class="info">
							        		 Id
							        	</th>
							        	<th width="50%" class="info"> 
							        		Autor
							        	</th>
							        	<th width="25%" class="info"> 
							        		Accions
							        	</th>
							        </tr>
							    </thead>
							    <tbody>
							        <c:forEach var="autor" items="${autors}">
								    	<tr>
								    		<td> ${autor.id} </td>
								    		<td> ${autor.name} </td>
								    		<td>
								    			<div class="btn-group-xs" role="group">
									    			<a href="${contextPath}/autors/registrationAutor?id=${autor.id}" class="btn btn-primary">Editar</a>
									    			<a href="#" class="btn btn-danger" onclick="eliminarAutor(${autor.id})">Eliminar</a>
									    		</div>
								    		</td>
								    	</tr>
								    </c:forEach>
							    </tbody>
							    <tfoot>
									<tr>
										<th>&nbsp;</th>
										<th>
											<input type="text" placeholder="Buscar per autor">
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
		<script src="${contextPath}/resources/js/autors/consulta.js"></script>
	</body>
</html>
