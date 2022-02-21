package com.ftn.teretana.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.ftn.teretana.models.Admin;
import com.ftn.teretana.models.Clan;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.service.KorisnikService;
import com.ftn.teretana.service.TerminTreningaService;

@Controller
@RequestMapping(value = "PrikazSvojiPodataka")
public class PrikazSvojihPodatakaController {

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TerminTreningaService terminTreningaService;
	
	@GetMapping(value = "/korisnik")
	private ModelAndView prikazStranice(HttpSession session,Model model) {
		
		Korisnik korisnik;
		
		korisnik = (Clan) session.getAttribute(LogInController.CLAN_KEY);
		
		if(korisnik == null) {
			korisnik = (Admin) session.getAttribute(LogInController.ADMIN_KEY);
		}
		
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
		
		
		ModelAndView rezultat = new ModelAndView("/pojedinacniModeli/svojiPodaci");
		rezultat.addObject("/pojedinacniModeli/svojiPodaci", korisnik);
		
		model.addAttribute("imaTermina", imaTermina);
		model.addAttribute("ukupnaCena", ukupnaCena);
		
		model.addAttribute("korisnik", korisnik);
		
		return rezultat;
	}
	
	@PostMapping(value = "/izmeni")
	private ModelAndView pregledPodataka(@RequestParam int id,
			@RequestParam(required=false) String ime,
			@RequestParam(required=false) String prezime,
			@RequestParam(required=false) String korisnickoIme,
			@RequestParam(required=false) String email,
			@RequestParam(required=false) String adresa,
			@RequestParam(required=false) String brojTelefona, Model model) {
		
		Korisnik nadjeniKorisnik = korisnikService.findOneById(id);
		
		try {
			boolean ok = true;
			nadjeniKorisnik.setIme(ime);
			nadjeniKorisnik.setPrezime(prezime);
			nadjeniKorisnik.setKorisnickoIme(korisnickoIme);
			nadjeniKorisnik.setEmail(email);
			nadjeniKorisnik.setAdresa(adresa);
			nadjeniKorisnik.setBrojTelefona(Long.parseLong(brojTelefona));
			
			String message = "";
			
			if(!ime.matches("[a-zA-Z]+\\.?")) {
				ok = false;
				message += "Ime ne moze sadrzati brojeve! ";
			}
			
			if(!prezime.matches("[a-zA-Z]+\\.?")) {
				ok = false;
				message += "Prezime ne moze sadrzati brojeve! ";
			}
			
			if(!email.contains("@gmail.com")) {
				ok = false;
				message += "Email mora sadrzati @gmail.com ";
			}
			
			if(brojTelefona.length() < 9) {
				ok = false;
				message += "Broj telefona mora biti veci od 9! ";
			}
			
			if(ok == false) {
				throw new Exception(message);
			}
			
			if(ok == true) {
				korisnikService.update(nadjeniKorisnik);
				ModelAndView rezultat = new ModelAndView("/pojedinacniModeli/svojiPodaci");
				rezultat.addObject("/pojedinacniModeli/svojiPodaci", nadjeniKorisnik);
				rezultat.addObject("poruka","uspesna izmena!");
				model.addAttribute("korisnik",nadjeniKorisnik);
				
				return rezultat;	
			}
			return null;
		} catch(Exception ex) {
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesna izmena podataka!";
			}
			ModelAndView rezultat = new ModelAndView("/pojedinacniModeli/svojiPodaci");
			rezultat.addObject("/pojedinacniModeli/svojiPodaci", nadjeniKorisnik);
			rezultat.addObject("poruka",poruka);
			model.addAttribute("korisnik",nadjeniKorisnik);
			
			return rezultat;
		}

	}
	
	@GetMapping(value = "/izmeniLozinku")
	private ModelAndView izmeniLozinkuPrikaz(@RequestParam int id, Model model) {
		
		Korisnik korisnik = korisnikService.findOneById(id);
		
		ModelAndView rezultat = new ModelAndView("izmenaModela/izmeniLozinku");
		rezultat.addObject("izmenaModela/izmeniLozinku", korisnik);
		
		model.addAttribute("korisnik",korisnik);
		
		return rezultat;
	}
	
	@PostMapping(value = "/izmeniLozinkuSave")
	private ModelAndView izmenaLozinkeSave(@RequestParam int id, @RequestParam String lozinka, 
			@RequestParam String lozinkaPonovljena,Model model) throws IOException{
		
		Korisnik korisnik = korisnikService.findOneById(id);
		
		try {
			
			boolean ok = true;
			
			if(!lozinka.equals(lozinkaPonovljena)) {
				ok = false;
				throw new Exception("Loznike moraju da se poklapaju");
			}
			
			if(ok == true) {
				
				korisnik.setLozinka(lozinka);
				korisnikService.update(korisnik);
				
				ModelAndView rezultat = new ModelAndView("/pojedinacniModeli/svojiPodaci");
				rezultat.addObject("/pojedinacniModeli/svojiPodaci", korisnik);
				
				model.addAttribute("korisnik",korisnik);
				
				return rezultat;
			}
			return null;
			
		} catch (Exception ex) {
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesna promena lozinke!";
			}
			ModelAndView rezultat = new ModelAndView("izmenaModela/izmeniLozinku");
			rezultat.addObject("izmenaModela/izmeniLozinku", korisnik);
			rezultat.addObject("poruka",poruka);
			
			model.addAttribute("korisnik",korisnik);
			
			return rezultat;
		}
	}
}
