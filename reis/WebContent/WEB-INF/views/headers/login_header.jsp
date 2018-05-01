<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">
				<strong>
					<img class="img-circle" src="/reis/resources/images/icon-36x36.png" width="25px">
					Arxiu Reis d'Igualada
				</strong>
			</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="${NavBarIniciActive}">
					<a href="${contextPath}/welcome">Inici</a>
				</li>
				<sec:authorize access="hasRole('ROLE_ADMIN') || hasRole('ROLE_ARXIU')">
					<li class="dropdown ${NavBarArxiuActive}">
						<a href="${contextPath}/arxiu" class="dropdown-toggle"	data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"> Arxiu <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="${NavBarArxiuConsultaActive}"><a href="${contextPath}/arxiu/consulta">Consulta</a></li>
							<li class="${NavBarArxiuRegistreActive}"><a href="${contextPath}/arxiu/registre">Registre</a></li>
							<li role="separator" class="divider"></li>
							<li class="dropdown-header">Configuracions</li>
							<li class="${NavBarClausActive}"><a href="${contextPath}/claus/consulta">Gestió de claus</a></li>
						</ul>
					</li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="dropdown ${NavBarConfigurationActive}">
						<a href="${contextPath}/configuration" class="dropdown-toggle"	data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Configuració <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="${NavBarUsersActive}"><a href="${contextPath}/configuration/config_users">Usuaris</a></li>
							<li class="${NavBarRolesActive}"><a href="${contextPath}/configuration/config_roles">Perfils</a></li>
							<li class="${NavBarPrintersActive}"><a href="${contextPath}/configuration/config_printers">Impresores</a></li>
							<li role="separator" class="divider"></li>
							<li class="${NavBarUserPrinterActive}"><a href="${contextPath}/configuration/registrationUserPrinter">Impresora</a></li>
						</ul>
					</li>
				</sec:authorize>
			</ul>
			<form class="navbar-form navbar-right" id="logoutForm" method="POST" action="${contextPath}/logout">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="form-group">
					Usuari: 
					<strong> 
						${pageContext.request.userPrincipal.name} 
						<sec:authorize access="hasRole('ROLE_ADMIN')"> (Administrador) </sec:authorize>
					</strong>
				</div>
				<a onclick="document.forms['logoutForm'].submit()" class="btn btn-danger">
					<span class="glyphicon glyphicon-off" aria-hidden="true" style="font-size: 1.15em; vertical-align: middle;"></span>
				</a>
			</form>
		</div>
	</div>
</nav>