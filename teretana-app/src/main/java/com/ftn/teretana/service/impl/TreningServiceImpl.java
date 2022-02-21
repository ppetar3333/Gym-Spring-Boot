package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.TreningDao;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.TreningService;

@Service
public class TreningServiceImpl implements TreningService{

	@Autowired
	private TreningDao treningDao;
	
	@Override
	public List<Trening> findAll() {
		return treningDao.findAll();
	}

	@Override
	public void save(Trening trening) {
		treningDao.save(trening);
	}

	@Override
	public void update(Trening trening) {
		treningDao.update(trening);
	}

	@Override
	public void delete(int id) {
		treningDao.delete(id);
	}

	@Override
	public int generateNewID() {
		return treningDao.generateNewID();
	}

	@Override
	public Trening findOneByID(int id) {
		return treningDao.findOneByID(id);
	}

	@Override
	public List<Trening> rezultatiPretrage(String naziv, String tipTreninga, String cenaOd, String cenaDo,
			String trener, String vrsta, String nivo,String sortiraj) {
		return treningDao.rezultatiPretrage(naziv, tipTreninga, cenaOd, cenaDo, trener, vrsta, nivo, sortiraj);
	}

	@Override
	public List<Trening> treninziSaBrojemTermina() {
		return treningDao.treninziSaBrojemTermina();
	}

}
