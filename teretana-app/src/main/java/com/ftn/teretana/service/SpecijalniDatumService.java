package com.ftn.teretana.service;

import java.util.List;

import com.ftn.teretana.models.SpecijalniDatum;

public interface SpecijalniDatumService {
	public List<SpecijalniDatum> findAll();
	public void save(SpecijalniDatum specijalniDatum);
	public void update(SpecijalniDatum specijalniDatum);
	public void delete(int id);
	public int generateNewID();
	public SpecijalniDatum findOneByID(int id);
	public List<SpecijalniDatum> getSpecijalniDatumDanas();
}
