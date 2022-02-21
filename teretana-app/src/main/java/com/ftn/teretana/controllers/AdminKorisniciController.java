package com.ftn.teretana.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.teretana.enums.StatusKomentara;
import com.ftn.teretana.enums.TipKorisnika;
import com.ftn.teretana.models.ClanskaKarta;
import com.ftn.teretana.models.Komentar;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.ListaZelja;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.ClanskaKartaService;
import com.ftn.teretana.service.KomentarService;
import com.ftn.teretana.service.KorisnikService;
import com.ftn.teretana.service.ListaZeljaService;
import com.ftn.teretana.service.TerminTreningaService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value = "/AdminKorisnici")
public class AdminKorisniciController {

	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TerminTreningaService terminTreningaService;
	
	@Autowired
	private TreningService treningService;
	
	@Autowired
	private ClanskaKartaService clanskaKartaService;
	
	@Autowired
	private ListaZeljaService listaZeljaService;
	
	@Autowired
	private KomentarService komentarService;
	
	private Korisnik korisnik;
	
	@GetMapping(value = "/pregledSvihKorisnika")
	private String pregledKorisnika(Model model) {
		List<Korisnik> korisnicilista = korisnikService.findAll();
		model.addAttribute("korisniciLista", korisnicilista);
		return "prikazSvihModela/listaSvihKorisnika";
	}
	
	@GetMapping(value = "/blokiraj")
	private ModelAndView blokiraj(@RequestParam int id, Model model) throws IOException {
		List<Korisnik> korisniciLista = korisnikService.findAll();
		Korisnik korisnik = korisnikService.findOneById(id);
		try {
			if (korisnik.getTipKorisnika() == TipKorisnika.admin) {
				throw new Exception("Ne mozete blokirati admina!");
			} else if(korisnik.getTipKorisnika() == TipKorisnika.clan && korisnik.isBlokiran()) {
				throw new Exception("Korisnik je vec blokiran!");
			} else {
				throw new Exception("Uspesno blokiranje");
			}			
		} catch (Exception ex) {
			String poruka = ex.getMessage();
			ModelAndView rezultat = new ModelAndView("prikazSvihModela/listaSvihKorisnika");
			rezultat.addObject("korisniciLista",korisniciLista);
			rezultat.addObject("poruka", poruka);
			
			if(poruka.equals("Uspesno blokiranje")) {
				korisnik.setBlokiran(true);
				korisnikService.update(korisnik);
			}
			
			return rezultat;		
		}
	}
	
	@GetMapping(value = "/odblokiraj")
	private ModelAndView odblokiraj(@RequestParam int id, Model model) throws IOException {
		List<Korisnik> korisniciLista = korisnikService.findAll();
		Korisnik korisnik = korisnikService.findOneById(id);
		try {
			if (korisnik.getTipKorisnika() == TipKorisnika.admin) {
				throw new Exception("Ne mozete odblokirati admina!");
			} else if(korisnik.getTipKorisnika() == TipKorisnika.clan && !korisnik.isBlokiran()) {
				throw new Exception("Korisnik nije blokiran!");
			} else {
				throw new Exception("Uspesno odblokiranje");
			}
		} catch (Exception ex) {
			String poruka = ex.getMessage();
			ModelAndView rezultat = new ModelAndView("prikazSvihModela/listaSvihKorisnika");
			rezultat.addObject("poruka", poruka);
			rezultat.addObject("korisniciLista",korisniciLista);
			
			if(poruka.equals("Uspesno odblokiranje")) {
				korisnik.setBlokiran(false);
				korisnikService.update(korisnik);
			}
			
			return rezultat;			
		}		
	}
	
	@GetMapping(value = "/jedanKorisnik")
	private ModelAndView jedanKorisnik(@RequestParam int id, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException  {
		
		korisnik = korisnikService.findOneById(id);
		
		ModelAndView rezultat = new ModelAndView("/pojedinacniModeli/pojedinacniKorisnik");
		rezultat.addObject("/pojedinacniModeli/pojedinacniKorisnik", korisnik);
		
		List<TerminTreninga> imaTermina = new ArrayList<>();
		double ukupnaCena = 0;
		
		for(TerminTreninga termin : terminTreningaService.findAll()) {
			if(termin.getClan().getId() == korisnik.getId()) {
				imaTermina.add(termin);
				ukupnaCena += termin.getTrening().getCena();
			}
		}
		
		if(!imaTermina.isEmpty()) {
			Collections.sort(imaTermina, Comparator.comparing(TerminTreninga::getDatumOdrzavanja).reversed());
		}
		
		model.addAttribute("imaTermina", imaTermina);
		model.addAttribute("ukupnaCena", ukupnaCena);
		model.addAttribute("korisnik", korisnik);
		
		return rezultat;
	}
	
	@PostMapping(value = "/izmeni")
	private ModelAndView izmeniKorisnika(@RequestParam int id, @RequestParam(required=false) String tip) {

		Korisnik korisnik = korisnikService.findOneById(id);
		
		korisnik.setTipKorisnika(TipKorisnika.valueOf(tip));
		
//		ako ga ima u terminima, staviti clana na null
		for(TerminTreninga termin : terminTreningaService.findAll()) {
			if(termin.getClan().getId() == korisnik.getId()) {
				termin.setClan(null);
				termin.setPopunjen(false);
				terminTreningaService.update(termin);
			}
		}
		
//		ako ima clansku karticu staviti clansku karticu na aktivan false
		for(ClanskaKarta karta : clanskaKartaService.findAll()) {
			if(karta.getClan().getId() == korisnik.getId()) {
				karta.setAktivan(false);
				clanskaKartaService.update(karta);
			}
		}
		
//		ako ima nesto u listi zelja staviti sve na aktivan false
		for(ListaZelja listaZelja : listaZeljaService.findAll()) {
			if(listaZelja.getClan().getId() == korisnik.getId()) {
				listaZelja.setAktivan(false);
				listaZeljaService.update(listaZelja);
			}
		}
		
//		ako ima neki komentar staviti clan na null i anoniman true
		for(Komentar komentar : komentarService.findAll()) {
			if(komentar.getClan().getId() == korisnik.getId()) {
				komentar.setClan(null);
				komentar.setAnoniman(true);
				komentarService.update(komentar);
			}
		}
		
		korisnikService.update(korisnik);

		ModelAndView rezultat = new ModelAndView("prikazSvihModela/listaSvihKorisnika");
		List<Korisnik> korisniciLista = korisnikService.findAll();
		rezultat.addObject("korisniciLista",korisniciLista);
		
		return rezultat;
	}
	
	@GetMapping(value = "/pregledTermina")
	private ModelAndView pregledTermina(@RequestParam int id, Model model) {
		
		TerminTreninga terminTreninga = terminTreningaService.findOneByID(id);
		
		ModelAndView rezultat = new ModelAndView("pojedinacniModeli/PojedinacniTermin");
		rezultat.addObject("termini", terminTreninga);
		
		return rezultat;
	}
}
