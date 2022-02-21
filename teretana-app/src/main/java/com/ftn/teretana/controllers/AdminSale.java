package com.ftn.teretana.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.teretana.enums.TipKorisnika;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.Sala;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.service.SalaService;
import com.ftn.teretana.service.TerminTreningaService;

@Controller
@RequestMapping(value = "AdminSale")
public class AdminSale {

	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private TerminTreningaService terminTreningaService;
	
	@GetMapping(value = "/adminSale")
	private String adminSale(Model model) {
		List<Sala> salaLista = salaService.findAll();
		model.addAttribute("salaLista", salaLista);
		return "prikazSvihModela/listaSvihSala";
	}
	
	@GetMapping(value = "/obrisi")
	private ModelAndView obrisiSalu(@RequestParam int id,Model model) {
		
		String poruka = "";		
		LocalDateTime trenutniDatum = LocalDateTime.now();

		Sala sala = salaService.findOneByID(id);
		
		List<TerminTreninga> listaSvihTermina = terminTreningaService.findAll();
		List<TerminTreninga> postojeTreninzi = new ArrayList<>();
		
		for(TerminTreninga termin : listaSvihTermina) {
			if(termin.getSala().getId() == sala.getId() && trenutniDatum.isBefore(termin.getDatumOdrzavanja()) ) {
				postojeTreninzi.add(termin);
			}
		}
		
		if(postojeTreninzi.isEmpty()) {
			
			sala.setAktivan(false);
			salaService.findAll().remove(sala);
			salaService.update(sala);
			
		} else {
			poruka += "Postoje zakazani treninzi u sali, ne mozete je obrisati";
		}
		
		List<Sala> salaLista = salaService.findAll();
		
		ModelAndView rezultat = new ModelAndView("prikazSvihModela/listaSvihSala");
		rezultat.addObject("poruka", poruka);
		rezultat.addObject("salaLista",salaLista);	

		return rezultat;
	}
	
	@GetMapping(value = "/dodajSalu")
	private String dodajSalu(Model model) {
		return "dodavanjeModela/dodavanjeSale";
	}
	
	@PostMapping(value = "/dodavanjeSale")
	private ModelAndView dodavanjeSale(@ModelAttribute Sala sala, Model model) throws IOException{
		
		List<Sala> listaSala = salaService.findAll();
		
		try {
			Sala novaSala = new Sala(salaService.generateNewID(),sala.getOznakaSale(),sala.getKapacitet(),false,true);

			boolean ok = true;
			String message = "";

			for(Sala sale : listaSala) {
				if(novaSala.getOznakaSale().equalsIgnoreCase(sale.getOznakaSale())) {
					ok = false;
					message += "Sala sa unetom oznakom vec postoji! ";
				} 
			}
			
			if(sala.getKapacitet() <= 0) {
				ok = false;
				message += "Kapacitet mora biti veci od 0! ";
			}
			
			if(ok == false) {
				throw new Exception(message);
			}
			
			if(ok == true) {
				salaService.findAll().add(novaSala);
				salaService.save(novaSala);
				ModelAndView sale = new ModelAndView("prikazSvihModela/listaSvihSala");
				model.addAttribute("salaLista",salaService.findAll());
				return sale;
			}
			return null;
		} catch(Exception ex) {
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesno dodavanje sale!";
			}
			ModelAndView rezultat = new ModelAndView("dodavanjeModela/dodavanjeSale");
			rezultat.addObject("poruka", poruka);
			return rezultat;
		}
		
	}
	
	@GetMapping(value = "/jednaSala")
	private ModelAndView jednaSala(@RequestParam int id, Model model) {
		Sala sala = salaService.findOneByID(id);
		
		ModelAndView rezultat = new ModelAndView("/pojedinacniModeli/pojedinacnaSala");
		rezultat.addObject("/pojedinacniModeli/pojedinacnaSala", sala);
		
		model.addAttribute("sala", sala);
		
		return rezultat;
	}
	
	@PostMapping(value = "/izmeni")
	private ModelAndView izmeniSalu(@RequestParam int id, Model model, @RequestParam String kapacitet) {
		
		Sala nadjenaSala = salaService.findOneByID(id);
		
		nadjenaSala.setKapacitet(Integer.parseInt(kapacitet));
		
		salaService.update(nadjenaSala);
		
		ModelAndView rezultat = new ModelAndView("prikazSvihModela/listaSvihSala");
		List<Sala> salaLista = salaService.findAll();
		rezultat.addObject("salaLista",salaLista);
		
		return rezultat;
	}
}
