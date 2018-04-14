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
	    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/thumbnail-gallery.css" rel="stylesheet">
	    <link href="${contextPath}/resources/css/lightbox.css" rel="stylesheet">
	    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
		    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	<body class="bodyGallery">
		<%@ include file="../headers/login_header.jsp" %>
	    <c:if test="${pageContext.request.userPrincipal.name != null}">
	    	<sec:authorize access="!hasRole('ROLE_ADMIN') && !hasRole('ROLE_ARXIU')">
				<div class="container">
					<%@ include file="../headers/no_grants.jsp" %>
				</div>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN') || hasRole('ROLE_ARXIU')">
				<div class="container">
					<div class="tz-gallery">
					
						<c:forEach var="entry" items="${listImages}" varStatus="loop">
							<div style="width: 265px; float: left; padding-left: 15px; padding-right: 15px">
				                <div class="thumbnail">
				                	<c:if test="${entry.typeDocument == 1}">
				                    	<div style="background-image:url('/project/images/gd_reis1/${entry.fileName}'); position: relative; float: left; width: 235px; height: 179px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
				                    </c:if>
				                    <c:if test="${entry.typeDocument == 2}">
				                    	<c:choose>
										    <c:when test="${entry.format == 'doc' || entry.format == 'docx' || entry.format == 'xls' || entry.format == 'xlsx' || entry.format == 'ppt' || entry.format == 'pdf'}">
										        <div style="background-image:url('${contextPath}/resources/images/${entry.format}.png'); position: relative; float: left; width: 235px; height: 179px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
										    </c:when>
										    <c:otherwise>
										        <div style="background-image:url('${contextPath}/resources/images/file.png'); position: relative; float: left; width: 235px; height: 179px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover; margin-bottom: 20px;"></div>
										    </c:otherwise>
										</c:choose>
				                    </c:if>
				                    <div class="caption">
				                        <h3>${entry.titolResum}</h3>
				                        <p style="font-style: italic; text-align: justify; height: 40px;">
				                        	${entry.observacionsResum}
				                        </p>
				                        <div align="center" style="padding-top: 10px;">
				                        	<c:if test="${entry.typeDocument == 1}">
					                        	<a href="/project/images/gd_reis1/${entry.fileName}" class="btn btn-sm btn-success" data-lightbox="images" data-title="${entry.titol}" title="Veure">
													<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
												</a>
											</c:if>
											<a href="/project/images/gd_reis1/${entry.fileName}" target="_blank" class="btn btn-sm btn-success" title="Descarregar">
												<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
											</a>
											<a href="${contextPath}/arxiu/registre?id=${entry.id}" class="btn btn-sm btn-primary" title="Editar">
												<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
											</a>
										</div>
				                    </div>
				                </div>
				            </div>
						</c:forEach>
						
					</div>
				</div>
			</sec:authorize>
	    </c:if>
		
		<!-- /container -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
		<script src="${contextPath}/resources/js/lightbox.js"></script>
	</body>
</html>
