package com.ftn.teretana.service;

import java.util.List;

import com.ftn.teretana.models.TipTreninga;

public interface TipTreningaService {
	public List<TipTreninga> findAll();
	public void save(TipTreninga tipTreninga);
	public void update(TipTreninga tipTreninga);
	public void delete(int id);
	public int generateNewID();
	public TipTreninga findOneByID(int id);
	public TipTreninga findByName(String ime);
}
