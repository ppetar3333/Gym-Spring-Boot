package com.ftn.teretana.dao;

import java.util.List;

import com.ftn.teretana.models.Izvestaj;
import com.ftn.teretana.models.SlobodniTermini;
import com.ftn.teretana.models.TerminTreninga;

public interface TerminTreningaDao {
	public List<TerminTreninga> findAll();
	public void save(TerminTreninga terminTreninga);
	public void update(TerminTreninga terminTreninga);
	public void delete(int id);
	public int generateNewID();
	public TerminTreninga findOneByID(int id);
	public List<Izvestaj> getTermineOdDo(String datumOd, String datumDo);
	public List<TerminTreninga> findTerminiTreninga(int id);
	public List<SlobodniTermini> getSlobodniTermini(int id);
}
