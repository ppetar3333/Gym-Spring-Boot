package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.ListaZeljaDao;
import com.ftn.teretana.models.ListaZelja;
import com.ftn.teretana.service.ListaZeljaService;

@Service
public class ListaZeljaServiceImpl implements ListaZeljaService{

	@Autowired
	private ListaZeljaDao listaZeljaDao;
	
	@Override
	public List<ListaZelja> findAll() {
		return listaZeljaDao.findAll();
	}

	@Override
	public void save(ListaZelja listaZelja) {
		listaZeljaDao.save(listaZelja);
	}

	@Override
	public void update(ListaZelja listaZelja) {
		listaZeljaDao.update(listaZelja);
	}

	@Override
	public void delete(int id) {
		listaZeljaDao.delete(id);
	}

	@Override
	public int generateNewID() {
		return listaZeljaDao.generateNewID();
	}

	@Override
	public ListaZelja findOneByID(int id) {
		return listaZeljaDao.findOneByID(id);
	}

}
