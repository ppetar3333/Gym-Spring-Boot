package com.ftn.teretana.service;

import java.util.List;

import com.ftn.teretana.models.ListaZelja;

public interface ListaZeljaService {
	public List<ListaZelja> findAll();
	public void save(ListaZelja listaZelja);
	public void update(ListaZelja listaZelja);
	public void delete(int id);
	public int generateNewID();
	public ListaZelja findOneByID(int id);
}
