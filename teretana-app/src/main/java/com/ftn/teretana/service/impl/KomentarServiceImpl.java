package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.KomentarDao;
import com.ftn.teretana.models.Komentar;
import com.ftn.teretana.service.KomentarService;

@Service
public class KomentarServiceImpl implements KomentarService{

	@Autowired
	private KomentarDao komentarDao;
	
	@Override
	public List<Komentar> findAll() {
		return komentarDao.findAll();
	}

	@Override
	public void save(Komentar komentar) {
		komentarDao.save(komentar);
	}

	@Override
	public void update(Komentar komentar) {
		komentarDao.update(komentar);
	}

	@Override
	public void delete(int id) {
		komentarDao.delete(id);
	}

	@Override
	public int generateNewID() {
		return komentarDao.generateNewID();
	}

	@Override
	public Komentar findOneByID(int id) {
		return komentarDao.findOneByID(id);
	}

}
