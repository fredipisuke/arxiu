<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ca">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		<meta name="description" content="">
		<meta name="author" content="">
		
		<title>Arxiu Reis d'Igualada :: Login</title>
		<link rel="icon" href="${contextPath}/resources/images/favicon.ico" type="image/x-icon">
		<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
		<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	
	<body>
		<div class="container">
			<form method="POST" action="${contextPath}/login" class="form-signin">
				<div style="text-align: center;">
					<img class="img-circle" src="${contextPath}/resources/images/icon-192x192.png">
				</div>
				<h2 class="form-heading" style="text-align: center;">Acc�s Arxiu Reis d'Igualada</h2>
	
				<div class="form-group ${error != null ? 'has-error' : ''}">
					<span>${message}</span> 
					<input name="username" type="text" class="form-control" placeholder="Usuari" autofocus="true" /> 
					<input name="password" type="password" class="form-control" placeholder="Contrasenya" /> 
					<span>${error}</span>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	
					<button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
					
				</div>
			</form>
		</div>
		<!-- /container -->
		<script
			src="${contextPath}/resources/js/jquery/1.11.2/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
	</body>
</html>
