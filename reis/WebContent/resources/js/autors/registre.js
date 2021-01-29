$(document).ready(function(){
});

$(document).on('keydown', 'body', function(event) {
    if(event.keyCode==115){ //F4
    	event.preventDefault();
    	$("form").submit();
     }
});

function eliminarAutor(id){
	if(!confirm("Estar segur que vol eliminar l'autor?")){
		return;
	}
	wwindow.location = "/reis/autors/deleteAutors?id=" + id;
}


