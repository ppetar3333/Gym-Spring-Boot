package com.ftn.teretana.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.teretana.enums.StatusKomentara;
import com.ftn.teretana.models.Komentar;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.KomentarService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value = "Admin")
public class AdminKomentariController {

	@Autowired
	private KomentarService komentarService;
	
	@Autowired
	private TreningService treningService;
	
	private List<Komentar> komentariNaCekanju;
	
	@GetMapping(value = "/Komentari")
	private String komentari() {
		return "pojedinacniModeli/komentari";
	}
	
	@GetMapping(value = "/komentariLista")
	@ResponseBody
	private List<Komentar> komentariLista() {
		
		List<Komentar> sviKomentari = komentarService.findAll();
		komentariNaCekanju = new ArrayList<Komentar>();
		for(Komentar kom : sviKomentari) {
			if(kom.getStatusKomentara() == StatusKomentara.naCekanju) {
				komentariNaCekanju.add(kom);
				System.out.println(kom.getClan().toString());
			}
		}
		
		return komentariNaCekanju;
	}
	
	@PostMapping(value="/komentariLista/odbijKomentar")
	@ResponseBody
	public ModelAndView odbij(@RequestParam int idKomentara) {
		Komentar komentar = komentarService.findOneByID(idKomentara);
		komentar.setStatusKomentara(StatusKomentara.nijeOdobren);
		komentar.setAktivan(false);
		komentarService.update(komentar);
		for(Komentar komentarLista : komentariNaCekanju) {
			if(komentar.getId() == komentarLista.getId()) {
				komentariNaCekanju.remove(komentar);
				break;
			}
		}
		ModelAndView rezultat = new ModelAndView("pojedinacniModeli/komentari");
		return rezultat;
	}
	
	@PostMapping(value="/komentariLista/odobriKomentar")
	@ResponseBody
	public ModelAndView odobri(@RequestParam int idKomentara) {
		Komentar komentar = komentarService.findOneByID(idKomentara);
		komentar.setStatusKomentara(StatusKomentara.odobren);
		komentarService.update(komentar);
		Trening trening = treningService.findOneByID(komentar.getTrening().getId());
		double ocena = komentar.getOcena();
		double novaOcena = (trening.getProsecnaOcena() + ocena) / 2;
		trening.setProsecnaOcena(novaOcena);
		treningService.update(trening);
		for(Komentar komentarLista : komentariNaCekanju) {
			if(komentar.getId() == komentarLista.getId()) {
				komentariNaCekanju.remove(komentar);
				break;
			}
		}
		ModelAndView rezultat = new ModelAndView("pojedinacniModeli/komentari");
		return rezultat;
	}
}
