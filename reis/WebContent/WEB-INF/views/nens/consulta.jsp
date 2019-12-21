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
	
	    <title>Reis d'Igualada :: Consulta patges infantils</title>
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
	    	<sec:authorize access="!hasRole('ROLE_ADMIN') && !hasRole('ROLE_NENS')">
				<div class="container">
					<%@ include file="../headers/no_grants.jsp" %>
				</div>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN') || hasRole('ROLE_NENS')">
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
					        	Consulta patges infantils
					        </h2>
					        
				         	<div class="form-group search-group">
								<input id="nom" name="nom" type="text" class="form-control" placeholder="nom" autofocus="true" required="true" value="${nom}" data-toggle="tooltip" title="Nom" data-original-title="Nom"></input>
							</div>
							
				         	<div class="form-group search-group">
								<input id="edat" name="edat" type="text" class="form-control" placeholder="edat" autofocus="true" required="true" value="${edat}" data-toggle="tooltip" title="Edat" data-original-title="Edat"></input>
							</div>
							
							<h4 class="form-signin-heading search-group">Sexe</h4>
							<div class="form-group search-group">
								<select id="sexe" name="sexe" class="form-control" required="true" data-toggle="tooltip" title="Sexe" data-original-title="Sexe">
									<option value="">-- Seleccionar  --</option>
									<option value="H" ${sexe=="H" ? "selected" : ""}>Home</option>
									<option value="D" ${sexe=="D" ? "selected" : ""}>Dona</option>
								</select>
							</div>
							
							<h4 class="form-signin-heading search-group">Escola</h4>
							<div class="form-group search-group">
								<select id="escola_id" name="escola_id" class="form-control" data-toggle="tooltip" title="Escola" data-original-title="Escola">
									<option value="">-- Seleccionar  --</option>
									<c:forEach var="escola" items="${escolaList}" varStatus="loop">
										<option value="${escola.id}" ${escola.id==escola_id ? 'selected' : ''} >${escola.descripcio}</option>
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
				        		<a href="${contextPath}/nens/registre" class="btn btn-success">
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
							<div class="row" id="tableNensLength" name="tableNensLength" style="${listNens.size()>0 ? 'display:;' : 'display:none;'}">
								<div class="col-sm-6">
									<div class="dataTables_length" id="tableNens_length">
										<label> 
											Mostrant 
											<select id="nElementsPerPage" name="nElementsPerPage" aria-controls="tableNens" class="input-sm">
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
							<table id="tableNens" class="table table-striped table-bordered" style="width:100%; ${listNens.size()>0 ? 'display:;' : 'display:none;'}">
						        <tr class="info">
						        	<th width="10%" class="info">
						        		Document
						        	</th>
						        	<th width="75%" class="info">
						        		Nen
						        	</th>
						        	<th width="15%" class="info"> 
						        		Accions
						        	</th>
						        </tr>
						    </table>
						    
						    <div class="row" id="tableNensTotals" name="tableNensTotals" style="${listNens.size()>0 ? 'display:;' : 'display:none;'}">
						    	<div class="col-sm-5" align="left">
						    		<div class="dataTables_info" id="tableNensInfo" name="tableNensInfo" role="status" aria-live="polite" style="margin-top:0px; white-space:nowrap"></div>
						    	</div>
						    	<div class="col-sm-7" align="right">
						    		<div id="tableNensPaginacio" name="tableNensPaginacio"></div>
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
		<script src="${contextPath}/resources/js/nens/consulta.js"></script>
	</body>
</html>