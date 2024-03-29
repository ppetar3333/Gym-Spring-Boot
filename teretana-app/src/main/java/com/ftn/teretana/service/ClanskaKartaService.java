package com.ftn.teretana.service;

import java.util.List;

import com.ftn.teretana.models.ClanskaKarta;

public interface ClanskaKartaService {
	public List<ClanskaKarta> findAll();
	public void save(ClanskaKarta clanskaKarta);
	public void update(ClanskaKarta clanskaKarta);
	public void delete(int id);
	public void deleteFromDataBase(int id);
	public int generateNewID();
	public ClanskaKarta findOneByID(int id);
}
