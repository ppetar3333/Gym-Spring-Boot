package com.ftn.teretana.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.teretana.dao.KorisnikDao;
import com.ftn.teretana.enums.TipKorisnika;
import com.ftn.teretana.models.Admin;
import com.ftn.teretana.models.Clan;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.Trening;

@Repository
public class KorisnikDaoImpl  implements KorisnikDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class KorisnikRowMapper implements RowMapper<Korisnik> {

		@Override
		public Korisnik mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			int id = rs.getInt(index++);
			String korisnickoIme = rs.getString(index++);
			String lozinka = rs.getString(index++);
			String email = rs.getString(index++);
			String ime = rs.getString(index++);
			String prezime = rs.getString(index++);
			Date datumRodjenja = rs.getDate(index++);
			String adresa = rs.getString(index++);
			long brojTelefona = rs.getLong(index++);
			LocalDateTime datumRegistracije = rs.getTimestamp(index++).toLocalDateTime();
			TipKorisnika tipKorisnika = TipKorisnika.valueOf(rs.getString(index++));
			boolean blokiran = rs.getBoolean(index++);
			boolean aktivan = rs.getBoolean(index++);
			
			if(tipKorisnika == TipKorisnika.admin) {
				Admin admin = new Admin(id,korisnickoIme,lozinka,email,ime,prezime,datumRodjenja,adresa,brojTelefona,datumRegistracije,tipKorisnika,blokiran,aktivan);
				return admin;
			} else {
				Clan clan = new Clan(id,korisnickoIme,lozinka,email,ime,prezime,datumRodjenja,adresa,brojTelefona,datumRegistracije,tipKorisnika,blokiran,aktivan);
				return clan;
			}
		}
	}
	
	@Override
	public List<Korisnik> findAll() {
		String sql = "select * from korisnici where aktivan=1";
		return jdbcTemplate.query(sql, new KorisnikRowMapper());
	}

	@Override
	public int generateNewID() {
		String sql = "select * from korisnici";
		List<Korisnik> korisnici = jdbcTemplate.query(sql, new KorisnikRowMapper());
		int max = 0;
		for(Korisnik korisnik : korisnici) {
			if(korisnik.getId() > max) {
				max = korisnik.getId();
			}
		}
		return max + 1;
	}
	
	@Override
	public void save(Korisnik korisnik) {
		String sql = "insert into korisnici (id,korisnickoIme,lozinka,email,ime,prezime,datumRodjenja,adresa,brojTelefona,datumRegistracije,tipKorisnika,blokiran,aktivan) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,korisnik.getId(),korisnik.getKorisnickoIme(),korisnik.getLozinka(),korisnik.getEmail(),korisnik.getIme(),korisnik.getPrezime(), korisnik.getDatumRodjenja(),
				korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getDatumRegistracije(), korisnik.getTipKorisnika().toString(), korisnik.isBlokiran(), korisnik.isAktivan());
	}

	@Override
	public void update(Korisnik korisnik) {
		String sql = "update korisnici set korisnickoIme=?,lozinka=?,email=?,ime=?,prezime=?,datumRodjenja=?,adresa=?,brojTelefona=?,datumRegistracije=?,tipKorisnika=?,blokiran=?,aktivan=? where id=?";
		jdbcTemplate.update(sql,korisnik.getKorisnickoIme(),korisnik.getLozinka(),korisnik.getEmail(),korisnik.getIme(),korisnik.getPrezime(),korisnik.getDatumRodjenja(),korisnik.getAdresa(),
				korisnik.getBrojTelefona(),korisnik.getDatumRegistracije(),korisnik.getTipKorisnika().toString(),korisnik.isBlokiran(),korisnik.isAktivan(),korisnik.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "update korisnici set aktivan=0 where id=?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public Korisnik findOneById(int id) {
		try {
			String sql = "select * from korisnici where id=?";
			return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(),id);
		} catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public Korisnik findOneByUsernamePassowrd(String username, String passowrd) {
		try {
			String sql = "select * from korisnici where korisnickoIme=? and lozinka=? and blokiran=0 and aktivan=1";
			return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(),username,passowrd);
		} catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Korisnik> rezultatiPretrage(String korisnickoIme, String tip) {
		List<Korisnik> listaKorisnika = findAll();
		List<Korisnik> nadjeniKorisnici = new ArrayList<>();
		
        for (Korisnik korisnik : listaKorisnika)
        {
            if( ( (korisnickoIme.equals("")) || (korisnickoIme.equalsIgnoreCase(korisnik.getKorisnickoIme())) ) &&
              ( (tip.equals("")) || (tip.equalsIgnoreCase(korisnik.getTipKorisnika().toString())) )) {
            	nadjeniKorisnici.add(korisnik);
            }
        }
        
        return nadjeniKorisnici;
	}

	@Override
	public Korisnik findOneByUsername(String username) {
		try {
			String sql = "select * from korisnici where korisnickoIme=?";
			return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(),username);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

}
