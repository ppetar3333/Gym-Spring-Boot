package com.ftn.teretana.controllers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.service.TerminTreningaService;

@Controller
@RequestMapping(value = "TreninziClan")
public class ZakazaniTreninziController {

	@Autowired
	private TerminTreningaService terminTreningaService;
	
	@GetMapping(value = "/prikazTreninga")
	private String prikazTreninga(Model model, HttpSession session) {
		
		
		List<TerminTreninga> zakazaniTreninzi = new ArrayList<>();
		Korisnik korisnik = (Korisnik) session.getAttribute("clan");
		
		for(TerminTreninga termin : terminTreningaService.findAll()) {
			if(termin.getClan().getId() == korisnik.getId()) {
				zakazaniTreninzi.add(termin);
			}
		}
		
		model.addAttribute("zakazaniTreninzi", zakazaniTreninzi);
		return "pojedinacniModeli/zakazaniTreninzi";
	}
	
	@GetMapping(value = "/otkaziTermin")
	private ModelAndView otkaziTermin(@RequestParam int id, Model model, HttpSession session ) {
		
		TerminTreninga terminZaOtkazivanje = terminTreningaService.findOneByID(id);
		String poruka = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		List<TerminTreninga> terminiZaOtkazivanje = new ArrayList<>();
		LocalDateTime trenutniDatum = LocalDateTime.now();
		
		if(trenutniDatum.isBefore(terminZaOtkazivanje.getDatumOdrzavanja())) {
			terminiZaOtkazivanje.add(terminZaOtkazivanje);
		}
		
		boolean mozeOtkazatiTrening = trenutniDatum.isBefore(terminZaOtkazivanje.getDatumOdrzavanja().minusDays(1));
		
		if(!terminiZaOtkazivanje.isEmpty()) {
			if(mozeOtkazatiTrening == true) {
				poruka += "Uspesno ste otkazali trening";
				
				terminZaOtkazivanje.setClan(null);
				terminZaOtkazivanje.setPopunjen(false);
				terminTreningaService.update(terminZaOtkazivanje);
				
				
			} else {
				poruka += "Trening pocinje za manje od 24h, ne mozete ga otkazati!";
			}
			
		} else {
			poruka += "Trening je prosao, ne mozete ga otkazati";
		}
		
		List<TerminTreninga> zakazaniTreninzi = new ArrayList<>();
		Korisnik korisnik = (Korisnik) session.getAttribute("clan");
		
		for(TerminTreninga termin : terminTreningaService.findAll()) {
			if(termin.getClan().getId() == korisnik.getId()) {
				zakazaniTreninzi.add(termin);
			}
		}
		
		model.addAttribute("zakazaniTreninzi", zakazaniTreninzi);
		
		ModelAndView rezultat = new ModelAndView("pojedinacniModeli/zakazaniTreninzi");
		
		rezultat.addObject("poruka", poruka);
		
		return rezultat;
	}
	
}
