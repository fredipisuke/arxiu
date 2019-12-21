$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	// Ocult per defecte
	if($('#searchOn').val()!="true"){
		hideData();
		$(".search-group").show();
		$("#btnExpand").hide();
		$("#btnColapse").show();
	} else {
		// Busquem les dades
		search();
	}
	$("#btnExpand").click(function(){
		$(".search-group").show();
		$("#btnExpand").hide();
		$("#btnColapse").show();
		$("#searchValues").hide();
	});
	$("#btnColapse").click(function(){
		$(".search-group").hide();
		$("#btnExpand").show();
		$("#btnColapse").hide();
		$("#searchValues").show();
	});
	$("#nom").change(function(){
		hideData();
	});
	$("#edat").change(function(){
		hideData();
	});
	$("#sexe").change(function(){
		hideData();
	});
	$("#escola_id").change(function(){
		hideData();
	});
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
		var url = "/reis/nens/downloadExcel"
				+ "?nom=" + $("#nom").val()
				+ "&edat=" + $("#edat").val()
				+ "&sexe=" + $("#sexe").val()
				+ "&escola=" + $("#escola_id").val();
		window.open(url);
    });
	$('#btnPDF').click(function () {
		var url = "/reis/nens/downloadPDF"
				+ "?nom=" + $("#nom").val()
				+ "&edat=" + $("#edat").val()
				+ "&sexe=" + $("#sexe").val()
				+ "&escola=" + $("#escola_id").val();
		window.open(url);
    });
});

function hideData(){
	$('#tableNens').hide();
	$('#tableNensLength').hide();
	$("#tableNensTotals").hide();
	$("#searchValues").html("");
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
	var params = {};
	params["nom"] = $("#nom").val();
	params["edat"] = $("#edat").val();
	params["sexe"] = $("#sexe").val();
	params["escola_id"] = $("#escola_id").val();
	// Paràmetres de la cerca
	var searchValues = "";
	if($("#nom").val()!=null && $("#nom").val()!=""){
		searchValues += "[<b>Nom:</b> " + $("#nom").val() + "] ";
	}
	if($("#edat").val()!=null && $("#edat").val()!=""){
		searchValues += "[<b>Edat:</b> " + $("#edat").val() + "] ";
	}
	if($("#sexe").val()=='H'){
		searchValues += " [<b>Sexe:</b> Home] ";
	} else if($("#typeDocument").val()=='D'){
		searchValues += "[<b>Sexe:</b> Dona] ";
	}
	if($("#escola_id").val()!=null && $("#escola_id").val()!=""){
		searchValues += "[<b>Escola:</b> " + $("#escola_id option:selected").text() + "] ";
	}
	$("#searchValues").html(searchValues);
	$("#searchValues").show();
	var pagina = $("#pagina").val();
	if(pagina==null || pagina=="") pagina = 0;
	params["pagina"] = pagina;
	params["nElementsPerPage"] = $("#nElementsPerPage").val();
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : '/reis/nens/consultaNens?_csrf=' + $("#_csrf").val(),
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
		$(".search-group").hide();
		$("#btnExpand").show();
		$("#btnColapse").hide();
	}
}

