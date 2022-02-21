package com.ftn.teretana.service;

import java.util.List;

import com.ftn.teretana.models.Korisnik;

public interface KorisnikService {
	public void save(Korisnik korisnik);
	public void update(Korisnik korisnik);
	public void delete(int id);
	public Korisnik findOneById(int id);
	public Korisnik findOneByUsernamePassowrd(String username,String passowrd);
	public Korisnik findOneByUsername(String username);
	public List<Korisnik> findAll();
	public int generateNewID();
	public List<Korisnik> rezultatiPretrage(String korisnickoIme,String tip);
}
