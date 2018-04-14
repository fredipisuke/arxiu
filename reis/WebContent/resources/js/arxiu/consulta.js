$(document).ready(function(){
	var table = $('#tableFitxers').DataTable( {
		"columnDefs": [
		      { className: "text-center", "targets": [0] },
		      { className: "text-center text-middle", "targets": [2] }
		    ],
		"columns": [
		      { "orderable": false },
		      null,
		      null
		    ],
	    order: [[1, 'asc']],
		"language": {
			"decimal":        	",",
			"emptyTable":     	"No hi han dades",
			"info": 			"Mostrant p&agrave;gina _PAGE_ de _PAGES_",
            "infoEmpty": 		"Sense dades disponibles",
            "infoFiltered": 	"(filtrant per _MAX_ total de resultats)",
            "infoPostFix":    	"",
            "thousands":      	".",
            "lengthMenu": 		"Mostrant _MENU_ elements per p&agrave;gina",
            "loadingRecords": 	"Carregant...",
            "processing":     	"Processant...",
            "search":         	"Cercar:",
            "zeroRecords": 		"No hi han dades",
            "paginate": {
                "first":      "Primer",
                "last":       "&Uacute;ltim",
                "next":       "Seg&uumlent",
                "previous":   "Anterior"
            },
            "aria": {
                "sortAscending":  ": activa ordenació ascendent de la columna",
                "sortDescending": ": activa ordenació descendent de la columna"
            }
        }
	});
	// Ocult per defecte
	hideData();
	
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
		if(!elements.includes(e.attrs.value)){
			alert("Clau invalida")
			return false;
		}
	});
	
	$('#btnSearch').click(function () {
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
	$('#tableFitxers_wrapper').hide();
	$('#btnExcel').hide();
	$('#btnPDF').hide();
	var tbl = $('#tableFitxers').DataTable();
	// Borrem els elements de la taula
	tbl.rows().remove().draw(false);
}

function search(){
	var params = {}
	params["titol"] = $("#titol").val();
	params["year"] = $("#year").val();
	params["typeDocument"] = $("#typeDocument").val();
	params["paraulesClau"] = $("#paraulesClau").val();
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
		$("#tableFitxers_wrapper").show();
		if(data.result.length>0){
			$('#btnExcel').show();
			$('#btnPDF').show();
		}
	}
}

function createTable(data){
	var tbl = $('#tableFitxers').DataTable();
	// Borrem els elements de la taula
	tbl.rows().remove().draw(false);
	
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
		
        tbl.row.add( [
        	// Document
        	miniImage,
        	
        	// Titol/Descripció
        	"<strong>" + data.result[i].titol + "</strong><br>" + data.result[i].observacionsResum,
        	
        	// Accions
        	"<a download=\"" + data.result[i].fileName + "\" href=\"/project/images/gd_reis1/" + data.result[i].fileName + "\" target=\"_blank\" class=\"btn btn-success\" title=\"Descarregar\">"
				+ "<span class=\"glyphicon glyphicon-download\" aria-hidden=\"true\" style=\"font-size: 1.6em;vertical-align: middle;\"></span>"
			+ "</a>"
			+ "&nbsp;"
			+ "<a href=\"registre?id=" + data.result[i].id + "\" class=\"btn btn-primary\" title=\"Editar\">"
				+ "<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\" style=\"font-size: 1.6em;vertical-align: middle;\"></span>"
			+ "</a>"
        ] ).draw( false );
	}
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