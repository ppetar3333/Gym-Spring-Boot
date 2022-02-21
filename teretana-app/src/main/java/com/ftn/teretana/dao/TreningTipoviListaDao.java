package com.ftn.teretana.dao;

import java.util.List;

import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.TreningTipoviLista;

public interface TreningTipoviListaDao {
	public List<TreningTipoviLista> findAll();
	public void save(TreningTipoviLista treningTipoviLista);
	public void update(TreningTipoviLista treningTipoviLista);
	public void delete(int id);
	public TreningTipoviLista findOneById(int id);
	public int generateNewID();
}
