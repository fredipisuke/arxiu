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
	
	    <title>Reis d'Igualada :: Gestió impresores</title>
	    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/dataTables.bootstrap.min.css" rel="stylesheet">
	    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
		    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	<body>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
	       	<%@ include file="../headers/login_header.jsp" %>
			<sec:authorize access="!hasRole('ROLE_ADMIN')">
				<div class="container">
					<%@ include file="../headers/no_grants.jsp" %>
				</div>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<div class="container">
					<div class="row">
						<div class="col">
					        <h2>
					        	Gestió impresores
					        </h2>
				        	<div align="right">
								<a href="${contextPath}/configuration/registrationPrinter" class="btn btn-success">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
									Crear impresora
								</a>
							</div>
							<br>
					        <table id="tablePrinters" class="table table-striped table-bordered" style="width:100%;">
						        <thead>
							        <tr class="info">
							        	<th width="10%" class="info">
							        	 	Id
							        	 </th>
							        	<th width="65%" class="info"> 
							        		Impresora
							        	</th>
							        	<th width="25%" class="info"> 
							        		Accions
							        	</th>
							        </tr>
							    </thead>
							    <tbody>
							        <c:forEach var="printer" items="${printers}">
								    	<tr>
								    		<td> ${printer.id} </td>
								    		<td> ${printer.printername} </td>
								    		<td>
								    			<div class="btn-group-xs" role="group">
									    			<a href="${contextPath}/configuration/registrationPrinter?id=${printer.id}" class="btn btn-primary">Editar</a>
									    			<a href="${contextPath}/configuration/deletePrinter?id=${printer.id}" class="btn btn-danger">Eliminar</a>
									    			<a href="${contextPath}/configuration/printTest?printername=${printer.printername}" class="btn btn-success">Test</a>
									    		</div>
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
	    
		<script src="${contextPath}/resources/js/jquery/3.3.1/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/jquery.dataTables.min.js"></script>
		<script src="${contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/configuration/config_printers.js"></script>
	</body>
</html>
