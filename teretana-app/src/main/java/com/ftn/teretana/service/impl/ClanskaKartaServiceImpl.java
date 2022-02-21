package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.ClanskaKartaDao;
import com.ftn.teretana.models.ClanskaKarta;
import com.ftn.teretana.service.ClanskaKartaService;

@Service
public class ClanskaKartaServiceImpl implements ClanskaKartaService{

	@Autowired
	private ClanskaKartaDao clanskaKartaDao;
	
	@Override
	public List<ClanskaKarta> findAll() {
		return clanskaKartaDao.findAll();
	}

	@Override
	public void save(ClanskaKarta clanskaKarta) {
		clanskaKartaDao.save(clanskaKarta);
	}

	@Override
	public void update(ClanskaKarta clanskaKarta) {
		clanskaKartaDao.update(clanskaKarta);
	}

	@Override
	public void delete(int id) {
		clanskaKartaDao.delete(id);
	}

	@Override
	public int generateNewID() {
		return clanskaKartaDao.generateNewID();
	}

	@Override
	public ClanskaKarta findOneByID(int id) {
		return clanskaKartaDao.findOneByID(id);
	}

	@Override
	public void deleteFromDataBase(int id) {
		clanskaKartaDao.deleteFromDataBase(id);
	}

}
