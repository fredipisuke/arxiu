$(document).ready(function(){
	// Ocult per defecte
	if($('#searchOn').val()!="true"){
		hideData();
	}
	$("#titol").change(function(){
		hideData();
	});
	$("#year").change(function(){
		hideData();
	});
	$("#typeDocument").change(function(){
		hideData();
		$('#paraulesClau').tokenfield('destroy');
		$('#paraulesClau').val("");
		if($("#typeDocument").val()!=null && $("#typeDocument").val()!=""){
			searchClaus();
		}
	});
	$('#paraulesClau').tokenfield({
		  autocomplete: {
		    source: elements,
		    delay: 100
		  },
		  showAutocompleteOnFocus: true
		});
	$('#paraulesClau').on('tokenfield:createtoken', function (e) {
		hideData();
		// INTERNET EXPLORER FALLA
		if(!elements.includes(e.attrs.value)){
			alert("Clau invalida")
			return false;
		}
	});
	if($("#typeDocument").val()==""){
		$('#paraulesClau').tokenfield('destroy');
		$('#paraulesClau').hide();
	}
	$("#nElementsPerPage").change(function(){
		hideData();
		$("#pagina").val(0);
		search();
	});
	$('#btnSearch').click(function () {
		$("#pagina").val(0);
		search();
    });
	$('#btnExcel').click(function () {
		var url = "/reis/arxiu/downloadExcel"
				+ "?titol=" + $("#titol").val()
				+ "&year=" + $("#year").val()
				+ "&typeDocument=" + $("#typeDocument").val()
				+ "&paraulesClau=" + $("#paraulesClau").val();
		window.open(url);
    });
	$('#btnPDF').click(function () {
		var url = "/reis/arxiu/downloadPDF"
				+ "?titol=" + $("#titol").val()
				+ "&year=" + $("#year").val()
				+ "&typeDocument=" + $("#typeDocument").val()
				+ "&paraulesClau=" + $("#paraulesClau").val();
		window.open(url);
    });
});

function hideData(){
	$('#tableFitxers').hide();
	$('#tableFitxersLength').hide();
	jQuery("#tableFitxersTotals").hide();
	$('#btnExcel').hide();
	$('#btnPDF').hide();
}


function searchPage(pg){
	$("#pagina").val(pg);
	search();
}
function searchNext(){
	$("#pagina").val(parseInt($("#pagina").val()) + 1);
	search();
}
function searchBack(){
	$("#pagina").val(parseInt($("#pagina").val()) - 1);
	search();
}
function search(){
	var params = {}
	params["titol"] = $("#titol").val();
	params["year"] = $("#year").val();
	params["typeDocument"] = $("#typeDocument").val();
	params["paraulesClau"] = $("#paraulesClau").val();
	var pagina = $("#pagina").val();
	if(pagina==null || pagina=="") pagina = 0;
	params["pagina"] = pagina;
	params["nElementsPerPage"] = $("#nElementsPerPage").val();
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : '/reis/arxiu/consultaFitxers?_csrf=' + $("#_csrf").val(),
        data : JSON.stringify(params),
		dataType : 'json',
		timeout : 100000,
        success : goSuccess,
        error: goError
	});
}

function goSuccess(data){
	if(data.msg!=null && data.msg!="") goError(data);
	if(data.result!=null){
		createTable(data);
		if(data.result.length>0){
			$('#btnExcel').show();
			$('#btnPDF').show();
		}
	}
}

