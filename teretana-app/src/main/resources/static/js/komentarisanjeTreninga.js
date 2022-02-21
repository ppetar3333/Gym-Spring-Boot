$(document).ready(function() {

	var tekstInput = $("textarea[name=tekst]");
	var ocenaInput = $("input[name=ocena]");
	var selectedOptionInput = $('#anoniman');
	
	function dodaj() {
		
		var tekst = tekstInput.val();
		var ocena = ocenaInput.val();
		var anoniman = selectedOptionInput.val();

		if(parseInt(ocena) >= 1 && parseInt(ocena) <= 5 && tekst != "") {

			var params = {
				tekstKomentara: tekst,
				ocena: ocena,
				anoniman: anoniman
			}
			console.log(params)
			
			$.post("KorisnikKomentar/komentar",
				params,
				function(){
					window.location.href = ''
				}
	
			)
			
		} else {
			alert("Ocena mora biti od 1 do 5 ili morate uneti tekst");
		}
		
		
		return false;
	}
	$("form").submit(dodaj)
})