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

import com.ftn.teretana.dao.KorpaDao;
import com.ftn.teretana.models.Komentar;
import com.ftn.teretana.models.Korpa;
import com.ftn.teretana.models.TipTreninga;
import com.ftn.teretana.service.TipTreningaService;

@Repository
public class KorpaDaoImpl implements KorpaDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TipTreningaService tipTreningaService;
	
	private class KorpaRowMapper implements RowMapper<Korpa> {
		
		private List<TipTreninga> listaTipovaTreninga = tipTreningaService.findAll(); 
		
		@Override
		public Korpa mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			int id = rs.getInt(index++);
			String nazivTreninga = rs.getString(index++);
			String trener = rs.getString(index++);
			int treningID = rs.getInt(index++);
			TipTreninga tipTreninga = new TipTreninga();
			for(TipTreninga treningKorpa : listaTipovaTreninga) {
				if(treningID == treningKorpa.getId()) {
					tipTreninga = treningKorpa;
				}
			}
			LocalDateTime datumOdrzavanja = rs.getTimestamp(index++).toLocalDateTime();
			double cena = rs.getDouble(index++);
			boolean aktivan = rs.getBoolean(index++);
			
			Korpa korpa = new Korpa(id,nazivTreninga,trener,tipTreninga,datumOdrzavanja,cena,aktivan);
			return korpa;
		}
	}
	
	@Override
	public List<Korpa> findAll() {
		String sql = "select * from korpa where aktivan=1";
		return jdbcTemplate.query(sql, new KorpaRowMapper());
	}

	@Override
	public int generateNewID() {
		String sql = "select * from korpa";
		List<Korpa> sveUkorpi = jdbcTemplate.query(sql, new KorpaRowMapper());
		int max = 0;
		for(Korpa korpa : sveUkorpi) {
			if(korpa.getId() > max) {
				max = korpa.getId();
			}
		}
		return max + 1;
	}
	
	@Override
	public void save(Korpa korpa) {
		String sql = "insert into korpa (id,nazivTreninga,trener,tipTreningaID,datumOdrzavanja,cena,aktivan) values (?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,korpa.getId(),korpa.getNazivTreninga(),korpa.getTrener(),korpa.getTipTreninga().getId(),korpa.getDatumOdrzavanja(),korpa.getCena(),korpa.isAktivan());
	}

	@Override
	public void update(Korpa korpa) {
		String sql = "update korpa set nazivTreninga=?,trener=?,tipTreningaID=?,datumOdrzavanja=?,cena=?,aktivan=? where id = ?";
		jdbcTemplate.update(sql,korpa.getNazivTreninga(),korpa.getTrener(),korpa.getTipTreninga().getId(),korpa.getDatumOdrzavanja(),korpa.getCena(),korpa.isAktivan(),korpa.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "update korpa set aktivan = 0 where id = ?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public Korpa findOneByID(int id) {
		try {
			String sql = "select * from korpa where id=?";
			return jdbcTemplate.queryForObject(sql, new KorpaRowMapper(),id);
		} catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

}
