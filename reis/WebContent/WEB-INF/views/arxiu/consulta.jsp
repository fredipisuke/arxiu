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
	
	    <title>Arxiu Reis d'Igualada :: Consulta fitxers</title>
		<link rel="icon" href="${contextPath}/resources/images/favicon.ico" type="image/x-icon">
	    <link href="${contextPath}/resources/css/common2.css" rel="stylesheet">
		<link href="${contextPath}/resources/css/jquery-ui.css" rel="stylesheet">
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
				<input type="hidden" id="searchOn" name="searchOn" value="${searchOn}" />
		    	<div class="container">
					<div class="row">
						<div class="col">
					        <h2>
					        	<a href="#" class="btn btn-primary" id="btnExpand" name="btnExpand" style="display: none;">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
								</a>
					        	<a href="#" class="btn btn-primary" id="btnColapse" name="btnColapse" style="display:;">
									<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
								</a>
					        	Consulta
					        </h2>
					        
				         	<div class="form-group search-group">
								<input id="fileName" name="fileName" type="text" class="form-control" placeholder="Referència" autofocus="true" required="true" value="${fileName}" data-toggle="tooltip" title="Referència de l'aplicació" data-original-title="Referència de l'aplicació"></input>
							</div>
							
							<div class="form-group search-group">
								<input id="referencia" name="referencia" type="text" class="form-control" placeholder="Referència antic arxiu" autofocus="true" required="true" value="${referencia}" data-toggle="tooltip" title="Referència existent en un antic arxiu" data-original-title="Referència existent en un antic arxiu"></input>
							</div>
							
				         	<div class="form-group search-group">
								<input id="titol" name="titol" type="text" class="form-control" placeholder="Títol document" autofocus="true" required="true" value="${titol}" data-toggle="tooltip" title="Títol del document" data-original-title="Títol del document"></input>
							</div>
							
							<div class="form-group search-group">
								<input id="year" name="year" type="text" class="form-control" placeholder="Any" autofocus="true" required="true" value="${year}"  data-toggle="tooltip" title="Any" data-original-title="Any"></input>
							</div>
							
							<h4 class="form-signin-heading search-group">Tipologia</h4>
							<div class="form-group search-group">
								<select id="typeDocument" name="typeDocument" class="form-control" required="true"  data-toggle="tooltip" title="Tipologia del document" data-original-title="Tipologia del document">
									<option value="">-- Seleccionar  --</option>
									<option value="1" ${typeDocument=="1" ? "selected" : ""}>Imatge</option>
									<option value="2" ${typeDocument=="2" ? "selected" : ""}>Document</option>
									<option value="3" ${typeDocument=="3" ? "selected" : ""}>Digital</option>
								</select>
							</div>
							
							<div class="form-group search-group">
								<input type="text" id="paraulesClau" name="paraulesClau" class="form-control" placeholder="Paraules clau" autofocus="true" value="${paraulesClau}" data-toggle="tooltip" title="Paraules clau" data-original-title="Paraules clau"></input>
							</div>
							
							<h4 class="form-signin-heading search-group">Autor</h4>
							<div class="form-group search-group">
								<select id="autor_id" name="autor_id" class="form-control" data-toggle="tooltip" title="Autor del fitxer" data-original-title="Autor del fitxer">
									<option value="">-- Seleccionar  --</option>
									<c:forEach var="autor" items="${autorList}" varStatus="loop">
										<option value="${autor.id}" ${autor.id==autor_id ? 'selected' : ''} >${autor.name}</option>
									</c:forEach>
								</select>
							</div>
							
							
							<div class="row" id="searchValues" name="searchValues" style="display:none; margin-bottom: 5px;"></div>
							
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
							
							<input type="hidden" id="pagina" name="pagina" value="${pagina!=null ? pagina : '0'}" />
							<div class="row" id="tableFitxersLength" name="tableFitxersLength" style="${listFitxers.size()>0 ? 'display:;' : 'display:none;'}">
								<div class="col-sm-6">
									<div class="dataTables_length" id="tableFitxers_length">
										<label> 
											Mostrant 
											<select id="nElementsPerPage" name="nElementsPerPage" aria-controls="tableFitxers" class="input-sm">
												<option value="10">10</option>
												<option value="25">25</option>
												<option value="50">50</option>
												<option value="100">100</option>
											</select> 
											elements per pàgina
										</label>
									</div>
								</div>
								<div class="col-sm-6"></div>
							</div>
							<table id="tableFitxers" class="table table-striped table-bordered" style="width:100%; ${listFitxers.size()>0 ? 'display:;' : 'display:none;'}">
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
						    </table>
						    
						    <div class="row" id="tableFitxersTotals" name="tableFitxersTotals" style="${listFitxers.size()>0 ? 'display:;' : 'display:none;'}">
						    	<div class="col-sm-5" align="left">
						    		<div class="dataTables_info" id="tableFitxersInfo" name="tableFitxersInfo" role="status" aria-live="polite" style="margin-top:0px; white-space:nowrap"></div>
						    	</div>
						    	<div class="col-sm-7" align="right">
						    		<div id="tableFitxersPaginacio" name="tableFitxersPaginacio"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</sec:authorize>
	    </c:if>
		
		<!-- /container -->
		<script src="${contextPath}/resources/js/jquery/1.9.1/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/jquery-ui.js"></script>
		<script src="${contextPath}/resources/js/moment.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap-tokenfield.min.js"></script>
		<script src="${contextPath}/resources/js/lightbox.js"></script>
		<script type="text/javascript">
    		var elements = [${paraulesClauList}];
	    </script>
		<script src="${contextPath}/resources/js/arxiu/consulta.js"></script>
	</body>
</html>