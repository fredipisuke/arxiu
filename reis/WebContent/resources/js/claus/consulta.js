$(document).ready(function(){
	var table = $('#tableClaus').DataTable( {
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
	
	table.columns().every( function () {
		var that = this;
		$( 'input', this.footer() ).on( 'keyup change', function () {
			if ( that.search() !== this.value ) {
				that.search( this.value )
	                .draw();
	        }
	    });
	});
});

$(document).on('keydown', 'body', function(event) {
    if(event.keyCode==115){ //F4
        event.preventDefault();
        window.location = "/reis/claus/registrationClau";
     }
});

function eliminarClau(id){
	if(!confirm("Estar segur que vol eliminar la clau?")){
		return;
	}
	window.location = "/reis/claus/deleteClaus?id=" + id;
}

function searchClaus(){
	var params = {}
	params["name"] = $("#name").val();
	params["tipus"] = $("#tipus").val();
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : '/reis/claus/consultaClaus?_csrf=' + $("#_csrf").val(),
        data : JSON.stringify(params),
		dataType : 'json',
		timeout : 100000,
        success : goSuccessConsulta,
        error: goError
	});
}

function goSuccessConsulta(data){
	if(data.msg!=null && data.msg!="") goError(data);
	if(data.result!=null){
		createTable(data);
		$("#tableClaus").show();
	}
}

function createTable(data){
	var tbl = $('#tableClaus').DataTable();
	
	// Borrem els elements de la taula
	tbl.rows().remove().draw(false);
	
	// Carraguem els nous valors a la taula
	for(var i=0; i<data.result.length; i++){
		var tipologia = "";
		if(data.result[i].type==1){
			tipologia = "Imatge <span class='glyphicon glyphicon-camera' aria-hidden='true' style='font-size: 1.15em; vertical-align: middle;'></span>";
		} else if(data.result[i].type==2){
			tipologia = "Document <span class='glyphicon glyphicon-book' aria-hidden='true' style='font-size: 1.15em; vertical-align: middle;'></span>"
		}
        tbl.row.add( [
        	data.result[i].id,
        	tipologia,
        	data.result[i].name,
        	"<div class='btn-group-xs' role='group'> "
				+ " <a href='registrationClau?id=" + data.result[i].id + "' class='btn btn-primary'>Editar</a>"
				+ " <a href='deleteClau?id=" + data.result[i].id + "' class='btn btn-danger'>Eliminar</a>"
			+ " </div>"
        ] ).draw( false );
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