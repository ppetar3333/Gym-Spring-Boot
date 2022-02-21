package com.ftn.teretana.dao;

import java.util.List;

import com.ftn.teretana.models.ClanskaKarta;
import com.ftn.teretana.models.Komentar;

public interface KomentarDao {
	public List<Komentar> findAll();
	public void save(Komentar komentar);
	public void update(Komentar komentar);
	public void delete(int id);
	public int generateNewID();
	public Komentar findOneByID(int id);
}
