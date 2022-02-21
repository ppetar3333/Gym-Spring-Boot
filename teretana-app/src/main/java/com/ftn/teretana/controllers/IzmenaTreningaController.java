package com.ftn.teretana.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.teretana.enums.NivoTreninga;
import com.ftn.teretana.enums.StatusKomentara;
import com.ftn.teretana.enums.TipKorisnika;
import com.ftn.teretana.enums.VrstaTreninga;
import com.ftn.teretana.models.Komentar;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.models.TipTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.KomentarService;
import com.ftn.teretana.service.TerminTreningaService;
import com.ftn.teretana.service.TipTreningaService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value = "IzmenaTreninga")
public class IzmenaTreningaController {
	
	@Autowired
	private TreningService treningService;
	
	@Autowired
	private TipTreningaService tipTreningaService;
	
	@Autowired
	private KomentarService komentarService;
	
	@Autowired
	private TerminTreningaService terminTreningaService;
	
	
	@GetMapping(value = "/jedanTrening")
	private ModelAndView jedanKorisnik(@RequestParam int id, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException  {
		
		Trening trening = treningService.findOneByID(id);
		List<TipTreninga> listaTreninga = tipTreningaService.findAll();
		List<String> tipTreningaLista = new ArrayList<>();
		
		for(TipTreninga tip : listaTreninga) {
			tipTreningaLista.add(tip.getIme());
		}
		
		ModelAndView rezultat = new ModelAndView("/izmenaModela/izmenaTreninga");
		rezultat.addObject("/izmenaModela/izmenaTreninga", trening);
		
		model.addAttribute("tipTreningaLista", tipTreningaLista);
		model.addAttribute("trening", trening);
		
		
		return rezultat;
	}
	
	@PostMapping(value = "/izmenaTreninga")
	private ModelAndView izmenaTreninga(@RequestParam int id, @RequestParam(required=false) String naziv,
			@RequestParam(required=false) String trener,
			@RequestParam(required=false) String opis,
			@RequestParam(required=false) String tipTreninga,
			@RequestParam(required=false) String cena,
			@RequestParam(required=false) String vrstaTreninga,
			@RequestParam(required=false) String nivoTreninga,
			@RequestParam(required=false) String trajanjeTreninga,
			@RequestParam("image") MultipartFile multipartFile, Model model) throws IOException{

		Trening nadjeniTrening = treningService.findOneByID(id);
		TipTreninga tip = tipTreningaService.findByName(tipTreninga);
		
		try {
			boolean ok = true;
			String message = "";
			
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			
			nadjeniTrening.setNaziv(naziv);
			nadjeniTrening.setTrener(trener);
			nadjeniTrening.setOpis(opis);
			nadjeniTrening.setTipTreninga(tip);
			nadjeniTrening.setCena(Double.parseDouble(cena));
			nadjeniTrening.setVrstaTreninga(VrstaTreninga.valueOf(vrstaTreninga));
			nadjeniTrening.setNivoTreninga(NivoTreninga.valueOf(nivoTreninga));
			nadjeniTrening.setTrajanjeTreninga(Integer.parseInt(trajanjeTreninga));
			
			if(fileName != "") {
				nadjeniTrening.setSlika(fileName);
				String uploadDir = "./treninzi-slike/";
				Path uploadPath = Paths.get(uploadDir);
				
				if(!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				
				try (InputStream inputStream = multipartFile.getInputStream()) {
					Path filePath = uploadPath.resolve(fileName);
					Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException ex) {
					throw new IOException("Cannot save img");
				}
			}
			
			if(!nadjeniTrening.getNaziv().matches("^[\\p{L} .'-]+$")) {
				ok = false;
				message += "Naziv ne moze sadrzati brojeve! ";
			}
			
			if(!nadjeniTrening.getTrener().matches("^[\\p{L} .'-]+$")) {
				ok = false;
				message += "Trener ne moze sadrzati brojeve! ";
			}
			
			if(nadjeniTrening.getCena() <= 0) {
				ok = false;
				message += "Cena mora biti veca od 0! ";
			}
			
			if(nadjeniTrening.getTrajanjeTreninga() <= 0) {
				ok = false;
				message += "Trajanje trenigna mora biti vece od 0!"; 
			}
			
			
			if(ok == false) {
				throw new Exception(message);
			}
			
			if(ok == true) {
				treningService.update(nadjeniTrening);
				List<Komentar> komentari = komentarService.findAll();
				
				List<Komentar> komentariZaDatiTrening = new ArrayList<>();
				
				List<TerminTreninga> listaTreningaZaDatiTrening = new ArrayList<>();
				
				List<TerminTreninga> terminiTreninga = terminTreningaService.findAll();
				
				for(Komentar kom : komentari) {
					if(nadjeniTrening.getId() == kom.getTrening().getId() && kom.getStatusKomentara() == StatusKomentara.odobren) {
						komentariZaDatiTrening.add(kom);
					}
				}
				
				for(TerminTreninga termin : terminiTreninga) {
					if(nadjeniTrening.getId() == termin.getTrening().getId()) {
						listaTreningaZaDatiTrening.add(termin);
					}
				}
				
				ModelAndView rezultat = new ModelAndView("/pojedinacniModeli/pojedinacniTrening");
				rezultat.addObject("/pojedinacniModeli/pojedinacniTrening", nadjeniTrening);
				
				model.addAttribute("trening",nadjeniTrening);
				model.addAttribute("terminiSale",listaTreningaZaDatiTrening);
				model.addAttribute("komentari",komentariZaDatiTrening);
				
				return rezultat;
			}
			return null;
			
		} catch (Exception ex) {
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesna izmena treninga!";
			}
			
			ModelAndView rezultat = new ModelAndView("izmenaModela/izmenaTreninga");
			rezultat.addObject("poruka", poruka);
			
			model.addAttribute("trening", nadjeniTrening);
			model.addAttribute("tipTreningaLista", tip.getIme());

			return rezultat;
		}
	}
}
