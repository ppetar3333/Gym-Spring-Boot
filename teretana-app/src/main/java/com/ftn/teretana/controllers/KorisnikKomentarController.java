package com.ftn.teretana.controllers;


import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftn.teretana.enums.StatusKomentara;
import com.ftn.teretana.models.Clan;
import com.ftn.teretana.models.Komentar;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.KomentarService;
import com.ftn.teretana.service.TipTreningaService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value = "KorisnikKomentar")
public class KorisnikKomentarController {

	@Autowired
	private KomentarService komentarService;
	
	@Autowired
	private TreningService treningService;
	
	@Autowired
	private TipTreningaService tipTreningaService;
	
	@GetMapping(value = "/komentarisi")
	private String prikazStranice(@RequestParam int id,HttpSession session) {
		session.setAttribute("treningID", id);
		return "pojedinacniModeli/korisnikKomentar";
	}
	
	@PostMapping(value = "/komentar")
	@ResponseBody
	public void komentarisanje(@RequestParam String tekstKomentara, @RequestParam String ocena, @RequestParam String anoniman, HttpSession session) {
		
		int treningID = (int) session.getAttribute("treningID");
		Clan clan = (Clan) session.getAttribute("clan");
		Trening trening = treningService.findOneByID(treningID);
		Komentar komentar;
		if(anoniman.equals("da")) {
			komentar = new Komentar(komentarService.generateNewID(), tekstKomentara, Double.parseDouble(ocena), LocalDateTime.now(), null, trening, StatusKomentara.naCekanju, true, true);
		} else {
			komentar = new Komentar(komentarService.generateNewID(), tekstKomentara, Double.parseDouble(ocena), LocalDateTime.now(), clan, trening, StatusKomentara.naCekanju, false, true);			
		}
		komentarService.save(komentar);
	}
}
