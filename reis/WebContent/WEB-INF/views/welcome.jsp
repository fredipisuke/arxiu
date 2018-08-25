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
	
	    <title>Arxiu Reis d'Igualada :: Benvinguts</title>
		<link rel="icon" href="${contextPath}/resources/images/favicon.ico" type="image/x-icon">
	    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/thumbnail-gallery.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/lightbox.css" rel="stylesheet">
	    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
		    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	<body>
		<%@ include file="headers/login_header.jsp" %>
	    <c:if test="${pageContext.request.userPrincipal.name != null}">
	    	<div class="container">
				<div class="row">
					<div class="col">
				        <h2>
				        	Benvingut a l'arxiu documental dels Reis d'Igualada
				        </h2>
					</div>
				</div>
				
				<div class="row">
					<div class="col">
						<div class="tz-gallery">
							<c:forEach var="entry" items="${listImages}" varStatus="loop">
								<div style="width: 265px; float: left; padding-left: 15px; padding-right: 15px">
					                <div class="thumbnail">
					                	<c:if test="${entry.typeDocument == 1}">
					                    	<div style="background-image:url('/project/images/gd_reis1/thumbnails/${entry.fileName}.${entry.format}'); position: relative; float: left; width: 235px; height: 179px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
					                    </c:if>
					                    <div class="caption">
					                        <h3>${entry.titolResum}</h3>
					                        <p style="font-style: italic; text-align: justify; height: 40px;">
					                        	${entry.observacionsWellcome}
					                        </p>
					                    </div>
					                </div>
					            </div>
							</c:forEach>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col">
				        <h2>
				        	Estadístiques
				        </h2>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<strong>Nombre total d'imatges:</strong> ${estadistiques.totalImatges}
					</div>
				</div>
				<div class="row">
					<div class="col">
						<strong>Nombre total de documents:</strong> ${estadistiques.totalDocuments}
					</div>
				</div>
				
				<div class="row">
					<div class="col">
				        <h2>
				        	Copia de seguretat 
				        	<a href="#" class="btn btn-success" id="btnBackup" name="btnBackup" onclick="window.open('${contextPath}/backup/dataBase');">
								<span class="glyphicon glyphicon-save" aria-hidden="true"></span>
							</a>
				        </h2>
					</div>
				</div>
				
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<div class="row">
						<div class="col">
					        <h2>
					        	Crear Thumbnails
					        	<a href="${contextPath}/backup/thumbnails" class="btn btn-success" id="btnThumbnails" name="btnThumbnails">
									<span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
								</a>
					        </h2>
						</div>
					</div>
				</sec:authorize>
			</div>
	    </c:if>
		
		<!-- /container -->		
		<script src="${contextPath}/resources/js/jquery/3.3.1/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/lightbox.js"></script>
	</body>
</html>
