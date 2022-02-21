package com.ftn.teretana.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.teretana.dao.TreningTipoviListaDao;
import com.ftn.teretana.models.Clan;
import com.ftn.teretana.models.ClanskaKarta;
import com.ftn.teretana.models.Korisnik;
import com.ftn.teretana.models.TipTreninga;
import com.ftn.teretana.models.Trening;
import com.ftn.teretana.models.TreningTipoviLista;
import com.ftn.teretana.service.TipTreningaService;
import com.ftn.teretana.service.TreningService;

@Repository
public class TreningTipoviListaDaoImpl implements TreningTipoviListaDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TreningService treningService;
	
	@Autowired
	private TipTreningaService tipTreningaService;
	
	
	private class TreningTipoviListaRowMapper implements RowMapper<TreningTipoviLista> {

		private List<Trening> listaTreninga = treningService.findAll();
		
		private List<TipTreninga> listaTipovaTreninga = tipTreningaService.findAll();
		
		@Override
		public TreningTipoviLista mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			int id = rs.getInt(index++);
			int treningID = rs.getInt(index++);
			Trening trening = new Trening();
			for(Trening tren : listaTreninga) {
				if(treningID == tren.getId()) {
					trening = tren;
				}
			}
			int tipTreningaID =rs.getInt(index++);
			TipTreninga tipTreninga = new TipTreninga();
			for(TipTreninga tip : listaTipovaTreninga) {
				if(tipTreningaID == tip.getId()) {
					tipTreninga = tip;
				}
			}
			boolean aktivan = rs.getBoolean(index++);

			TreningTipoviLista treningTipoviLista = new TreningTipoviLista(id,tipTreninga,trening,aktivan);
			return treningTipoviLista;
		}

	}
	
	@Override
	public List<TreningTipoviLista> findAll() {
		String sql = "select * from treningtipovilista where aktivan=1";
		return jdbcTemplate.query(sql, new TreningTipoviListaRowMapper());
	}

	@Override
	public void save(TreningTipoviLista treningTipoviLista) {
		String sql = "insert into treningtipovilista (id,treningID,tipTreningaID,aktivan) values (?,?,?,?)";
		jdbcTemplate.update(sql,treningTipoviLista.getId(),treningTipoviLista.getTrening().getId(),treningTipoviLista.getTipTreninga().getId(),treningTipoviLista.isAktivan());
	}

	@Override
	public void update(TreningTipoviLista treningTipoviLista) {
		String sql = "update treningtipovilista set treningID=?,tipTreningaID=?,aktivan=? where id=?";
		jdbcTemplate.update(sql,treningTipoviLista.getTrening().getId(),treningTipoviLista.getTipTreninga().getId(),treningTipoviLista.isAktivan(),treningTipoviLista.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "update treningtipovilista set aktivan=0 where id=?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public TreningTipoviLista findOneById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int generateNewID() {
		String sql = "select * from treningtipovilista";
		List<TreningTipoviLista> tipLista = jdbcTemplate.query(sql, new TreningTipoviListaRowMapper());
		int max = 0;
		for(TreningTipoviLista tip : tipLista) {
			if(tip.getId() > max) {
				max = tip.getId();
			}
		}
		return max + 1;
	}

}
