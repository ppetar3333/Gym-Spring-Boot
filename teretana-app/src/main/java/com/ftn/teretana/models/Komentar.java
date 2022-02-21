package com.ftn.teretana.models;

import java.time.LocalDateTime;

import com.ftn.teretana.enums.StatusKomentara;

public class Komentar {

	private int id;
	private String tekstKomentara;
	private double ocena;
	private LocalDateTime datumPostavljanja;
	private Clan clan;
	private Trening trening;
	private StatusKomentara statusKomentara;
	private boolean anoniman;
	private boolean aktivan;
	
	public Komentar(int id, String tekstKomentara, double ocena, LocalDateTime datumPostavljanja, Clan clan,
			Trening trening, StatusKomentara statusKomentara, boolean anoniman, boolean aktivan) {
		super();
		this.id = id;
		this.tekstKomentara = tekstKomentara;
		this.ocena = ocena;
		this.datumPostavljanja = datumPostavljanja;
		this.clan = clan;
		this.trening = trening;
		this.statusKomentara = statusKomentara;
		this.anoniman = anoniman;
		this.aktivan = aktivan;
	}
	
	public Komentar() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTekstKomentara() {
		return tekstKomentara;
	}

	public void setTekstKomentara(String tekstKomentara) {
		this.tekstKomentara = tekstKomentara;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

	public LocalDateTime getDatumPostavljanja() {
		return datumPostavljanja;
	}

	public void setDatumPostavljanja(LocalDateTime datumPostavljanja) {
		this.datumPostavljanja = datumPostavljanja;
	}

	public Clan getClan() {
		return clan;
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}

	public Trening getTrening() {
		return trening;
	}

	public void setTrening(Trening trening) {
		this.trening = trening;
	}

	public StatusKomentara getStatusKomentara() {
		return statusKomentara;
	}

	public void setStatusKomentara(StatusKomentara statusKomentara) {
		this.statusKomentara = statusKomentara;
	}

	public boolean isAnoniman() {
		return anoniman;
	}

	public void setAnoniman(boolean anoniman) {
		this.anoniman = anoniman;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	@Override
	public String toString() {
		return "Komentar [id=" + id + ", tekstKomentara=" + tekstKomentara + ", ocena=" + ocena + ", datumPostavljanja="
				+ datumPostavljanja + ", clan=" + clan + ", trening=" + trening + ", statusKomentara=" + statusKomentara
				+ ", anoniman=" + anoniman + ", aktivan=" + aktivan + "]";
	}
}
