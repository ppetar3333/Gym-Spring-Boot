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

import com.ftn.teretana.enums.StatusKomentara;
import com.ftn.teretana.models.Clan;
import com.ftn.teretana.models.Komentar;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.ListaZelja;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.KomentarService;
import com.ftn.teretana.service.ListaZeljaService;
import com.ftn.teretana.service.TerminTreningaService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value = "ListaZelja")
public class ListaZeljaController {

	@Autowired
	private ListaZeljaService listaZeljaService;
	
	@Autowired
	private TreningService treningService;
	
	@Autowired
	private TerminTreningaService terminTreningaService;
	
	@Autowired
	private KomentarService komentarService;
	
	@GetMapping(value = "/prikaz")
	private String prikaz(Model model, HttpSession session) {
		
		Korisnik korisnik = (Korisnik) session.getAttribute("clan");
		List<ListaZelja> listaZelja = new ArrayList<>();
		
		for(ListaZelja zelje : listaZeljaService.findAll()) {
			if(zelje.getClan().getId() == korisnik.getId()) {
				listaZelja.add(zelje);
			}
		}
		
		model.addAttribute("listaZelja", listaZelja);
		
		return "prikazSvihModela/listaZelja";
	}
	
	@GetMapping(value = "/ubaciUlistuZelja")
	private ModelAndView ubaciUlistuZelja(@RequestParam int id, Model model, HttpSession session) throws IOException {
		
		String poruka = "";
		
		Trening trening = treningService.findOneByID(id);
		
		Korisnik korisnik = (Korisnik) session.getAttribute("clan");

		List<ListaZelja> listaZelja = listaZeljaService.findAll();
		List<ListaZelja> postojiTrening = new ArrayList<>();
		
		for(ListaZelja zelje : listaZelja) {
			if(zelje.getTrening().getId() == trening.getId() && zelje.getClan().getId() == korisnik.getId()) {
				postojiTrening.add(zelje);
			}
		}
		
		if(!postojiTrening.isEmpty()) {
			poruka += "Trening se vec nalazi u vasoj listi zelja!";
		} else {
			poruka += "Uspesno ste dodali trening u listu zelja!";
			
			ListaZelja novaListaZelja = new ListaZelja(listaZeljaService.generateNewID(), (Clan) korisnik, trening, true);
			listaZeljaService.findAll().add(novaListaZelja);
			listaZeljaService.save(novaListaZelja);
		}
		
		pojedinacniTrening(model, session, trening);
		
		ModelAndView rezultat = new ModelAndView("pojedinacniModeli/pojedinacniTrening");
		rezultat.addObject("poruka", poruka);
		
		return rezultat;
	}

	@GetMapping(value = "/ukloniIzlisteZelja")
	private ModelAndView ukloniIzlisteZelja(@RequestParam int id, Model model, HttpSession session) {
		
		ListaZelja zelja = listaZeljaService.findOneByID(id);
		
		listaZeljaService.delete(zelja.getId());
		listaZeljaService.findAll().remove(zelja);
		
		String poruka = "Uspesno ste uklonili trening iz liste zelja!";
		
		Korisnik korisnik = (Korisnik) session.getAttribute("clan");
		List<ListaZelja> listaZelja = new ArrayList<>();
		
		for(ListaZelja zelje : listaZeljaService.findAll()) {
			if(zelje.getClan().getId() == korisnik.getId()) {
				listaZelja.add(zelje);
			}
		}
		
		ModelAndView rezultat = new ModelAndView("prikazSvihModela/listaZelja");
		rezultat.addObject("poruka", poruka);
		rezultat.addObject("listaZelja", listaZelja);
		
		return rezultat;
	}
	
	private void pojedinacniTrening(Model model, HttpSession session, Trening trening) {
		List<TerminTreninga> terminiTreninga = terminTreningaService.findAll();
		
		List<TerminTreninga> listaTreningaZaDatiTrening = new ArrayList<>();
		
		List<TerminTreninga> listaTreningaKorisnika = new ArrayList<>();
		
		Korisnik korisnik = (Korisnik) session.getAttribute("clan");
		
		for(TerminTreninga termin : terminiTreninga) {
			if(termin.getClan().getId() == korisnik.getId()) {
				listaTreningaKorisnika.add(termin);
			}
		}
		
		for(TerminTreninga termin : terminiTreninga) {
			if(trening.getId() == termin.getTrening().getId()) {
				listaTreningaZaDatiTrening.add(termin);
			}
		}
		
		List<Komentar> komentari = komentarService.findAll();
		
		List<Komentar> komentariZaDatiTrening = new ArrayList<>();
		
		for(Komentar kom : komentari) {
			if(trening.getId() == kom.getTrening().getId() && kom.getStatusKomentara() == StatusKomentara.odobren) {
				komentariZaDatiTrening.add(kom);
			}
		}
		
		model.addAttribute("trening", trening);
		model.addAttribute("terminiSale",listaTreningaZaDatiTrening);
		model.addAttribute("komentari",komentariZaDatiTrening);
		model.addAttribute("listaTreningaKorisnika", listaTreningaKorisnika);
	}
	
}
