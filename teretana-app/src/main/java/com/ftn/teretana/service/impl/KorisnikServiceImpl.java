package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.KorisnikDao;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.service.KorisnikService;

@Service
public class KorisnikServiceImpl implements KorisnikService{

	@Autowired
	private KorisnikDao korisnikDao;
	
	@Override
	public void save(Korisnik korisnik) {
		korisnikDao.save(korisnik);
	}

	@Override
	public void update(Korisnik korisnik) {
		korisnikDao.update(korisnik);
	}

	@Override
	public void delete(int id) {
		korisnikDao.delete(id);
	}

	@Override
	public Korisnik findOneById(int id) {
		return korisnikDao.findOneById(id);
	}

	@Override
	public Korisnik findOneByUsernamePassowrd(String username, String passowrd) {
		return korisnikDao.findOneByUsernamePassowrd(username, passowrd);
	}

	@Override
	public List<Korisnik> findAll() {
		return korisnikDao.findAll();
	}

	@Override
	public int generateNewID() {
		return korisnikDao.generateNewID();
	}

	@Override
	public List<Korisnik> rezultatiPretrage(String korisnickoIme, String tip) {
		return korisnikDao.rezultatiPretrage(korisnickoIme, tip);
	}

	@Override
	public Korisnik findOneByUsername(String username) {
		return korisnikDao.findOneByUsername(username);
	}

}
