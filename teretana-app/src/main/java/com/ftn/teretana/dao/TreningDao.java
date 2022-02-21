package com.ftn.teretana.dao;

import java.util.List;

import com.ftn.teretana.models.Trening;

public interface TreningDao {
	public List<Trening> findAll();
	public void save(Trening trening);
	public void update(Trening trening);
	public void delete(int id);
	public int generateNewID();
	public Trening findOneByID(int id);
	public List<Trening> rezultatiPretrage(String naziv,String tipTreninga,String cenaOd,String cenaDo,String trener,String vrsta,String nivo,String sortiraj);
	public List<Trening> treninziSaBrojemTermina();
}
