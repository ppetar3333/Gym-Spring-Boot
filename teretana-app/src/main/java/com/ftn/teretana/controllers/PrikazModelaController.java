package com.ftn.teretana.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.teretana.models.ClanskaKarta;
import com.ftn.teretana.models.Komentar;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.Korpa;
import com.ftn.teretana.models.ListaZelja;
import com.ftn.teretana.models.Sala;
import com.ftn.teretana.models.SpecijalniDatum;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.models.TipTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.ClanskaKartaService;
import com.ftn.teretana.service.KomentarService;
import com.ftn.teretana.service.KorisnikService;
import com.ftn.teretana.service.KorpaService;
import com.ftn.teretana.service.ListaZeljaService;
import com.ftn.teretana.service.SalaService;
import com.ftn.teretana.service.SpecijalniDatumService;
import com.ftn.teretana.service.TerminTreningaService;
import com.ftn.teretana.service.TipTreningaService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value="/")
public class PrikazModelaController {

	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TreningService treningService;
	
	@Autowired
	private KomentarService komentarService;
	
	@Autowired
	private KorpaService korpaService;
	
	@Autowired
	private ListaZeljaService listaZeljaService;
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private ClanskaKartaService clanskaKartaService;
	
	@Autowired
	private SpecijalniDatumService specDatumService;
	
	@Autowired
	private TerminTreningaService terminTreningaService;
	
	@Autowired
	private TipTreningaService tipTreningaService;
	
	@GetMapping(value="/korisniciLista")
	private String getKorisnikList(Model model) {
		List<Korisnik> korisnicilista = korisnikService.findAll();
		System.out.println(korisnicilista.toString());
		model.addAttribute("korisniciLista", korisnicilista);
		return "prikazSvihModela/listaSvihKorisnika";
	}
	
	@GetMapping
	private String getTreninziList(Model model) {
		List<Trening> treningLista = treningService.findAll();
		List<String> tipTreningaLista = new ArrayList<>();
		List<TipTreninga> tipLista = tipTreningaService.findAll();
		for(TipTreninga tip : tipLista) {
			tipTreningaLista.add(tip.getIme());
		}
		model.addAttribute("treninziLista", treningLista);
		model.addAttribute("tipTreningaLista", tipTreningaLista);
		return "index";
	}
	
	@GetMapping(value="/komentariLista")
	private String getKomentarList(Model model) {
		List<Komentar> komentarLista = komentarService.findAll();
		System.out.println(komentarLista.toString());
		model.addAttribute("komentariLista", komentarLista);
		return "prikazSvihModela/listaSvihKomentara";
	}
	
	@GetMapping(value="/korpaLista")
	private String getKorpaList(Model model) {
		List<Korpa> korpaLista = korpaService.findAll();
		System.out.println(korpaLista.toString());
		model.addAttribute("korpaLista", korpaLista);
		return "prikazSvihModela/listaPodatakaIzKorpe";
	}
	
	@GetMapping(value="/zeljeLista")
	private String getZeljeList(Model model) {
		List<ListaZelja> zeljeLista = listaZeljaService.findAll();
		System.out.println(zeljeLista.toString());
		model.addAttribute("zeljeLista", zeljeLista);
		return "prikazSvihModela/listaSvihZelja";
	}
	
	@GetMapping(value="/salaLista")
	private String getSalaList(Model model) {
		List<Sala> salaLista = salaService.findAll();
		System.out.println(salaLista.toString());
		model.addAttribute("salaLista", salaLista);
		return "prikazSvihModela/listaSvihSala";
	}
	
	@GetMapping(value="/clanskeKarteLista")
	private String getClanskaKartaList(Model model) {
		List<ClanskaKarta> clanskaKartaLista = clanskaKartaService.findAll();
		System.out.println(clanskaKartaLista.toString());
		model.addAttribute("clanskaKartaLista", clanskaKartaLista);
		return "prikazSvihModela/listaSvihClanskihKarti";
	}
	
	@GetMapping(value="/specDatumiLista")
	private String getSpecDatumList(Model model) {
		List<SpecijalniDatum> specDatumLista = specDatumService.findAll();
		System.out.println(specDatumLista.toString());
		model.addAttribute("specDatumLista", specDatumLista);
		return "prikazSvihModela/listaSvihSpecDatuma";
	}
	
	@GetMapping(value="/terminiLista")
	private String getTerminiList(Model model) {
		List<TerminTreninga> terminTreningaLista = terminTreningaService.findAll();
		System.out.println(terminTreningaLista.toString());
		model.addAttribute("terminTreningaLista", terminTreningaLista);
		return "prikazSvihModela/listaSvihTerminaTreninga";
	}
	
	@GetMapping(value="/tipoviTreningaLista")
	private String getTipTreningaList(Model model) {
		List<TipTreninga> tipTreningaList = tipTreningaService.findAll();
		System.out.println(tipTreningaList.toString());
		model.addAttribute("tipTreningaList", tipTreningaList);
		return "prikazSvihModela/listaSvihTipovaTreninga";
	}
}
