$(document).ready(function(){
});

$(document).on('keydown', 'body', function(event) {
    if(event.keyCode==115){ //F4
    	event.preventDefault();
    	$("form").submit();
     }
});

function eliminarEscola(id){
	if(!confirm("Estar segur que vol eliminar l'escola?")){
		return;
	}
	window.location = "/reis/escoles/deleteEscola?id=" + id;
}


