$(document).ready(function(){
	$('#tableUsers').DataTable( {
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
	} );
});