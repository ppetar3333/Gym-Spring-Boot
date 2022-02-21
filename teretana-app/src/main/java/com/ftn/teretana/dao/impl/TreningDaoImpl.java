package com.ftn.teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.ftn.teretana.dao.TreningDao;
import com.ftn.teretana.enums.NivoTreninga;
import com.ftn.teretana.enums.VrstaTreninga;
import com.ftn.teretana.models.TipTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.TipTreningaService;

@Repository
public class TreningDaoImpl implements TreningDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TipTreningaService tipTreningaService;
	
	private class TreningRowMapper implements RowMapper<Trening> {
		
		private List<TipTreninga> tipTreningaList = tipTreningaService.findAll();
		
		@Override
		public Trening mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			int id = rs.getInt(index++);
			String naziv = rs.getString(index++);
			String trener = rs.getString(index++);
			String opis = rs.getString(index++);
			int tipTreningaID = rs.getInt(index++);
			TipTreninga tipTreninga = new TipTreninga();
			for(TipTreninga tipoviTreninga : tipTreningaList) {
				if(tipTreningaID == tipoviTreninga.getId()) {
					tipTreninga = tipoviTreninga;
				}
			}
			double cena = rs.getDouble(index++);
			VrstaTreninga vrstaTreninga = VrstaTreninga.valueOf(rs.getString(index++));
			NivoTreninga nivoTreninga = NivoTreninga.valueOf(rs.getString(index++));
			String slika = rs.getString(index++);
			int trajanjeTreninga = rs.getInt(index++);
			double prosecnaOcena = rs.getDouble(index++);
			boolean aktivan = rs.getBoolean(index++);
			
			Trening trening = new Trening(id,naziv,trener,opis,tipTreninga,cena,vrstaTreninga,nivoTreninga,slika,trajanjeTreninga,prosecnaOcena,aktivan);
			return trening;
		}
	}
	
	@Override
	public List<Trening> findAll() {
		String sql = "select * from trening where aktivan=1";
		return jdbcTemplate.query(sql, new TreningRowMapper());
	}

	@Override
	public int generateNewID() {
		String sql = "select * from trening";
		List<Trening> sviTreninzi = jdbcTemplate.query(sql, new TreningRowMapper());
		int max = 0;
		for(Trening trening : sviTreninzi) {
			if(trening.getId() > max) {
				max = trening.getId();
			}
		}
		return max + 1;
	}
	
	@Override
	public void save(Trening trening) {
		String sql = "insert into trening (id,naziv,trener,opis,tipTreningaID,cena,vrstaTreninga,nivoTreninga,slika,trajanjeTreninga,prosecnaOcena,aktivan) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,trening.getId(),trening.getNaziv(),trening.getTrener(),trening.getOpis(),trening.getTipTreninga().getId(),trening.getCena(),trening.getVrstaTreninga().toString(),trening.getNivoTreninga().toString(),
				trening.getSlika(),trening.getTrajanjeTreninga(),trening.getProsecnaOcena(),trening.isAktivan());
	}

	@Override
	public void update(Trening trening) {
		String sql = "update trening set naziv=?,trener=?,opis=?,tipTreningaID=?,cena=?,vrstaTreninga=?,nivoTreninga=?,slika=?,trajanjeTreninga=?,prosecnaOcena=?,aktivan=? where id = ?";
		jdbcTemplate.update(sql,trening.getNaziv(),trening.getTrener(),trening.getOpis(),trening.getTipTreninga().getId(),trening.getCena(),trening.getVrstaTreninga().toString(),trening.getNivoTreninga().toString(),
				trening.getSlika(),trening.getTrajanjeTreninga(),trening.getProsecnaOcena(),trening.isAktivan(),trening.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "update trening set aktivan = 0 where id = ?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public Trening findOneByID(int id) {
		try {
			String sql = "select * from trening where id=?";
			return jdbcTemplate.queryForObject(sql, new TreningRowMapper(),id);
		} catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Trening> rezultatiPretrage(String naziv, String tipTreninga, String cenaOd, String cenaDo,
			String trener, String vrsta, String nivo, String sortiraj) {
		List<Trening> listaTreninga = findAll();
		List<Trening> nadjeniTreninzi = new ArrayList<>();
		ValueRange range = ValueRange.of(0L, 1000000L);
		
		if(!cenaOd.equals("") && !cenaDo.equals("")) {
			range = ValueRange.of(Long.parseLong(cenaOd), Long.parseLong(cenaDo));
		} else if(!cenaOd.equals("") && cenaDo.equals("")) {
			// znaci da je uneo od, a do nije uneo,  cenaOd, 100000L
			range = ValueRange.of(Long.parseLong(cenaOd), 100000L);
		} else if(cenaOd.equals("") && !cenaDo.equals("")) {
			// znaci da od nije uneo ali je uneo do,  0L,cenaDo
			range = ValueRange.of(0L, Long.parseLong(cenaDo));
		}
		
        for (Trening trening : listaTreninga)
        {
            if( ( (naziv.equals("")) || (naziv.equalsIgnoreCase(trening.getNaziv())) ) &&
            		( (tipTreninga.equals("")) || (tipTreninga.equalsIgnoreCase(trening.getTipTreninga().getIme())) ) &&
            		( (trener.equals("")) || (trener.equalsIgnoreCase(trening.getTrener())) ) &&
            		( (vrsta.equals("")) || (vrsta.equalsIgnoreCase(trening.getVrstaTreninga().toString())) ) &&
            		( (nivo.equals("")) || (nivo.equalsIgnoreCase(trening.getNivoTreninga().toString())) ) &&
            		( range.isValidIntValue((long) trening.getCena())  )) {
            	nadjeniTreninzi.add(trening);
            }
        }
        return nadjeniTreninzi;
	}

	@Override
	public List<Trening> treninziSaBrojemTermina() {
		String sql = "select t.id, t.naziv, t.trener, t.opis, t.tipTreningaID, t.cena, t.vrstaTreninga, t.nivoTreninga, t.slika, count(te.id) as trajanjeTreninga, t.prosecnaOcena, t.aktivan\r\n"
				+ "from trening as t,\r\n"
				+ "termintreninga as te\r\n"
				+ "where t.id = te.treningID and t.aktivan = true and te.aktivan = true\r\n"
				+ "group by t.naziv, t.trener";
		return jdbcTemplate.query(sql, new TreningRowMapper());
	}

}
