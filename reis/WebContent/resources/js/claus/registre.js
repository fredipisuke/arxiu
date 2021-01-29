$(document).ready(function(){
});

$(document).on('keydown', 'body', function(event) {
    if(event.keyCode==115){ //F4
    	event.preventDefault();
    	$("form").submit();
     }
});

function eliminarClau(id){
	if(!confirm("Estar segur que vol eliminar la clau?")){
		return;
	}
	window.location = "/reis/claus/deleteClaus?id=" + id;
}


