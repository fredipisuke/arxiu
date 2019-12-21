$(document).ready(function(){	
	
});

function eliminarFitxer(id){
	if(!confirm("Estar segur que vol eliminar el fitxer?")){
		return;
	}
	window.location = "/reis/nens/eliminar?id=" + id;
}