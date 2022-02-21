package com.ftn.teretana.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.teretana.models.SpecijalniDatum;
import com.ftn.teretana.models.TipTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.SpecijalniDatumService;
import com.ftn.teretana.service.TipTreningaService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value = "SpecijalniDatum")
public class SpecijalniDatumController {

	@Autowired
	private TreningService treningService;
	
	@Autowired
	private TipTreningaService tipTreningaService;
	
	@Autowired
	private SpecijalniDatumService specijalniDatumService;
	
	@GetMapping(value = "/prikazStranice")
	private String prikazStranice(Model model) {
		
		List<String> treninzi = new ArrayList<>();
		for(Trening trening : treningService.findAll()) {
			treninzi.add(trening.getNaziv());
		}
		
		model.addAttribute("treninzi", treninzi);
		return "specijalniDatum/prikazStranice";
	}
	
	@PostMapping(value = "dodajSpecDatum")
	private ModelAndView dodajSpecDatum(@RequestParam(required = false) String datum, @RequestParam(required = false) String popust, @RequestParam(required = false) String trening, Model model) {
		
		try {
			boolean ok = true;
			String message = "";
			String datumVreme = datum + " 00:00";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime datumLDT = LocalDateTime.parse(datumVreme, formatter);
			
			int popustBroj = Integer.parseInt(popust);
			
			List<SpecijalniDatum> specijalniDatumiLista = specijalniDatumService.findAll();
			boolean postojiSpecijalniDatum = false;
			
			for(SpecijalniDatum spec : specijalniDatumiLista) {
				if(spec.getTrening().getId() == 0) {
					if(spec.getDatum().format(formatterDate).equals(datumLDT.format(formatterDate))) {
						postojiSpecijalniDatum = true;
					}
				}
			}
			
			for(SpecijalniDatum spec : specijalniDatumiLista) {
				if(spec.getTrening().getId() != 0) {
					if(spec.getTrening().getNaziv().equals(trening) && spec.getDatum().format(formatterDate).equals(datumLDT.format(formatterDate))) {
						postojiSpecijalniDatum = true;
					}
				}
			}
			
			if(postojiSpecijalniDatum == true) {
				ok = false;
				message += "Postoji specijalni datum";
			}
			
			if(popustBroj < 5 || popustBroj > 90) {
				ok = false;
				message += "Popust mora biti od 5% do 90%";
			}
			
			if(ok == false) {
				throw new Exception(message);
			}
			
			List<Trening> treninzi = treningService.findAll();
			Trening selektovaniTrening = null;
			for(Trening treningg : treninzi) {
				if(treningg.getNaziv().equals(trening)) {
					selektovaniTrening = treningg;
				}
			}
		
			
			if(ok == true) {
				
				SpecijalniDatum specijalniDatum;
				
				if(trening.equals("Svi Treninzi")) {
					specijalniDatum = new SpecijalniDatum(specijalniDatumService.generateNewID(), datumLDT, popustBroj, null, true);					
				} else {
					specijalniDatum = new SpecijalniDatum(specijalniDatumService.generateNewID(), datumLDT, popustBroj, selektovaniTrening, true);					
				}
				
				specijalniDatumService.save(specijalniDatum);
				
				ModelAndView rezultat = new ModelAndView("index");
				
				List<Trening> treningLista = treningService.findAll();
				List<String> tipTreningaLista = new ArrayList<>();
				List<TipTreninga> tipLista = tipTreningaService.findAll();
				for(TipTreninga tip : tipLista) {
					tipTreningaLista.add(tip.getIme());
				}
				model.addAttribute("treninziLista", treningLista);
				model.addAttribute("tipTreningaLista", tipTreningaLista);
				
				return rezultat;
			} 
			
			return null;
			
		} catch (Exception ex) {
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesno dodavanje specijalnog datuma!";
			}
			List<String> treninzi = new ArrayList<>();
			for(Trening treninz : treningService.findAll()) {
				treninzi.add(treninz.getNaziv());
			}
			
			model.addAttribute("treninzi", treninzi);
			
			ModelAndView rezultat = new ModelAndView("specijalniDatum/prikazStranice");
			rezultat.addObject("poruka", poruka);

			return rezultat;
		}
	}
}
