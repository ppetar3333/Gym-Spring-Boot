package com.ftn.teretana.controllers;

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

import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.Sala;
import com.ftn.teretana.models.TipTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.KorisnikService;
import com.ftn.teretana.service.SalaService;
import com.ftn.teretana.service.TipTreningaService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value = "/pretragaModela")
public class PretragaController {

	@Autowired
	private TreningService treningService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired 
	private SalaService salaService;

	@Autowired
	private TipTreningaService tipTreningaService;
	
	@GetMapping(value = "/pretragaTreninga")
	public ModelAndView pretragaTreninga(@RequestParam(required=false) String naziv, @RequestParam(required=false) String tipTreninga, 
			@RequestParam(required=false) String cenaOd,
			@RequestParam(required=false) String cenaDo, @RequestParam(required=false) String trener,
			@RequestParam(required=false) String vrsta, @RequestParam(required=false) String nivo,
			@RequestParam(required=false) String sortiraj, @RequestParam(required=false) String ascDesc, Model model) {
		
		System.out.println(naziv + "," + tipTreninga + "," + cenaOd + "," + cenaDo + "," + vrsta + "," + trener + "," + nivo + "," + sortiraj + "," + ascDesc);
		
		List<Trening> nadjeniTreninzi = treningService.rezultatiPretrage(naziv,tipTreninga,cenaOd,cenaDo,trener,vrsta,nivo,sortiraj);
		
		if(sortiraj.equals("nazivu")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getNaziv));
			} else {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getNaziv).reversed());
			}
		} else if(sortiraj.equals("tipu")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getTipTreninga, Comparator.comparing(TipTreninga::getIme)));
			} else {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getTipTreninga, Comparator.comparing(TipTreninga::getIme)).reversed());
			}
		} else if(sortiraj.equals("ceni")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getCena));
			} else {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getCena).reversed());
			}
		} else if(sortiraj.equals("trenerima")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getTrener));
			} else {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getTrener).reversed());
			}
		} else if(sortiraj.equals("vrstiTreninga")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getVrstaTreninga));
			} else {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getVrstaTreninga).reversed());
			}
		} else if(sortiraj.equals("nivouTreninga")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getNivoTreninga));
			} else {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getNivoTreninga).reversed());
			}
		} else if(sortiraj.equals("prosecnojOceni")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getProsecnaOcena));
			} else {
				Collections.sort(nadjeniTreninzi, Comparator.comparing(Trening::getProsecnaOcena).reversed());
			}
		}
		
		
        ModelAndView rezultat = new ModelAndView("index");
        rezultat.addObject("treninziLista",nadjeniTreninzi);
        
		List<String> tipTreningaLista = new ArrayList<>();
		List<TipTreninga> tipLista = tipTreningaService.findAll();
		for(TipTreninga tip : tipLista) {
			tipTreningaLista.add(tip.getIme());
		}
        model.addAttribute("tipTreningaLista",tipTreningaLista);
        
		return rezultat;
	}
	
	@GetMapping(value = "/pretragaKorisnika")
	private ModelAndView pretragaKorisnika(@RequestParam(required=false) String korisnickoIme,
			@RequestParam(required=false) String tip, @RequestParam(required=false) String sortiraj, 
			@RequestParam(required=false) String ascDesc) {
		
		List<Korisnik> nadjeniKorisnici = korisnikService.rezultatiPretrage(korisnickoIme,tip);
		
		if(sortiraj.equals("korisnickomImenu")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(nadjeniKorisnici, Comparator.comparing(Korisnik::getKorisnickoIme));
			} else {
				Collections.sort(nadjeniKorisnici, Comparator.comparing(Korisnik::getKorisnickoIme).reversed());
			}
		} else if(sortiraj.equals("tipu")) {
			if(ascDesc.equals("rastuce")) {
				Collections.sort(nadjeniKorisnici, Comparator.comparing(Korisnik::getTipKorisnika));
			} else {
				Collections.sort(nadjeniKorisnici, Comparator.comparing(Korisnik::getTipKorisnika).reversed());
			}
		}

        ModelAndView rezultat = new ModelAndView("prikazSvihModela/listaSvihKorisnika");
        rezultat.addObject("korisniciLista",nadjeniKorisnici);
        
		return rezultat;
	}
	
	@GetMapping(value = "/pretragaSala")
	private ModelAndView pretragaSala(@RequestParam(required=false) String oznakaSale, @RequestParam(required=false) String ascDesc) {
		
		List<Sala> nadjeneSale = salaService.rezultatiPretrage(oznakaSale);
		
		if(ascDesc.equals("rastuce")) {
			Collections.sort(nadjeneSale, Comparator.comparing(Sala::getOznakaSale));
		} else {
			Collections.sort(nadjeneSale, Comparator.comparing(Sala::getOznakaSale).reversed());
		}

        ModelAndView rezultat = new ModelAndView("prikazSvihModela/listaSvihSala");
        rezultat.addObject("salaLista",nadjeneSale);
        
		return rezultat;
	}
}