function createTable(data){
	jQuery("#tableNens").show();
	jQuery("#tableNensLength").show();
	jQuery("#tableNens").find("tr:gt(0)").remove();
	var tbody = jQuery("#tableNens");
	// Carraguem els nous valors a la taula
	for(var i=0; i<data.result.length; i++){
		var miniImage = "";
		if(data.result[i].pk.typeDocument==1){
			miniImage = "<a href=\"/project/images/gd_reis1/" + data.result[i].fileName + "." + data.result[i].format + "\" data-lightbox=\"images\" data-title=\"" + data.result[i].titol + "\" title=\"Veure\">"
				        	+ "<div style=\"background-image:url('/project/images/gd_reis1/thumbnails/" + data.result[i].fileName + "." + data.result[i].format + "'); position: relative; float: center; width: 45px; height: 45px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover;\">"
				        		+ "<span class=\"glyphicon glyphicon-search\" aria-hidden=\"true\" style=\"margin-top: 30px; margin-left: 55px\"></span>"
				        	+ "</div>"
				        + "</a>";
		} else if(data.result[i].pk.typeDocument==2){
			if(data.result[i].format == 'doc' || data.result[i].format == 'docx' || data.result[i].format == 'xls' || data.result[i].format == 'xlsx' || data.result[i].format == 'ppt' || data.result[i].format == 'pdf'){
				miniImage = "<div style=\"background-image:url('/reis/resources/images/" + data.result[i].format + ".png'); position: relative; float: center; width: 50px; height: 50px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover;\"></div>";
			} else {
				miniImage = "<div style=\"background-image:url('/reis/resources/images/file.png'); position: relative; float: center; width: 45px; height: 45px; background-position: 50% 50%; background-repeat: no-repeat;background-size: cover;\"></div>";
			}
		}
		var trow = jQuery("<tr>");
		jQuery("<td align='center'>")
					.html(miniImage)
					.appendTo(trow);
		jQuery("<td align='left'>")
					.html("<strong>" + data.result[i].fileName + " - " + data.result[i].titol + "</strong><br>" + data.result[i].observacionsResum)
					.appendTo(trow);
		jQuery("<td align='center' style='vertical-align: middle;'>")
					.html("<a href=\"/reis/nens/downloadImage?id=" + data.result[i].pk.id + "&typeDocument=" + data.result[i].pk.typeDocument + "\" target=\"_blank\" class=\"btn btn-success\" title=\"Descarregar\">"
							+ "<span class=\"glyphicon glyphicon-download\" aria-hidden=\"true\" style=\"font-size: 1.6em;vertical-align: middle;\"></span>"
							+ "</a>"
							+ "&nbsp;"
							+ "<a href=\"registre?id=" + data.result[i].pk.id + "&typeDocument=" + data.result[i].pk.typeDocument + "\" class=\"btn btn-primary\" title=\"Editar\">"
								+ "<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\" style=\"font-size: 1.6em;vertical-align: middle;\"></span>"
							+ "</a>"
							+ "&nbsp;"
							+ "<a href=\"#\" class=\"btn btn-danger\" title=\"Eliminar\" onclick=\"eliminarFitxer(" + data.result[i].pk.id + ", " + data.result[i].pk.typeDocument + ")\">"
								+ "<span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\" style=\"font-size: 1.6em;vertical-align: middle;\"></span>"
							+ "</a>")
					.appendTo(trow);
		trow.appendTo(tbody);
	}

	var pagina = parseInt($("#pagina").val());
	var totalPages = Math.ceil(parseInt(data.total)/$("#nElementsPerPage").val());
	var nElements = (pagina * $("#nElementsPerPage").val()) + 1;
	var nElementsMax = ((pagina + 1) * $("#nElementsPerPage").val());
	if(nElementsMax>data.total)
		nElementsMax = data.total;
	if(data.total>0){
		jQuery("#tableNensInfo").html("<b>Total: " + data.total + "&nbsp;&nbsp;&nbsp;&nbsp; Mostrant element " + nElements + " al " + nElementsMax + "</b>");
	} else {
		jQuery("#tableNensInfo").html("No hi han resultats amb els par&agrave;metres introduits");
	}
	
	// BOTONS DE PÀGINACIÓ
	var tableNensPaginacio = "";
	if(data.total > 0){
		tableNensPaginacio = "<ul class='pagination' style='margin-top:0px; white-space:nowrap'>";
		
		// RESULTATS AMB 5 PAGINES
		if(totalPages <= 5){
			// BOTÓ ANTERIOR
			if(pagina == 0){
				tableNensPaginacio += "<li class='paginate_button previous disabled' id='tableNens_previous'>"
											+ "<a href='#' aria-controls='tableNens' data-dt-idx='0' tabindex='0'>Anterior</a>"
										+ "</li>";
			} else {
				tableNensPaginacio += "<li class='paginate_button previous' id='tableNens_previous'>"
											+ "<a href='javascript:searchBack()' aria-controls='tableNens' data-dt-idx='0' tabindex='0'>Anterior</a>"
										+ "</li>";
			}
			
			// BOTONS INTERMITJOS
			for(var i=1; i<=5 && i<=totalPages; i++){
				var idPage = i - 1;
				if(pagina==idPage){
					tableNensPaginacio += "<li class='paginate_button active'>"
												+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableNens' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
											+ "</li>";
				} else {
					tableNensPaginacio += "<li class='paginate_button'>"
												+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableNens' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
											+ "</li>";
				}
			}

			// BOTO SEGÜENT
			if(pagina < totalPages - 1){
				tableNensPaginacio += "<li class='paginate_button next' id='tableNens_next'>"
											+ "<a href='javascript:searchNext()' aria-controls='tableNens' data-dt-idx='0' tabindex='0'>Seg&uuml;ent</a>"
										+ "</li>";
			} else {
				tableNensPaginacio += "<li class='paginate_button next disabled' id='tableNens_next'>"
											+ "<a href='#' aria-controls='tableNens' data-dt-idx='0' tabindex='0'>Seg&uuml;ent</a>"
										+ "</li>";
			}
		} else {
			// BOTÓ ANTERIOR
			if(pagina == 0){
				tableNensPaginacio += "<li class='paginate_button previous disabled' id='tableNens_previous'>"
											+ "<a href='#' aria-controls='tableNens' data-dt-idx='0' tabindex='0'>Anterior</a>"
										+ "</li>";
			} else {
				tableNensPaginacio += "<li class='paginate_button previous' id='tableNens_previous'>"
											+ "<a href='javascript:searchBack()' aria-controls='tableNens' data-dt-idx='0' tabindex='0'>Anterior</a>"
										+ "</li>";
			}
			
			// BOTONS INTERMITJOS
			if(pagina < 3){
				for(var i=1; i<=4; i++){
					var idPage = i - 1;
					if(pagina==idPage){
						tableNensPaginacio += "<li class='paginate_button active'>"
													+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableNens' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
												+ "</li>";
					} else {
						tableNensPaginacio += "<li class='paginate_button'>"
													+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableNens' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
												+ "</li>";
					}
				}
				tableNensPaginacio += "<li class='paginate_button previous disabled' id='tableNens_previous'>"
											+ "<a href='#' aria-controls='tableNens' data-dt-idx='...' tabindex='...'>...</a>"
										+ "</li>";
				tableNensPaginacio += "<li class='paginate_button'>"
											+ "<a href='javascript:searchPage(" + (totalPages-1) + ")' aria-controls='tableNens' data-dt-idx='" + (totalPages-1) + "' tabindex='" + (totalPages-1) + "'>" + totalPages + "</a>"
										+ "</li>";
			} else if(pagina < totalPages-3){
				tableNensPaginacio += "<li class='paginate_button'>"
											+ "<a href='javascript:searchPage(0)' aria-controls='tableNens' data-dt-idx='0' tabindex='0'>1</a>"
										+ "</li>";
				tableNensPaginacio += "<li class='paginate_button previous disabled' id='tableNens_previous'>"
											+ "<a href='#' aria-controls='tableNens' data-dt-idx='...' tabindex='...'>...</a>"
										+ "</li>";
				for(var i=pagina; i<pagina+3; i++){
					var idPage = i - 1;
					if(pagina==idPage){
						tableNensPaginacio += "<li class='paginate_button active'>"
													+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableNens' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
												+ "</li>";
					} else {
						tableNensPaginacio += "<li class='paginate_button'>"
													+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableNens' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
												+ "</li>";
					}
				}
				tableNensPaginacio += "<li class='paginate_button previous disabled' id='tableNens_previous'>"
											+ "<a href='#' aria-controls='tableNens' data-dt-idx='...' tabindex='...'>...</a>"
										+ "</li>";
				tableNensPaginacio += "<li class='paginate_button'>"
											+ "<a href='javascript:searchPage(" + (totalPages-1) + ")' aria-controls='tableNens' data-dt-idx='" + (totalPages-1) + "' tabindex='" + (totalPages-1) + "'>" + totalPages + "</a>"
										+ "</li>";
			} else {
				tableNensPaginacio += "<li class='paginate_button'>"
											+ "<a href='javascript:searchPage(0)' aria-controls='tableNens' data-dt-idx='0' tabindex='0'>1</a>"
										+ "</li>";
				tableNensPaginacio += "<li class='paginate_button previous disabled' id='tableNens_previous'>"
											+ "<a href='#' aria-controls='tableNens' data-dt-idx='...' tabindex='...'>...</a>"
										+ "</li>";
				for(var i=totalPages-3; i<=totalPages; i++){
					var idPage = i - 1;
					if(pagina==idPage){
						tableNensPaginacio += "<li class='paginate_button active'>"
													+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableNens' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
												+ "</li>";
					} else {
						tableNensPaginacio += "<li class='paginate_button'>"
													+ "<a href='javascript:searchPage(" + idPage + ")' aria-controls='tableNens' data-dt-idx='" + i + "' tabindex='" + i + "'>" + i + "</a>"
												+ "</li>";
					}
				}
			}
			
			// BOTO SEGÜENT
			if(pagina < totalPages - 1){
				tableNensPaginacio += "<li class='paginate_button next' id='tableNens_next'>"
											+ "<a href='javascript:searchNext()' aria-controls='tableNens' data-dt-idx='0' tabindex='0'>Seg&uuml;ent</a>"
										+ "</li>";
			} else {
				tableNensPaginacio += "<li class='paginate_button next disabled' id='tableNens_next'>"
											+ "<a href='#' aria-controls='tableNens' data-dt-idx='0' tabindex='0'>Seg&uuml;ent</a>"
										+ "</li>";
			}
		}
		tableNensPaginacio += "</ul>";
	}
	jQuery("#tableNensPaginacio").html(tableNensPaginacio);
	jQuery("#tableNensTotals").show();
}

function eliminarFitxer(id, typeDocument){
	if(!confirm("Estar segur que vol eliminar el fitxer?")){
		return;
	}
	window.location = "/reis/nens/eliminar?id=" + id
						+ "&searchOn=true" 
						+ "&nom=" + $("#nom").val()
						+ "&edat=" + $("#edat").val()
						+ "&sexe=" + $("#sexe").val()
						+ "&escola=" + $("#escola_id").val();;
}

function goError(data){
	$('#txtMsgError').html(data.msg);
	$('#divMsgError').show();
}

function cleanErrors(){
	$('#divMsgError').hide();
	$('#txtMsgError').html("");
}