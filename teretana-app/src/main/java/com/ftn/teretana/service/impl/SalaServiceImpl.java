package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.SalaDao;
import com.ftn.teretana.models.PopunjenostSale;
import com.ftn.teretana.models.Sala;
import com.ftn.teretana.service.SalaService;

@Service
public class SalaServiceImpl implements SalaService{

	@Autowired
	private SalaDao salaDao;
	
	@Override
	public List<Sala> findAll() {
		return salaDao.findAll();
	}

	@Override
	public void save(Sala sala) {
		salaDao.save(sala);
	}

	@Override
	public void update(Sala sala) {
		salaDao.update(sala);
	}

	@Override
	public void delete(int id) {
		salaDao.delete(id);
	}

	@Override
	public int generateNewID() {
		return salaDao.generateNewID();
	}

	@Override
	public Sala findOneByID(int id) {
		return salaDao.findOneByID(id);
	}

	@Override
	public List<Sala> rezultatiPretrage(String oznakaSale) {
		return salaDao.rezultatiPretrage(oznakaSale);
	}

	@Override
	public List<PopunjenostSale> getPopunjenostSale() {
		return salaDao.getPopunjenostSale();
	}

}
