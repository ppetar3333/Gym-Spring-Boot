package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.KorpaDao;
import com.ftn.teretana.models.Korpa;
import com.ftn.teretana.service.KorpaService;

@Service
public class KorpaServiceImpl implements KorpaService{

	@Autowired
	private KorpaDao korpaDao;
	
	@Override
	public List<Korpa> findAll() {
		return korpaDao.findAll();
	}

	@Override
	public void save(Korpa korpa) {
		korpaDao.save(korpa);
	}

	@Override
	public void update(Korpa korpa) {
		korpaDao.update(korpa);
	}

	@Override
	public void delete(int id) {
		korpaDao.delete(id);
	}

	@Override
	public int generateNewID() {
		return korpaDao.generateNewID();
	}

	@Override
	public Korpa findOneByID(int id) {
		return korpaDao.findOneByID(id);
	}

}
