package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.TipTreningaDao;
import com.ftn.teretana.models.TipTreninga;
import com.ftn.teretana.service.TipTreningaService;

@Service
public class TipTreningaServiceImpl implements TipTreningaService {

	@Autowired
	private TipTreningaDao tipTreningaDao;
	
	@Override
	public List<TipTreninga> findAll() {
		return tipTreningaDao.findAll();
	}

	@Override
	public void save(TipTreninga tipTreninga) {
		tipTreningaDao.save(tipTreninga);
	}

	@Override
	public void update(TipTreninga tipTreninga) {
		tipTreningaDao.update(tipTreninga);
	}

	@Override
	public void delete(int id) {
		tipTreningaDao.delete(id);
	}

	@Override
	public int generateNewID() {
		return tipTreningaDao.generateNewID();
	}

	@Override
	public TipTreninga findOneByID(int id) {
		return tipTreningaDao.findOneByID(id);
	}

	@Override
	public TipTreninga findByName(String ime) {
		return tipTreningaDao.findByName(ime);
	}

}
