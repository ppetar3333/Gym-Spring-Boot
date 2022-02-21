package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.SpecijalniDatumDao;
import com.ftn.teretana.models.SpecijalniDatum;
import com.ftn.teretana.service.SpecijalniDatumService;

@Service
public class SpecijalniDatumServiceImpl implements SpecijalniDatumService{

	@Autowired
	private SpecijalniDatumDao specijalniDatumDao;
	
	@Override
	public List<SpecijalniDatum> findAll() {
		return specijalniDatumDao.findAll();
	}

	@Override
	public void save(SpecijalniDatum specijalniDatum) {
		specijalniDatumDao.save(specijalniDatum);
	}

	@Override
	public void update(SpecijalniDatum specijalniDatum) {
		specijalniDatumDao.update(specijalniDatum);
	}

	@Override
	public void delete(int id) {
		specijalniDatumDao.delete(id);
	}

	@Override
	public int generateNewID() {
		return specijalniDatumDao.generateNewID();
	}

	@Override
	public SpecijalniDatum findOneByID(int id) {
		return specijalniDatumDao.findOneByID(id);
	}

	@Override
	public List<SpecijalniDatum> getSpecijalniDatumDanas() {
		return specijalniDatumDao.getSpecijalniDatumDanas();
	}

}
