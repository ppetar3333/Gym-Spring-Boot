package com.ftn.teretana.controllers;

import java.io.IOException;
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

import com.ftn.teretana.models.Clan;
import com.ftn.teretana.models.ClanskaKarta;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.service.ClanskaKartaService;

@Controller
@RequestMapping(value = "/ClanskaKartica")
public class ClanskaKarticaController {

	
	@Autowired
	private ClanskaKartaService clanksaKarticaService;
	
	@GetMapping(value = "/prikazStranice")
	public String clanskaKarticaStranica(Model model, HttpSession session) {
		
		List<ClanskaKarta> karticeNaCekanju = new ArrayList<>();
		
		for(ClanskaKarta clanskaKarta : clanksaKarticaService.findAll()) {
			if(!clanskaKarta.isPrihvacen()) {
				karticeNaCekanju.add(clanskaKarta);				
			}
		}
		
		List<ClanskaKarta> imaKarticu = new ArrayList<>();
		
		Korisnik korisnik = (Korisnik) session.getAttribute("clan");
		
		if(korisnik != null) {
			for(ClanskaKarta clanskaKarta : clanksaKarticaService.findAll()) {
				if(clanskaKarta.getClan().getId() == korisnik.getId()) {
					imaKarticu.add(clanskaKarta);
				}
			}
		}
		
		model.addAttribute("karticeNaCekanju", karticeNaCekanju);
		model.addAttribute("imaKarticu", imaKarticu);
		
		return "pojedinacniModeli/clanskaKartica";
	}
	
	@GetMapping(value = "/prihvati")
	private ModelAndView prihvati(@RequestParam int id, Model model) throws IOException {
		
		ClanskaKarta clanskaKartica = clanksaKarticaService.findOneByID(id);
		
		clanskaKartica.setPrihvacen(true);
		clanskaKartica.setBrojPoena(10);
		clanskaKartica.setPopust(50);
		
		clanksaKarticaService.update(clanskaKartica);
		
		List<ClanskaKarta> karticeNaCekanju = new ArrayList<>();
		
		for(ClanskaKarta clanskaKarta : clanksaKarticaService.findAll()) {
			if(!clanskaKarta.isPrihvacen()) {
				karticeNaCekanju.add(clanskaKarta);				
			}
		}
		
		ModelAndView rezultat = new ModelAndView("pojedinacniModeli/clanskaKartica");
		
		rezultat.addObject("karticeNaCekanju",karticeNaCekanju);
		rezultat.addObject("poruka","Uspesno ste prihvatili zahtev!");
		
		return rezultat;
	}
	
	@GetMapping(value = "/odbij")
	private ModelAndView odbij(@RequestParam int id, Model model) throws IOException {
		
		ClanskaKarta clanskaKartica = clanksaKarticaService.findOneByID(id);
		
		clanksaKarticaService.deleteFromDataBase(clanskaKartica.getId());
		
		List<ClanskaKarta> karticeNaCekanju = new ArrayList<>();
		
		for(ClanskaKarta clanskaKarta : clanksaKarticaService.findAll()) {
			if(!clanskaKarta.isPrihvacen()) {
				karticeNaCekanju.add(clanskaKarta);				
			}
		}
		
		ModelAndView rezultat = new ModelAndView("pojedinacniModeli/clanskaKartica");
		
		rezultat.addObject("karticeNaCekanju",karticeNaCekanju);
		rezultat.addObject("poruka","Uspesno ste odbili zahtev!");
		
		return rezultat;
	}
	
	@GetMapping(value = "/zatraziKreiranjeKartice")
	private ModelAndView zatraziKreiranjeKartice(HttpSession session) {
		
		Korisnik korisnik = (Korisnik) session.getAttribute("clan");
		
		ClanskaKarta clanskaKartica = new ClanskaKarta(clanksaKarticaService.generateNewID(), 0, 0, (Clan) korisnik, false, false, true);
		
		clanksaKarticaService.save(clanskaKartica);
		
		List<ClanskaKarta> imaKarticu = new ArrayList<>();
		
		for(ClanskaKarta clanskaKarta : clanksaKarticaService.findAll()) {
			if(clanskaKarta.getClan().getId() == korisnik.getId()) {
				imaKarticu.add(clanskaKarta);
			}
		}
		
		ModelAndView rezultat = new ModelAndView("pojedinacniModeli/clanskaKartica");
		rezultat.addObject("poruka","Uspesno ste zatrazili zahtev za kreiranje!");
		rezultat.addObject("imaKarticu", imaKarticu);
		
		return rezultat;
	}
}