function createTable(data){
	jQuery("#tableFitxers").show();
	jQuery("#tableFitxersLength").show();
	jQuery("#tableFitxers").find("tr:gt(0)").remove();
	var tbody = jQuery("#tableFitxers");
	// Carraguem els nous valors a la taula
	for(var i=0; i<data.result.length; i++){
		var miniImage = "";
		if(data.result[i].typeDocument==1){
			miniImage = "<a href=\"/project/images/gd_reis1/" + data.result[i].fileName + "\" data-lightbox=\"images\" data-title=\"" + data.result[i].titol + "\" title=\"Veure\">"
				        	+ "<div style=\"background-image:url('/project/images/gd_reis1/" + data.result[i].fileName + "'); position: relative; float: center; width: 50px; height: 50px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover;\">"
				        		+ "<span class=\"glyphicon glyphicon-search\" aria-hidden=\"true\" style=\"margin-top: 30px; margin-left: 55px\"></span>"
				        	+ "</div>"
				        + "</a>";
		} else if(data.result[i].typeDocument==2){
			if(data.result[i].format == 'doc' || data.result[i].format == 'docx' || data.result[i].format == 'xls' || data.result[i].format == 'xlsx' || data.result[i].format == 'ppt' || data.result[i].format == 'pdf'){
				miniImage = "<div style=\"background-image:url('/reis/resources/images/" + data.result[i].format + ".png'); position: relative; float: center; width: 50px; height: 50px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover;\"></div>";
			} else {
				miniImage = "<div style=\"background-image:url('/reis/resources/images/file.png'); position: relative; float: center; width: 50px; height: 50px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover;\"></div>";
			}
		}
		var trow = jQuery("<tr>");
		jQuery("<td align='center'>")
					.html(miniImage)
					.appendTo(trow);
		jQuery("<td align='left'>")
					.html("<strong>" + data.result[i].titol + "</strong><br>" + data.result[i].observacionsResum)
					.appendTo(trow);
		jQuery("<td align='center' style='vertical-align: middle;'>")
					.html("<a download=\"" + data.result[i].fileName + "\" href=\"/project/images/gd_reis1/" + data.result[i].fileName + "\" target=\"_blank\" class=\"btn btn-success\" title=\"Descarregar\">"
							+ "<span class=\"glyphicon glyphicon-download\" aria-hidden=\"true\" style=\"font-size: 1.6em;vertical-align: middle;\"></span>"
							+ "</a>"
							+ "&nbsp;"
							+ "<a href=\"registre?id=" + data.result[i].id + "\" class=\"btn btn-primary\" title=\"Editar\">"
								+ "<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\" style=\"font-size: 1.6em;vertical-align: middle;\"></span>"
							+ "</a>"
							+ "&nbsp;"
							+ "<a href=\"#\" class=\"btn btn-danger\" title=\"Eliminar\" onclick=\"eliminarFitxer(" + data.result[i].id + ")\">"
								+ "<span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\" style=\"font-size: 1.6em;vertical-align: middle;\"></span>"
							+ "</a>")
					.appendTo(trow);
		trow.appendTo(tbody);
	}

	var pagina = parseInt($("#pagina").val());
	var totalPages = Math.round(parseInt(data.total)/$("#nElementsPerPage").val());
	var nElements = (pagina * $("#nElementsPerPage").val()) + 1;
	if(data.total>0){
		jQuery("#tableFitxersInfo").text("Mostrant " + nElements + " de " + data.total);
	} else {
		jQuery("#tableFitxersInfo").text("No hi han resultats amb els par&agrave;metres introduits");
	}
	var tableFitxersPaginacio = "<ul class='pagination'>";
	
	// BOTÓ PRIMER
	if(pagina>=1){
		tableFitxersPaginacio += "<li class='paginate_button previous' id='tableFitxers_previous'>"
									+ "<a href='javascript:searchBack()' aria-controls='tableFitxers' data-dt-idx='0' tabindex='0'>Anterior</a>"
								+ "</li>";
		if(pagina>1){
			tableFitxersPaginacio += "<li class='paginate_button'>"
										+ "<a href='javascript:searchPage(0)' aria-controls='tableFitxers' data-dt-idx='0' tabindex='0'>1</a>"
									+ "</li>";
		}
	} else {
		tableFitxersPaginacio += "<li class='paginate_button previous disabled' id='tableFitxers_previous'>"
									+ "<a href='#' aria-controls='tableFitxers' data-dt-idx='0' tabindex='0'>Anterior</a>"
								+ "</li>";
	}
	
	// BOTONS INTERMITJOS
	if(pagina<4){
		var count = 2;
		if(pagina<=1){
			count = 1;
		}
		for(var i=count; i<=5 && i<=totalPages+1; i++){
			var idPage = i - 1;
			if(pagina==idPage){
				tableFitxersPaginacio += "<li class='paginate_button active'>"
											+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableFitxers' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
										+ "</li>";
			} else {
				tableFitxersPaginacio += "<li class='paginate_button'>"
											+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableFitxers' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
										+ "</li>";
			}
		}
		if(totalPages>5){
			tableFitxersPaginacio += "<li class='paginate_button previous disabled' id='tableFitxers_previous'>"
										+ "<a href='#' aria-controls='tableFitxers' data-dt-idx='...' tabindex='...'>...</a>"
									+ "</li>";
		}
	} else if(pagina < totalPages-4){
		tableFitxersPaginacio += "<li class='paginate_button previous disabled' id='tableFitxers_previous'>"
									+ "<a href='#' aria-controls='tableFitxers' data-dt-idx='...' tabindex='...'>...</a>"
								+ "</li>";
		for(var i=pagina; i<pagina+3; i++){
			var idPage = i - 1;
			if(pagina==idPage){
				tableFitxersPaginacio += "<li class='paginate_button active'>"
											+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableFitxers' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
										+ "</li>";
			} else {
				tableFitxersPaginacio += "<li class='paginate_button'>"
											+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableFitxers' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
										+ "</li>";
			}
		}
		tableFitxersPaginacio += "<li class='paginate_button previous disabled' id='tableFitxers_previous'>"
									+ "<a href='#' aria-controls='tableFitxers' data-dt-idx='...' tabindex='...'>...</a>"
								+ "</li>";
	} else {
		tableFitxersPaginacio += "<li class='paginate_button previous disabled' id='tableFitxers_previous'>"
									+ "<a href='#' aria-controls='tableFitxers' data-dt-idx='...' tabindex='...'>...</a>"
								+ "</li>";
		for(var i=totalPages-4; i<=totalPages+1; i++){
			var idPage = i - 1;
			if(pagina==idPage){
				tableFitxersPaginacio += "<li class='paginate_button active'>"
											+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableFitxers' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
										+ "</li>";
			} else {
				tableFitxersPaginacio += "<li class='paginate_button'>"
											+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableFitxers' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
										+ "</li>";
			}
		}
	}
	
	// BOTÓ ÚLTIM
	if(pagina < totalPages){
		if(pagina < totalPages-4){
			var idPage = totalPages;
			var i = totalPages + 1;
			tableFitxersPaginacio += "<li class='paginate_button'>"
										+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableFitxers' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
									+ "</li>";
		}
		tableFitxersPaginacio += "<li class='paginate_button next' id='tableFitxers_next'>"
									+ "<a href='javascript:searchNext()' aria-controls='tableFitxers' data-dt-idx='0' tabindex='0'>Seg&uuml;ent</a>"
								+ "</li>";
	} else {
		tableFitxersPaginacio += "<li class='paginate_button next disabled' id='tableFitxers_next'>"
									+ "<a href='#' aria-controls='tableFitxers' data-dt-idx='0' tabindex='0'>Seg&uuml;ent</a>"
								+ "</li>";
	}
	tableFitxersPaginacio += "</ul>";
	if(data.total>0){
		jQuery("#tableFitxersPaginacio").html(tableFitxersPaginacio);
	}
	jQuery("#tableFitxersTotals").show();
}

