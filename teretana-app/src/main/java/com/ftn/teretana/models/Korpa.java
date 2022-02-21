package com.ftn.teretana.models;

import java.time.LocalDateTime;

public class Korpa {
	
	private int id;
	private String nazivTreninga;
	private String trener;
	private TipTreninga tipTreninga;
	private LocalDateTime datumOdrzavanja;
	private double cena;
	private boolean aktivan;
	
	public Korpa(int id, String nazivTreninga, String trener, TipTreninga tipTreninga, LocalDateTime datumOdrzavanja,
			double cena, boolean aktivan) {
		super();
		this.id = id;
		this.nazivTreninga = nazivTreninga;
		this.trener = trener;
		this.tipTreninga = tipTreninga;
		this.datumOdrzavanja = datumOdrzavanja;
		this.cena = cena;
		this.aktivan = aktivan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazivTreninga() {
		return nazivTreninga;
	}

	public void setNazivTreninga(String nazivTreninga) {
		this.nazivTreninga = nazivTreninga;
	}

	public String getTrener() {
		return trener;
	}

	public void setTrener(String trener) {
		this.trener = trener;
	}

	public TipTreninga getTipTreninga() {
		return tipTreninga;
	}

	public void setTipTreninga(TipTreninga tipTreninga) {
		this.tipTreninga = tipTreninga;
	}

	public LocalDateTime getDatumOdrzavanja() {
		return datumOdrzavanja;
	}

	public void setDatumOdrzavanja(LocalDateTime datumOdrzavanja) {
		this.datumOdrzavanja = datumOdrzavanja;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	@Override
	public String toString() {
		return "Korpa [id=" + id + ", nazivTreninga=" + nazivTreninga + ", trener=" + trener + ", tipTreninga="
				+ tipTreninga + ", datumOdrzavanja=" + datumOdrzavanja + ", cena=" + cena + ", aktivan=" + aktivan
				+ "]" + "\n";
	}
		
}
