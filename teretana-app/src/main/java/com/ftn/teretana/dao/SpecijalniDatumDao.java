package com.ftn.teretana.dao;

import java.util.List;

import com.ftn.teretana.models.Sala;
import com.ftn.teretana.models.SpecijalniDatum;

public interface SpecijalniDatumDao {
	public List<SpecijalniDatum> findAll();
	public void save(SpecijalniDatum specijalniDatum);
	public void update(SpecijalniDatum specijalniDatum);
	public void delete(int id);
	public int generateNewID();
	public SpecijalniDatum findOneByID(int id);
	public List<SpecijalniDatum> getSpecijalniDatumDanas();
}
