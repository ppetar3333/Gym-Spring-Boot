package com.ftn.teretana.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.teretana.models.Izvestaj;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.TerminTreningaService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value = "Izvestaji")
public class IzvestajiController {

	@Autowired
	private TerminTreningaService terminTreningaService;
	
	@Autowired
	private TreningService treningService;
	
	@GetMapping(value = "/prikazStranice")
	private String prikazStranice(Model model) {
		
		List<TerminTreninga> rezultatiIzvestaja = new ArrayList<>();
		
		model.addAttribute("rezultatiIzvestaja",rezultatiIzvestaja);
		
		return "izvestaj/izvestajiAdmin";
	}
	
	@GetMapping(value = "/izborDatuma")
	private ModelAndView izborDatuma(@RequestParam(required=false) String datumOd, @RequestParam(required=false) String datumDo,
			@RequestParam(required=false) String sortiraj, @RequestParam(required=false) String ascDesc, Model model) {
		
		// napisan query koji vraca id treninga, naziv treninga, trenera, broj termina, cenu treninga za datum OD-DO
		List<Izvestaj> rezultatiIzvestaja = terminTreningaService.getTermineOdDo(datumOd, datumDo);
		
		// ukupno svega
		double ukupnaCena = 0;
		double ukupanBrojZakazanihTermina = 0;
		
		for(Izvestaj izvestaji : rezultatiIzvestaja) {
			ukupnaCena += izvestaji.getCena();
			ukupanBrojZakazanihTermina += izvestaji.getBrojuZakazanihTermina();
		}
		
		// sortiranje
		if(sortiraj.equals("brojuZakazanihTermina")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(rezultatiIzvestaja, Comparator.comparing(Izvestaj::getBrojuZakazanihTermina));
			} else {
				Collections.sort(rezultatiIzvestaja, Comparator.comparing(Izvestaj::getBrojuZakazanihTermina).reversed());
			}
		}
		
		if(sortiraj.equals("ceni")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(rezultatiIzvestaja, Comparator.comparing(Izvestaj::getCena));
			} else {
				Collections.sort(rezultatiIzvestaja, Comparator.comparing(Izvestaj::getCena).reversed());
			}
		}
		
		// prikaz
		ModelAndView rezultat = new ModelAndView("izvestaj/izvestajiAdmin");
		rezultat.addObject("rezultatiIzvestaja", rezultatiIzvestaja);
		rezultat.addObject("ukupnaCena",ukupnaCena);
		rezultat.addObject("ukupanBrojZakazanihTermina",ukupanBrojZakazanihTermina);
		
		return rezultat;
	}
}
