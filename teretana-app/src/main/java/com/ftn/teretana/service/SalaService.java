package com.ftn.teretana.service;

import java.util.List;

import com.ftn.teretana.models.PopunjenostSale;
import com.ftn.teretana.models.Sala;

public interface SalaService {
	public List<Sala> findAll();
	public void save(Sala sala);
	public void update(Sala sala);
	public void delete(int id);
	public int generateNewID();
	public Sala findOneByID(int id);
	public List<Sala> rezultatiPretrage(String oznakaSale);
	public List<PopunjenostSale> getPopunjenostSale();
}
