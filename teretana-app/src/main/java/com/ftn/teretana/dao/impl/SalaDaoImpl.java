package com.ftn.teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.teretana.dao.SalaDao;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.ListaZelja;
import com.ftn.teretana.models.PopunjenostSale;
import com.ftn.teretana.models.Sala;

@Repository
public class SalaDaoImpl implements SalaDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class SalaRowMapper implements RowMapper<Sala> {

		@Override
		public Sala mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			int id = rs.getInt(index++);
			String oznakaSale = rs.getString(index++);
			int kapacitet = rs.getInt(index++);
			boolean popunjen = rs.getBoolean(index++);
			boolean aktivan = rs.getBoolean(index++);

			Sala sala = new Sala(id,oznakaSale,kapacitet,popunjen,aktivan);
			return sala;
		}

	}
	
	private class PopunjenostSaleRowMapper implements RowMapper<PopunjenostSale> {
		
		@Override
		public PopunjenostSale mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			String oznakaSale = rs.getString(index++);
			int kapacitet = rs.getInt(index++);
			LocalDateTime datumOdrzavanja = rs.getTimestamp(index++).toLocalDateTime();
			int brojTerminaUsali = rs.getInt(index++);
			
			PopunjenostSale popunjenostSale = new PopunjenostSale(oznakaSale, kapacitet, datumOdrzavanja, brojTerminaUsali);
			return popunjenostSale;
		}
	}
	
	@Override
	public List<Sala> findAll() {
		String sql = "select * from sala where aktivan=1";
		return jdbcTemplate.query(sql, new SalaRowMapper());
	}

	@Override
	public int generateNewID() {
		String sql = "select * from sala";
		List<Sala> sale = jdbcTemplate.query(sql, new SalaRowMapper());
		int max = 0;
		for(Sala sala : sale) {
			if(sala.getId() > max) {
				max = sala.getId();
			}
		}
		return max + 1;
	}
	
	@Override
	public void save(Sala sala) {
		String sql = "insert into sala (id,oznakaSale,kapacitet,popunjen,aktivan) values (?,?,?,?,?)";
		jdbcTemplate.update(sql,sala.getId(),sala.getOznakaSale(),sala.getKapacitet(),sala.isPopunjen(),sala.isAktivan());
	}

	@Override
	public void update(Sala sala) {
		String sql = "update sala set oznakaSale=?,kapacitet=?,popunjen=?,aktivan=? where id = ?";
		jdbcTemplate.update(sql,sala.getOznakaSale(),sala.getKapacitet(),sala.isPopunjen(),sala.isAktivan(),sala.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "update sala set aktivan = 0 where id = ?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public Sala findOneByID(int id) {
		try {
			String sql = "select * from sala where id=?";
			return jdbcTemplate.queryForObject(sql, new SalaRowMapper(),id);
		} catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Sala> rezultatiPretrage(String oznakaSale) {
		List<Sala> listaSala = findAll();
		List<Sala> nadjeneSale = new ArrayList<>();
		
        for (Sala sala : listaSala)
        {
            if ( (oznakaSale.equals(""))  || (oznakaSale.equalsIgnoreCase(sala.getOznakaSale()))) {
            	nadjeneSale.add(sala);
            }
        }
        
        return nadjeneSale;
	}

	@Override
	public List<PopunjenostSale> getPopunjenostSale() {
		String sql = "select s.oznakaSale, s.kapacitet, t.datumOdrzavanja, count(t.datumOdrzavanja) as brojClanovaZaTermin\r\n"
				+ "from sala as s,\r\n"
				+ "termintreninga as t\r\n"
				+ "where s.id = t.salaID\r\n"
				+ "group by s.oznakaSale, s.kapacitet, t.datumOdrzavanja";
		return jdbcTemplate.query(sql, new PopunjenostSaleRowMapper());
	}

}
