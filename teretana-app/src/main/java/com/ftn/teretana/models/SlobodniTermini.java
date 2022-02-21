package com.ftn.teretana.models;

import java.time.LocalDateTime;

public class SlobodniTermini {
	
	private int terminID;
	private String oznakaSale;
	private int kapacitet;
	private LocalDateTime datumOdrzavanja;
	private String nazivTreninga;
	private int brojClanovaZaTermin;
	
	public SlobodniTermini(int terminID, String oznakaSale, int kapacitet, LocalDateTime datumOdrzavanja,
			String nazivTreninga, int brojClanovaZaTermin) {
		super();
		this.terminID = terminID;
		this.oznakaSale = oznakaSale;
		this.kapacitet = kapacitet;
		this.datumOdrzavanja = datumOdrzavanja;
		this.nazivTreninga = nazivTreninga;
		this.brojClanovaZaTermin = brojClanovaZaTermin;
	}

	public String getOznakaSale() {
		return oznakaSale;
	}

	public void setOznakaSale(String oznakaSale) {
		this.oznakaSale = oznakaSale;
	}

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	public LocalDateTime getDatumOdrzavanja() {
		return datumOdrzavanja;
	}

	public void setDatumOdrzavanja(LocalDateTime datumOdrzavanja) {
		this.datumOdrzavanja = datumOdrzavanja;
	}

	public String getNazivTreninga() {
		return nazivTreninga;
	}

	public void setNazivTreninga(String nazivTreninga) {
		this.nazivTreninga = nazivTreninga;
	}

	public int getBrojClanovaZaTermin() {
		return brojClanovaZaTermin;
	}

	public void setBrojClanovaZaTermin(int brojClanovaZaTermin) {
		this.brojClanovaZaTermin = brojClanovaZaTermin;
	}
	
	public int getTerminID() {
		return terminID;
	}

	public void setTerminID(int terminID) {
		this.terminID = terminID;
	}

	@Override
	public String toString() {
		return "SlobodniTermini [terminID=" + terminID + ", oznakaSale=" + oznakaSale + ", kapacitet=" + kapacitet
				+ ", datumOdrzavanja=" + datumOdrzavanja + ", nazivTreninga=" + nazivTreninga + ", brojClanovaZaTermin="
				+ brojClanovaZaTermin + "]";
	}
}
