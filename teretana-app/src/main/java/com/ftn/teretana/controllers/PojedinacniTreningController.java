package com.ftn.teretana.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.KomentarService;
import com.ftn.teretana.service.TerminTreningaService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value = "/PojedinacniTrening")
public class PojedinacniTreningController {

	@Autowired
	private TreningService treningService;
	
	@Autowired
	private TerminTreningaService terminTreningaService;
	
	@Autowired
	private KomentarService komentarService;
	
	@GetMapping(value = "/jedanTrening")
	private ModelAndView jedanTrening(@RequestParam int id, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException  {
		
		Trening trening = treningService.findOneByID(id);
		
		List<TerminTreninga> terminiTreninga = terminTreningaService.findAll();
		
		List<TerminTreninga> listaTreningaZaDatiTrening = new ArrayList<>();
		
		List<TerminTreninga> listaTreningaKorisnika = new ArrayList<>();
		
		Korisnik korisnik = (Korisnik) session.getAttribute("clan");
		
		
		for(TerminTreninga termin : terminiTreninga) {
			if(trening.getId() == termin.getTrening().getId()) {
				listaTreningaZaDatiTrening.add(termin);
			}
		}
		
		if(korisnik != null) {
			System.out.println("Korisnik je ulogovan kao clan: " + korisnik.getIme());
			for(TerminTreninga termin : listaTreningaZaDatiTrening) {
				if(termin.getClan().getId() == korisnik.getId()) {
					listaTreningaKorisnika.add(termin);
					System.out.println(termin);
				}
			}
		} else {
			System.out.println("Korisnik nije ulogovan ili je ulogovan kao admin.");
		}
		
		List<Komentar> komentari = komentarService.findAll();
		
		List<Komentar> komentariZaDatiTrening = new ArrayList<>();
		
		for(Komentar kom : komentari) {
			if(trening.getId() == kom.getTrening().getId() && kom.getStatusKomentara() == StatusKomentara.odobren) {
				komentariZaDatiTrening.add(kom);
			}
		}
		
		ModelAndView rezultat = new ModelAndView("/pojedinacniModeli/pojedinacniTrening");
		rezultat.addObject("/pojedinacniModeli/pojedinacniTrening", trening);
		
		model.addAttribute("trening", trening);
		model.addAttribute("terminiSale",listaTreningaZaDatiTrening);
		model.addAttribute("komentari",komentariZaDatiTrening);
		model.addAttribute("listaTreningaKorisnika", listaTreningaKorisnika);
		
		return rezultat;
	}
}
