package com.ftn.teretana.models;

import java.time.LocalDateTime;

public class SpecijalniDatum {
	
	private int id;
	private LocalDateTime datum;
	private int popust;
	private Trening trening;
	private boolean aktivan;
	
	public SpecijalniDatum(int id, LocalDateTime datum, int popust, Trening trening,boolean aktivan) {
		super();
		this.id = id;
		this.datum = datum;
		this.popust = popust;
		this.trening = trening;
		this.aktivan = aktivan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDatum() {
		return datum;
	}

	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}

	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}


	public Trening getTrening() {
		return trening;
	}

	public void setTrening(Trening trening) {
		this.trening = trening;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	@Override
	public String toString() {
		return "SpecijalniDatum [id=" + id + ", datum=" + datum + ", popust=" + popust + ", trening=" + trening
				+ ", aktivan=" + aktivan + "]";
	}

}
