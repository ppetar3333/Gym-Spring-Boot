package com.ftn.teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.teretana.dao.ClanskaKartaDao;
import com.ftn.teretana.models.Clan;
import com.ftn.teretana.models.ClanskaKarta;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.service.KorisnikService;

@Repository
public class ClanskaKartaDaoImpl implements ClanskaKartaDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private KorisnikService korisnikService;
	
	private class ClanskaKartaRowMapper implements RowMapper<ClanskaKarta> {

		private List<Korisnik> listaKorisnika = korisnikService.findAll();
		@Override
		public ClanskaKarta mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			int id = rs.getInt(index++);
			int popust = rs.getInt(index++);
			int brojPoena = rs.getInt(index++);
			int clanID = rs.getInt(index++);
			Clan clan = new Clan();
			for(Korisnik clanKartice : listaKorisnika) {
				if(clanID == clanKartice.getId()) {
					clan = (Clan) clanKartice;
				}
			}
			boolean prihvacen = rs.getBoolean(index++);
			boolean iskoriscena = rs.getBoolean(index++);
			boolean aktivan = rs.getBoolean(index++);

			ClanskaKarta clanskaKarta = new ClanskaKarta(id,popust,brojPoena,clan,prihvacen,iskoriscena,aktivan);
			return clanskaKarta;
		}

	}
	
	@Override
	public List<ClanskaKarta> findAll() {
		String sql = "select * from clanskakarta where aktivan=1";
		return jdbcTemplate.query(sql, new ClanskaKartaRowMapper());
	}

	@Override
	public int generateNewID() {
		String sql = "select * from clanskakarta";
		List<ClanskaKarta> clanskeKarte = jdbcTemplate.query(sql, new ClanskaKartaRowMapper());
		int max = 0;
		for(ClanskaKarta karte : clanskeKarte) {
			if(karte.getId() > max) {
				max = karte.getId();
			}
		}
		return max + 1;
	}
	
	@Override
	public void save(ClanskaKarta clanskaKarta) {
		String sql = "insert into clanskakarta (id,popust,brojPoena,clanID,prihvacen,iskoricena,aktivan) values (?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,clanskaKarta.getId(),clanskaKarta.getPopust(),clanskaKarta.getBrojPoena(),clanskaKarta.getClan().getId(), clanskaKarta.isPrihvacen(),clanskaKarta.isIskoriscena(),clanskaKarta.isAktivan());
	}

	@Override
	public void update(ClanskaKarta clanskaKarta) {
		String sql = "update clanskakarta set popust=?,brojPoena=?,clanID=?,prihvacen=?,iskoricena=?,aktivan=? where id = ?";
		jdbcTemplate.update(sql,clanskaKarta.getPopust(),clanskaKarta.getBrojPoena(),clanskaKarta.getClan().getId(),clanskaKarta.isPrihvacen(),clanskaKarta.isIskoriscena(),clanskaKarta.isAktivan(),clanskaKarta.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "update clanskakarta set aktivan = 0 where id = ?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public void deleteFromDataBase(int id) {
		String sql = "delete from clanskakarta where id = ?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public ClanskaKarta findOneByID(int id) {
		try {
			String sql = "select * from clanskakarta where id=?";
			return jdbcTemplate.queryForObject(sql, new ClanskaKartaRowMapper(),id);
		} catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

}
