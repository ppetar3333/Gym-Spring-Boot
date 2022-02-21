package com.ftn.teretana.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.teretana.dao.TreningTipoviListaDao;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.TreningTipoviLista;
import com.ftn.teretana.service.TreningTipoviListaService;

@Service
public class TreningTipoviListaServiceImpl implements TreningTipoviListaService{

	@Autowired
	private TreningTipoviListaDao treningTipoviListaDao;
	
	@Override
	public List<TreningTipoviLista> findAll() {
		return treningTipoviListaDao.findAll();
	}

	@Override
	public void save(TreningTipoviLista treningTipoviLista) {
		treningTipoviListaDao.save(treningTipoviLista);
	}

	@Override
	public void update(TreningTipoviLista treningTipoviLista) {
		treningTipoviListaDao.update(treningTipoviLista);
	}

	@Override
	public void delete(int id) {
		treningTipoviListaDao.delete(id);
	}

	@Override
	public int generateNewID() {
		return treningTipoviListaDao.generateNewID();
	}

	@Override
	public TreningTipoviLista findOneById(int id) {
		return treningTipoviListaDao.findOneById(id);
	}

}
