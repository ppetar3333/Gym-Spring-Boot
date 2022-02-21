package com.ftn.teretana.models;

public class ClanskaKarta {
	
	private int id;
	private int popust;
	private int brojPoena;
	private Clan clan;
	private boolean prihvacen;
	private boolean iskoriscena;
	private boolean aktivan;
	
	public ClanskaKarta(int id, int popust, int brojPoena, Clan clan, boolean prihvacen, boolean iskoriscena,
			boolean aktivan) {
		super();
		this.id = id;
		this.popust = popust;
		this.brojPoena = brojPoena;
		this.clan = clan;
		this.prihvacen = prihvacen;
		this.iskoriscena = iskoriscena;
		this.aktivan = aktivan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	public int getBrojPoena() {
		return brojPoena;
	}

	public void setBrojPoena(int brojPoena) {
		this.brojPoena = brojPoena;
	}

	public Clan getClan() {
		return clan;
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}

	public boolean isPrihvacen() {
		return prihvacen;
	}

	public void setPrihvacen(boolean prihvacen) {
		this.prihvacen = prihvacen;
	}

	public boolean isIskoriscena() {
		return iskoriscena;
	}

	public void setIskoriscena(boolean iskoriscena) {
		this.iskoriscena = iskoriscena;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	@Override
	public String toString() {
		return "ClanskaKarta [id=" + id + ", popust=" + popust + ", brojPoena=" + brojPoena + ", clan=" + clan
				+ ", prihvacen=" + prihvacen + ", iskoriscena=" + iskoriscena + ", aktivan=" + aktivan + "]" + "\n";
	}
}