function eliminarFitxer(id){
	window.location = "/reis/arxiu/eliminar?id=" + id
						+ "&searchOn=true" 
						+ "&titol=" + $("#titol").val()
						+ "&year=" + $("#year").val()
						+ "&typeDocument=" + $("#typeDocument").val()
						+ "&paraulesClau=" + $("#paraulesClau").val();
}

function searchClaus(){
	var params = {}
	params["typeDocument"] = $("#typeDocument").val();
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : '/reis/claus/getClaus?_csrf=' + $("#_csrf").val(),
        data : JSON.stringify(params),
		dataType : 'json',
		timeout : 100000,
        success : goSuccessConsulta,
        error: goError
	});
}

function goSuccessConsulta(data){
	$('#paraulesClau').tokenfield('destroy');
	if(data.result!=null){
		var listValues = [];
		for(var i=0; i<data.result.length; i++){
			listValues.push(data.result[i].name);
		}
		elements = listValues;
		$('#paraulesClau').tokenfield({
			  autocomplete: {
			    source: elements,
			    delay: 100
			  },
			  showAutocompleteOnFocus: true
			});
		$('#paraulesClau').on('tokenfield:createtoken', function (e) {
			if(!elements.includes(e.attrs.value)){
				alert("Clau invalida")
				return false;
			}
		});
	}
}

function goError(data){
	$('#txtMsgError').html(data.msg);
	$('#divMsgError').show();
}

function cleanErrors(){
	$('#divMsgError').hide();
	$('#txtMsgError').html("");
}