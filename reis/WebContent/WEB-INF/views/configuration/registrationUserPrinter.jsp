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
		<meta name="description" content="">
		<meta name="author" content="Reis d'Igualada">
		
		<title>Reis d'Igualada :: Impresora</title>
		
	    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
		<link href="${contextPath}/resources/css/common2.css" rel="stylesheet">
	    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
		    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	
	<body>
		<%@ include file="../headers/login_header.jsp" %>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<div class="container">
				<form id="printTicketForm" method="GET" action="${contextPath}/configuration/printTest">
		            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		            <input type="hidden" name="printerid" value=""/>
		            <input type="hidden" name="printername" value=""/>
		            <input type="hidden" name="redirect" value="/configuration/registrationUserPrinter"/>
		        </form>
				<form:form method="POST" modelAttribute="userForm" class="form-signin">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					
					<h2 class="form-signin-heading">Impresora</h2>
					
					<spring:bind path="printer">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:select path="printer.id" class="form-control">
								<form:option value="">-- Impresora --</form:option>
						 		<form:options items="${printers}" itemLabel="printername" itemValue="id" />
							</form:select>
							<form:errors path="printer"></form:errors>
						</div>
					</spring:bind>
					
					<h4>Impressió de prova</h4>
 					<a onclick="printTest()" class="btn btn-success">Imprimir</a>
					<br><br>

					<div align="center">
						<button class="btn btn-lg btn-primary" type="submit">Modificar</button>
					</div>
					
				</form:form>
			</div>
		</c:if>
		<!-- /container -->
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			function printTest(){
				document.forms['printTicketForm'].printerid.value = document.getElementById("printer.id").options[document.getElementById("printer.id").selectedIndex].value;
				document.forms['printTicketForm'].printername.value = document.getElementById("printer.id").options[document.getElementById("printer.id").selectedIndex].text;
				document.forms['printTicketForm'].submit();
			}
		</script>
	</body>
</html>
