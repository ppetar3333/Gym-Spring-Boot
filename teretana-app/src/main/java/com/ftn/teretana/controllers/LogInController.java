package com.ftn.teretana.controllers;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.teretana.enums.TipKorisnika;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.KorisnikService;
import com.ftn.teretana.service.TreningService;

@Controller
@RequestMapping(value="/PrijavaNaSistem")
public class LogInController {

	@Autowired
	private KorisnikService korisnikService;
	
	public static final String CLAN_KEY = "clan";
	public static final String ADMIN_KEY = "admin";
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL;
	
	@Autowired
	private TreningService treningService;
	
	@PostConstruct
	public void init() {	
		baseURL = servletContext.getContextPath() + "/";			
	}

    @GetMapping(value="/prijava")
    public String prijava() {
        return "logIn";
    }
    
    @PostMapping(value = "/logIn")
    public ModelAndView postLogin(@RequestParam String korisnickoIme, @RequestParam String lozinka, 
			HttpSession session, HttpServletResponse response) throws IOException {
		try {
			Korisnik korisnik = korisnikService.findOneByUsernamePassowrd(korisnickoIme, lozinka);
			
			if (korisnik == null) {
				throw new Exception("Neispravno korisnicko ime ili lozinka!");
			} else if(korisnik.getTipKorisnika() == TipKorisnika.clan) {
				System.out.println("Ulogovan kao clan. Ime: " + korisnik.getIme());
				session.setAttribute(LogInController.CLAN_KEY, korisnik);
			} else if(korisnik.getTipKorisnika() == TipKorisnika.admin) {
				System.out.println("Ulogovan kao admin. Ime: " + korisnik.getIme());
				session.setAttribute(LogInController.ADMIN_KEY, korisnik);
			} else {
				System.out.println("Greska");
			}
			
			response.sendRedirect(baseURL);
			
			return null;
		} catch (Exception ex) {
			String poruka = ex.getMessage();
			if (poruka == "") {
				poruka = "Neuspesna prijava!";
			}
			
			ModelAndView rezultat = new ModelAndView("logIn");
			rezultat.addObject("poruka", poruka);

			return rezultat;
		}
    }
    
	@GetMapping(value="/Logout")
	public void logout(HttpSession session, HttpServletResponse response) throws IOException {
		session.invalidate();
		response.sendRedirect(baseURL);
	}
	
}
