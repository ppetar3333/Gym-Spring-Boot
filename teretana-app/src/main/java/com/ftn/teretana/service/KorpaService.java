package com.ftn.teretana.service;

import java.util.List;

import com.ftn.teretana.models.Korpa;

public interface KorpaService {
	public List<Korpa> findAll();
	public void save(Korpa korpa);
	public void update(Korpa korpa);
	public void delete(int id);
	public int generateNewID();
	public Korpa findOneByID(int id);
}
