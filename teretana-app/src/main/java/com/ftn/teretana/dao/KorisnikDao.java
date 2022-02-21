package com.ftn.teretana.dao;

import java.util.List;

import com.ftn.teretana.models.Korisnik;

public interface KorisnikDao {
	public List<Korisnik> findAll();
	public void save(Korisnik korisnik);
	public void update(Korisnik korisnik);
	public void delete(int id);
	public Korisnik findOneById(int id);
	public Korisnik findOneByUsernamePassowrd(String username,String passowrd);
	public Korisnik findOneByUsername(String username);
	public int generateNewID();
	public List<Korisnik> rezultatiPretrage(String korisnickoIme,String tip);
}
