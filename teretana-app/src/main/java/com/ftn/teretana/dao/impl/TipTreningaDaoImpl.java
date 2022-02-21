package com.ftn.teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.teretana.dao.TipTreningaDao;
import com.ftn.teretana.models.TerminTreninga;
import com.ftn.teretana.models.TipTreninga;

@Repository
public class TipTreningaDaoImpl implements TipTreningaDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class TipTreningaRowMapper implements RowMapper<TipTreninga> {

		@Override
		public TipTreninga mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			int id = rs.getInt(index++);
			String ime = rs.getString(index++);
			String opis = rs.getString(index++);
			boolean aktivan = rs.getBoolean(index++);
			
			TipTreninga tipTreninga = new TipTreninga(id,ime,opis,aktivan);
			return tipTreninga;
		}
	}
	
	@Override
	public List<TipTreninga> findAll() {
		String sql = "select * from tiptreninga where aktivan=1";
		return jdbcTemplate.query(sql, new TipTreningaRowMapper());
	}

	@Override
	public int generateNewID() {
		String sql = "select * from tiptreninga";
		List<TipTreninga> sviTipovi = jdbcTemplate.query(sql, new TipTreningaRowMapper());
		int max = 0;
		for(TipTreninga tip : sviTipovi) {
			if(tip.getId() > max) {
				max = tip.getId();
			}
		}
		return max + 1;
	}
	
	@Override
	public void save(TipTreninga tipTreninga) {
		String sql = "insert into tiptreninga (id,ime,opis,aktivan) values (?,?,?,?)";
		jdbcTemplate.update(sql,tipTreninga.getId(),tipTreninga.getIme(),tipTreninga.getOpis(),tipTreninga.isAktivan());
	}

	@Override
	public void update(TipTreninga tipTreninga) {
		String sql = "update tiptreninga set ime=?,opis=?,aktivan=? where id = ?";
		jdbcTemplate.update(sql,tipTreninga.getIme(),tipTreninga.getOpis(),tipTreninga.isAktivan(),tipTreninga.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "update tiptreninga set aktivan = 0 where id = ?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public TipTreninga findOneByID(int id) {
		try {
			String sql = "select * from tiptreninga where id=?";
			return jdbcTemplate.queryForObject(sql, new TipTreningaRowMapper(),id);
		} catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public TipTreninga findByName(String ime) {
		for(TipTreninga tip : findAll()) {
			if(ime.equals(tip.getIme())) {
				return tip;
			}
		}
		return null;
	}

}
