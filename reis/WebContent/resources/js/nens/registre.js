$(document).ready(function(){	
	if($("#tipusDocument").val()=="1"){
		$("#divDocument").show();
	} else {
		$("#divDocument").hide();
	}
	
	$("#tipusDocument").change(function(){
		if($("#tipusDocument").val()=="1"){
			$("#divDocument").show();
		} else {
			$("#divDocument").hide();
		}
		$("#document").val("");
	});
});

function eliminarFitxer(id){
	if(!confirm("Estar segur que vol eliminar el fitxer?")){
		return;
	}
	window.location = "/reis/nens/eliminar?id=" + id;
}