package com.ftn.teretana.models;

import java.time.LocalDateTime;

public class PopunjenostSale {

	private String oznakaSale;
	private int kapacitet;
	private LocalDateTime datumOdrzavanja;
	private int popunjenost;
	
	public PopunjenostSale(String oznakaSale, int kapacitet, LocalDateTime datumOdrzavanja, int popunjenost) {
		super();
		this.oznakaSale = oznakaSale;
		this.kapacitet = kapacitet;
		this.datumOdrzavanja = datumOdrzavanja;
		this.popunjenost = popunjenost;
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

	public int getPopunjenost() {
		return popunjenost;
	}

	public void setPopunjenost(int popunjenost) {
		this.popunjenost = popunjenost;
	}
	

	public LocalDateTime getDatumOdrzavanja() {
		return datumOdrzavanja;
	}

	public void setDatumOdrzavanja(LocalDateTime datumOdrzavanja) {
		this.datumOdrzavanja = datumOdrzavanja;
	}

	@Override
	public String toString() {
		return "PopunjenostSale [oznakaSale=" + oznakaSale + ", kapacitet=" + kapacitet + ", datumOdrzavanja="
				+ datumOdrzavanja + ", popunjenost=" + popunjenost + "]";
	}

}
