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

import com.ftn.teretana.dao.SpecijalniDatumDao;
import com.ftn.teretana.models.Sala;
import com.ftn.teretana.models.SpecijalniDatum;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.service.TreningService;

@Repository
public class SpecijalniDatumDaoImpl implements SpecijalniDatumDao{

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TreningService treningService;
	
	private class SpecijalniDatumRowMapper implements RowMapper<SpecijalniDatum> {
		
		private List<Trening> listaTreninga = treningService.findAll();
		
		@Override
		public SpecijalniDatum mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			int id = rs.getInt(index++);
			LocalDateTime datum = rs.getTimestamp(index++).toLocalDateTime();
			int popust = rs.getInt(index++);
			int treningID = rs.getInt(index++);
			Trening trening = new Trening();
			for(Trening tren : listaTreninga) {
				if(treningID == tren.getId()) {
					trening = tren;
				}
			}
			boolean aktivan = rs.getBoolean(index++);
			
			SpecijalniDatum specijalniDatum = new SpecijalniDatum(id,datum,popust,trening,aktivan);
			return specijalniDatum;
		}
	}
	
	@Override
	public List<SpecijalniDatum> findAll() {
		String sql = "select * from specijalnidatum where aktivan=1";
		return jdbcTemplate.query(sql, new SpecijalniDatumRowMapper());
	}
	
	@Override
	public int generateNewID() {
		String sql = "select * from specijalnidatum";
		List<SpecijalniDatum> specDatumi = jdbcTemplate.query(sql, new SpecijalniDatumRowMapper());
		int max = 0;
		for(SpecijalniDatum datum : specDatumi) {
			if(datum.getId() > max) {
				max = datum.getId();
			}
		}
		return max + 1;
	}

	@Override
	public void save(SpecijalniDatum specijalniDatum) {
		String sql = "insert into specijalnidatum (id,datum,popust,treningID,aktivan) values (?,?,?,?,?)";
		if(specijalniDatum.getTrening() == null) {
			jdbcTemplate.update(sql,specijalniDatum.getId(),specijalniDatum.getDatum(),specijalniDatum.getPopust(),null,specijalniDatum.isAktivan());			
		} else {
			jdbcTemplate.update(sql,specijalniDatum.getId(),specijalniDatum.getDatum(),specijalniDatum.getPopust(),specijalniDatum.getTrening().getId(),specijalniDatum.isAktivan());
		}
	}

	@Override
	public void update(SpecijalniDatum specijalniDatum) {
		String sql = "update specijalnidatum set datum=?,popust=?,treningID=?,aktivan=? where id = ?";
		jdbcTemplate.update(sql,specijalniDatum.getDatum(),specijalniDatum.getPopust(),specijalniDatum.getTrening().getId(),specijalniDatum.isAktivan(),specijalniDatum.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "update specijalnidatum set aktivan = 0 where id = ?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public SpecijalniDatum findOneByID(int id) {
		try {
			String sql = "select * from specijalnidatum where id=?";
			return jdbcTemplate.queryForObject(sql, new SpecijalniDatumRowMapper(),id);
		} catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<SpecijalniDatum> getSpecijalniDatumDanas() {
		String sql = "select spec.id, spec.datum, spec.popust, spec.treningID, spec.aktivan\r\n"
				+ "from specijalnidatum as spec,\r\n"
				+ "termintreninga as termin\r\n"
				+ "where (spec.treningID = termin.treningID)\r\n"
				+ "and date_format(spec.datum, '%Y-%m-%d') = date_format(termin.datumOdrzavanja, '%Y-%m-%d')\r\n"
				+ "UNION\r\n"
				+ "select spec.id, spec.datum, spec.popust, spec.treningID, spec.aktivan\r\n"
				+ "from specijalnidatum as spec,\r\n"
				+ "termintreninga as termin\r\n"
				+ "where date_format(spec.datum, '%Y-%m-%d') = date_format(termin.datumOdrzavanja, '%Y-%m-%d');";
		return jdbcTemplate.query(sql, new SpecijalniDatumRowMapper());
	}

}
