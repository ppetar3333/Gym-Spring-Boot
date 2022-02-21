package com.ftn.teretana.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.teretana.enums.TipKorisnika;
import com.ftn.teretana.models.Clan;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.service.KorisnikService;

@Controller
@RequestMapping(value = "/Registracija")
public class RegistracijaController {

	
	@Autowired
	private KorisnikService korisnikService;
	
	@GetMapping(value = "/registracija")
	public String registracijaStranica() {
		return "registracija";
	}
	
	@PostMapping(value = "/RegistracijaSave")
	public ModelAndView registracijaSave(@ModelAttribute Clan korisnik, Model model) {
		
		try {
			Clan clan = new Clan(korisnikService.generateNewID(),korisnik.getKorisnickoIme(),korisnik.getLozinka(),korisnik.getEmail(),korisnik.getIme(),
					korisnik.getPrezime(),korisnik.getDatumRodjenja(),korisnik.getAdresa(),korisnik.getBrojTelefona(),LocalDateTime.now(),TipKorisnika.clan,false,true);
			
			boolean ok = true;
			String message = "";
			
			Korisnik nadjeniKorisnik = korisnikService.findOneByUsername(korisnik.getKorisnickoIme());
			
			if(!clan.getIme().matches("[a-zA-Z]+\\.?")) {
				ok = false;
				message += "Ime ne moze sadrzati brojeve! ";
			}
			
			if(!clan.getPrezime().matches("[a-zA-Z]+\\.?")) {
				ok = false;
				message += "Prezime ne moze sadrzati brojeve! ";
			}
			
			if(nadjeniKorisnik != null) {
				ok = false;
				message += "Korisnicko ime vec postoji! ";
			} 
			
			if(clan.getLozinka().length() < 5) {
				ok = false;
				message += "Sifra mora imati vise od 5 karaktera! ";
			}
			
			if(!clan.getEmail().contains("@gmail.com")) {
				ok = false;
				message += "Email mora sadrzati @gmail.com! ";
			}
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			String trenutniDatumString = LocalDate.now().toString();
			LocalDate trenutniDatum = LocalDate.parse(trenutniDatumString,formatter);
			
			String izabraniDatumString = clan.getDatumRodjenja().toString();
			LocalDate izabraniDatum = LocalDate.parse(izabraniDatumString, formatter);
			
			boolean mozeSeRegistrovati80god = izabraniDatum.isBefore(trenutniDatum.minusYears(100));
			
			boolean mozeSeRegistrovati18god = izabraniDatum.isBefore(trenutniDatum.minusYears(18));
			
			if(mozeSeRegistrovati80god == true) {
				ok = false;
				message += "Izabrali ste preko datum rodjenja veci od 100 godina, greska!";
			}
			
			if(mozeSeRegistrovati18god == false) {
				ok = false;
				message += "Morate biti stariji od 18 godina!";
			}
			
			if(clan.getBrojTelefona() < 9) {
				ok = false;
				message += "Broj telefona mora biti veci od 9! ";
			}
			
			if (ok == false) {
				throw new Exception(message);
			}
			
			if(ok == true) {
				korisnikService.findAll().add(clan);
				korisnikService.save(clan);
				ModelAndView login = new ModelAndView("login");
				
				return login;
			}
			return null;
		} catch (Exception ex) {
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesna registracija!";
			}
			ModelAndView rezultat = new ModelAndView("registracija");
			rezultat.addObject("poruka", poruka);

			return rezultat;
		}
	}
}
