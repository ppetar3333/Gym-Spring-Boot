package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.TerminTreningaDao;
import com.ftn.teretana.models.Izvestaj;
import com.ftn.teretana.models.SlobodniTermini;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.service.TerminTreningaService;

@Service
public class TerminTreningaServiceImpl implements TerminTreningaService{

	@Autowired
	private TerminTreningaDao terminTreningaDao;
	
	@Override
	public List<TerminTreninga> findAll() {
		return terminTreningaDao.findAll();
	}

	@Override
	public void save(TerminTreninga terminTreninga) {
		terminTreningaDao.save(terminTreninga);
	}

	@Override
	public void update(TerminTreninga terminTreninga) {
		terminTreningaDao.update(terminTreninga);
	}

	@Override
	public void delete(int id) {
		terminTreningaDao.delete(id);
	}

	@Override
	public int generateNewID() {
		return terminTreningaDao.generateNewID();
	}

	@Override
	public TerminTreninga findOneByID(int id) {
		return terminTreningaDao.findOneByID(id);
	}

	@Override
	public List<Izvestaj> getTermineOdDo(String datumOd, String datumDo) {
		return terminTreningaDao.getTermineOdDo(datumOd, datumDo);
	}

	@Override
	public List<TerminTreninga> findTerminiTreninga(int id) {
		return terminTreningaDao.findTerminiTreninga(id);
	}

	@Override
	public List<SlobodniTermini> getSlobodniTermini(int id) {
		return terminTreningaDao.getSlobodniTermini(id);
	}

}
