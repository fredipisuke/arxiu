$(document).ready(function(){
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
	
	$("#pk\\.typeDocument").change(function(){
		$('#paraulesClau').tokenfield('destroy');
		$('#paraulesClau').val("");
		if($("#pk\\.typeDocument").val()!=null && $("#pk\\.typeDocument").val()!=""){
			if($("#pk\\.typeDocument").val()=="1"){
				$("#ubicacio").show();
				$("#ubicacioArxiu").hide();
			} else if($("#pk\\.typeDocument").val()=="2"){
				$("#ubicacioArxiu").show();
				$("#ubicacio").hide();
			}
			searchClaus();
		}
	});
});

function eliminarFitxer(id, typeDocument){
	if(!confirm("Estar segur que vol eliminar el fitxer?")){
		return;
	}
	window.location = "/reis/arxiu/eliminar?id=" + id + "&typeDocumentPk=" + typeDocument;
}

function searchClaus(){
	var params = {}
	params["typeDocument"] = $("#pk\\.typeDocument").val();
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

function goError(){
	$('#paraulesClau').tokenfield('destroy');	
}