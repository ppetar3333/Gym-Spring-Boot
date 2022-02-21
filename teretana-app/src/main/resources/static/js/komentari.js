function json(response) {
	return response.json()
}

fetch('Admin/komentariLista', {
	method: 'get'
	}).then(response => {
	  return response.text()
	}).then((data) => {
		console.log(data ? JSON.parse(data) : {});
		listaKomentara = JSON.parse(data);

		var table = document.getElementById("komentari");
		
		if(JSON.parse(data).length > 0) {
			
			for(var i=0; i < listaKomentara.length; i++) {
			
				var tr = document.createElement('TR');
				table.appendChild(tr);
				
				var tdTekst = document.createElement('TD');
				tdTekst.appendChild(document.createTextNode(listaKomentara[i].tekstKomentara));
				tr.appendChild(tdTekst);
				
				var tdOcena = document.createElement('TD');
				tdOcena.appendChild(document.createTextNode(listaKomentara[i].ocena));
				tr.appendChild(tdOcena);
				
				var tdDatumPostavljanja = document.createElement('TD');
				tdDatumPostavljanja.appendChild(document.createTextNode(listaKomentara[i].datumPostavljanja));
				tr.appendChild(tdDatumPostavljanja);

				var tdClan = document.createElement('TD');
				tdClan.appendChild(document.createTextNode(listaKomentara[i].clan.ime));
				tr.appendChild(tdClan);
				
				var tdTrening = document.createElement('TD');
				tdTrening.appendChild(document.createTextNode(listaKomentara[i].trening.naziv));
				tr.appendChild(tdTrening);
				
		        var tdFormaOdbij = document.createElement('TD')
		        var formOdbij = document.createElement('FORM')
	
				formOdbij.setAttribute('method', "post")
		       	formOdbij.setAttribute('action', "Admin/komentariLista/odbijKomentar")
	
				var inputHiddenOdbij = document.createElement('INPUT')
				
		        inputHiddenOdbij.setAttribute('type', "hidden")
		        inputHiddenOdbij.setAttribute('name', "idKomentara")
		        inputHiddenOdbij.setAttribute('value', listaKomentara[i].id)
	
		        var inputSubmitOdbij = document.createElement('INPUT')
		        inputSubmitOdbij.setAttribute('type', "submit")
		        inputSubmitOdbij.setAttribute('value', "Odbij komentar")
	
		        formOdbij.appendChild(inputHiddenOdbij)
		        formOdbij.appendChild(inputSubmitOdbij)
		       
		        tdFormaOdbij.appendChild(formOdbij)
		       
		        tr.appendChild(tdFormaOdbij);

		        var tdFormaOdobri = document.createElement('TD')
		        var formOdobri = document.createElement('FORM')
	
				formOdobri.setAttribute('method', "post")
		       	formOdobri.setAttribute('action', "Admin/komentariLista/odobriKomentar")
	
				var inputHiddenOdobri = document.createElement('INPUT')
				
		        inputHiddenOdobri.setAttribute('type', "hidden")
		        inputHiddenOdobri.setAttribute('name', "idKomentara")
		        inputHiddenOdobri.setAttribute('value', listaKomentara[i].id)
	
		        var inputSubmitOdobri = document.createElement('INPUT')
		        inputSubmitOdobri.setAttribute('type', "submit")
		        inputSubmitOdobri.setAttribute('value', "Odobri komentar")
	
		        formOdobri.appendChild(inputHiddenOdobri)
		        formOdobri.appendChild(inputSubmitOdobri)
		       
		        tdFormaOdobri.appendChild(formOdobri)
		       
		        tr.appendChild(tdFormaOdobri);
			}
		
		} else {
			var div = document.createElement("div");
			div.innerHTML = "Nema komentara";
			table.appendChild(div);
		}
		
	})
  .catch(function (error) {
	console.log('Request Failed', error);
});