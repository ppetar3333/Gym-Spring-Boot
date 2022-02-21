package com.ftn.teretana.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.ftn.teretana.dao.KomentarDao;
import com.ftn.teretana.enums.StatusKomentara;
import com.ftn.teretana.enums.TipKorisnika;
import com.ftn.teretana.models.Admin;
import com.ftn.teretana.models.Clan;
import com.ftn.teretana.models.ClanskaKarta;
import com.ftn.teretana.models.Komentar;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.KorisnikService;
import com.ftn.teretana.service.TreningService;

@Repository
public class KomentarDaoImpl implements KomentarDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private TreningService treningService;
	
	private class KomentarRowMapper implements RowMapper<Komentar> {

		private List<Korisnik> listaKorisnika = korisnikService.findAll();
		
		private List<Trening> listaTreninga = treningService.findAll(); 
		
		@Override
		public Komentar mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			int id = rs.getInt(index++);
			String tekstKomentara = rs.getString(index++);
			double ocena = rs.getDouble(index++);
			LocalDateTime datumPostavljanja = rs.getTimestamp(index++).toLocalDateTime();
			int clanID = rs.getInt(index++);
			Clan clan = new Clan();
			for(Korisnik clanTreninga : listaKorisnika) {
				if(clanID == clanTreninga.getId()) {
					clan = (Clan) clanTreninga;
				}
			}
			int treningID = rs.getInt(index++);
			Trening trening = new Trening();
			for(Trening treningKomentar : listaTreninga) {
				if(treningID == treningKomentar.getId()) {
					trening = treningKomentar;
				}
			}
			StatusKomentara statusKomentara = StatusKomentara.valueOf(rs.getString(index++));
			boolean anoniman = rs.getBoolean(index++);
			boolean aktivan = rs.getBoolean(index++);
			
			Komentar komentar = new Komentar(id,tekstKomentara,ocena,datumPostavljanja,clan,trening,statusKomentara,anoniman,aktivan);
			return komentar;
		}
	}
	
	@Override
	public List<Komentar> findAll() {
		String sql = "select * from komentar where aktivan=1";
		return jdbcTemplate.query(sql, new KomentarRowMapper());
	}

	@Override
	public int generateNewID() {
		String sql = "select * from komentar";
		List<Komentar> komentari = jdbcTemplate.query(sql, new KomentarRowMapper());
		int max = 0;
		for(Komentar komentar : komentari) {
			if(komentar.getId() > max) {
				max = komentar.getId();
			}
		}
		return max + 1;
	}
	
	@Override
	public void save(Komentar komentar) {
		if(komentar.getClan() == null) {
			String sql = "insert into komentar (id,tekstKomentara,ocena,datumPostavljanja,clanID,treningID,statusKomentara,anoniman,aktivan) values (?,?,?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql,komentar.getId(),komentar.getTekstKomentara(),komentar.getOcena(),komentar.getDatumPostavljanja(),null,komentar.getTrening().getId(),komentar.getStatusKomentara().toString(),komentar.isAnoniman(),komentar.isAktivan());
		} else {
			String sql = "insert into komentar (id,tekstKomentara,ocena,datumPostavljanja,clanID,treningID,statusKomentara,anoniman,aktivan) values (?,?,?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql,komentar.getId(),komentar.getTekstKomentara(),komentar.getOcena(),komentar.getDatumPostavljanja(),komentar.getClan().getId(),komentar.getTrening().getId(),komentar.getStatusKomentara().toString(),komentar.isAnoniman(),komentar.isAktivan());
		}
	}

	@Override
	public void update(Komentar komentar) {
		if(komentar.getClan().getId() == 0) {
			String sql = "update komentar set tekstKomentara=?,ocena=?,datumPostavljanja=?,clanID=?,treningID=?,statusKomentara=?,anoniman=?,aktivan=? where id = ?";
			jdbcTemplate.update(sql,komentar.getTekstKomentara(),komentar.getOcena(),komentar.getDatumPostavljanja(),null,komentar.getTrening().getId(),komentar.getStatusKomentara().toString(),komentar.isAnoniman(),komentar.isAktivan(),komentar.getId());
		} else {
			String sql = "update komentar set tekstKomentara=?,ocena=?,datumPostavljanja=?,clanID=?,treningID=?,statusKomentara=?,anoniman=?,aktivan=? where id = ?";
			jdbcTemplate.update(sql,komentar.getTekstKomentara(),komentar.getOcena(),komentar.getDatumPostavljanja(),komentar.getClan().getId(),komentar.getTrening().getId(),komentar.getStatusKomentara().toString(),komentar.isAnoniman(),komentar.isAktivan(),komentar.getId());	
		}
	}

	@Override
	public void delete(int id) {
		String sql = "update komentar set aktivan = 0 where id = ?";
		jdbcTemplate.update(sql,id);
	}


	@Override
	public Komentar findOneByID(int id) {
		try {
			String sql = "select * from komentar where id=?";
			return jdbcTemplate.queryForObject(sql, new KomentarRowMapper(),id);
		} catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

}
