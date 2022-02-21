package com.ftn.teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.teretana.dao.ListaZeljaDao;
import com.ftn.teretana.models.Clan;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.Korpa;
import com.ftn.teretana.models.ListaZelja;
import com.ftn.teretana.models.Sala;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.KorisnikService;
import com.ftn.teretana.service.SalaService;
import com.ftn.teretana.service.TreningService;

@Repository
public class ListaZeljaDaoImpl implements ListaZeljaDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TreningService treningService;
	
	private class ListaZeljaRowMapper implements RowMapper<ListaZelja> {
		
		private List<Korisnik> listaKorisnika = korisnikService.findAll();
		
		private List<Trening> listaTreninga = treningService.findAll();
		
		@Override
		public ListaZelja mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			int id = rs.getInt(index++);
			int clanID = rs.getInt(index++);
			Clan clan = new Clan();
			for(Korisnik clanTreninga : listaKorisnika) {
				if(clanID == clanTreninga.getId()) {
					clan = (Clan) clanTreninga;
				}
			}
			int treningID = rs.getInt(index++);
			Trening trening = new Trening();
			for(Trening treningTermin : listaTreninga) {
				if(treningID == treningTermin.getId()) {
					trening = treningTermin;
				}
			}
			boolean aktivan = rs.getBoolean(index++);

			ListaZelja terminTreninga = new ListaZelja(id,clan,trening,aktivan);
			return terminTreninga;
		}

	}
	
	@Override
	public List<ListaZelja> findAll() {
		String sql = "select * from listazelja where aktivan=1";
		return jdbcTemplate.query(sql, new ListaZeljaRowMapper());
	}

	@Override
	public int generateNewID() {
		String sql = "select * from listazelja";
		List<ListaZelja> listaZelja = jdbcTemplate.query(sql, new ListaZeljaRowMapper());
		int max = 0;
		for(ListaZelja zelje : listaZelja) {
			if(zelje.getId() > max) {
				max = zelje.getId();
			}
		}
		return max + 1;
	}
	
	@Override
	public void save(ListaZelja listaZelja) {
		String sql = "insert into listazelja (id,clanID,treningID,aktivan) values (?,?,?,?)";
		jdbcTemplate.update(sql,listaZelja.getId(),listaZelja.getClan().getId(),listaZelja.getTrening().getId(),listaZelja.isAktivan());
	}

	@Override
	public void update(ListaZelja listaZelja) {
		String sql = "update listazelja set clanID=?,treningID=?,aktivan=? where id = ?";
		jdbcTemplate.update(sql,listaZelja.getClan().getId(),listaZelja.getTrening().getId(),listaZelja.isAktivan(),listaZelja.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "update listazelja set aktivan = 0 where id = ?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public ListaZelja findOneByID(int id) {
		try {
			String sql = "select * from listazelja where id=?";
			return jdbcTemplate.queryForObject(sql, new ListaZeljaRowMapper(),id);
		} catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

}
