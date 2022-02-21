package com.ftn.teretana.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.teretana.enums.NivoTreninga;
import com.ftn.teretana.enums.VrstaTreninga;
import com.ftn.teretana.models.TipTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.TipTreningaService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value = "DodavanjeTreninga")
public class DodavanjeTreningaController {

	
	@Autowired
	private TipTreningaService tipTreningaService;
	
	@Autowired
	private TreningService treningService;
	
	@GetMapping(value = "/dodavanjeTreninga")
	private String dodavanjeTreninga(Model model) {
		
		List<TipTreninga> listaTreninga = tipTreningaService.findAll();
		List<String> tipTreningaLista = new ArrayList<>();
		
		for(TipTreninga tip : listaTreninga) {
			tipTreningaLista.add(tip.getIme());
		}
		
		model.addAttribute("tipTreningaLista", tipTreningaLista);
		
		return "dodavanjeModela/dodavanjeTreninga";
	}
	
	@PostMapping(value = "/dodavanjeTreninga")
	private ModelAndView dodavanjeTreningaSave(@RequestParam(required=false) String naziv, @RequestParam(required=false) String opis, @RequestParam(required=false) String trener, @RequestParam(required=false) String tipTreninga,
			@RequestParam(required=false) String cena, @RequestParam(required=false) String vrstaTreninga, @RequestParam(required=false) String nivoTreninga,
			@RequestParam("image") MultipartFile multipartFile, @RequestParam(required=false) String trajanjeTreninga, Model model) throws IOException {
		
		List<TipTreninga> tipTreningaLista = tipTreningaService.findAll();
		List<String> tipTreningaString = new ArrayList<>();
		
		for(TipTreninga tip : tipTreningaLista) {
			tipTreningaString.add(tip.getIme());
		}
		
		try {
			
			boolean ok = true;
			String message = "";
			
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			
			TipTreninga nadjeniTipTreninga = tipTreningaService.findByName(tipTreninga);
			
			double ocena = 0;
			
			Trening noviTrening = new Trening(tipTreningaService.generateNewID(),naziv,trener,opis,nadjeniTipTreninga,Double.parseDouble(cena),VrstaTreninga.valueOf(vrstaTreninga),NivoTreninga.valueOf(nivoTreninga),fileName,Integer.parseInt(trajanjeTreninga),ocena,true);
			
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
			
			if(!noviTrening.getNaziv().matches("^[\\p{L} .'-]+$")) {
				ok = false;
				message += "Naziv ne moze sadrzati brojeve! ";
			}
			
			if(!noviTrening.getTrener().matches("^[\\p{L} .'-]+$")) {
				ok = false;
				message += "Trener ne moze sadrzati brojeve! ";
			}
			
			if(noviTrening.getCena() <= 0) {
				ok = false;
				message += "Cena mora biti veca od 0! ";
			}
			
			if(noviTrening.getTrajanjeTreninga() <= 0) {
				ok = false;
				message += "Trajanje trenigna mora biti vece od 0!"; 
			}
			
			if(ok == false) {
				throw new Exception(message);
			}
			
			if(ok == true) {
				List<Trening> listaTreninga = treningService.findAll();
				listaTreninga.add(noviTrening);
				
				treningService.save(noviTrening);
				
				ModelAndView rezultat = new ModelAndView("index");
				model.addAttribute("treninziLista", listaTreninga);
				model.addAttribute("tipTreningaLista", tipTreningaString);
				
				return rezultat;
			}
			
			return null;
			
		} catch (Exception ex) {
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesno dodavanje treninga!";
			}
			ModelAndView rezultat = new ModelAndView("dodavanjeModela/dodavanjeTreninga");
			rezultat.addObject("poruka", poruka);
			
			model.addAttribute("tipTreningaLista", tipTreningaString);

			return rezultat;
		}
	}
}
