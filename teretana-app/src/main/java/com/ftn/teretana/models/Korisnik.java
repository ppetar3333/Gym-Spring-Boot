package com.ftn.teretana.models;

import java.sql.Date;
import java.time.LocalDateTime;

import com.ftn.teretana.enums.TipKorisnika;

public abstract class Korisnik {
	
	protected int id;
	protected String korisnickoIme;
	protected String lozinka;
	protected String email;
	protected String ime;
	protected String prezime;
	protected Date datumRodjenja;
	protected String adresa;
	protected long brojTelefona;
	protected LocalDateTime datumRegistracije;
	protected TipKorisnika tipKorisnika;
	protected boolean blokiran;
	protected boolean aktivan;
	
	public Korisnik() {}
	
	public Korisnik(int id, String korisnickoIme, String lozinka, String email, String ime, String prezime,
			Date datumRodjenja, String adresa, long brojTelefona, LocalDateTime datumRegistracije,
			TipKorisnika tipKorisnika, boolean blokiran, boolean aktivan) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumRegistracije = datumRegistracije;
		this.tipKorisnika = tipKorisnika;
		this.blokiran = blokiran;
		this.aktivan = aktivan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public long getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(long brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public LocalDateTime getDatumRegistracije() {
		return datumRegistracije;
	}

	public void setDatumRegistracije(LocalDateTime datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + ", email=" + email
				+ ", ime=" + ime + ", prezime=" + prezime + ", datumRodjenja=" + datumRodjenja + ", adresa=" + adresa
				+ ", brojTelefona=" + brojTelefona + ", datumRegistracije=" + datumRegistracije + ", tipKorisnika="
				+ tipKorisnika + ", blokiran=" + blokiran + ", aktivan=" + aktivan + "]" + "\n";
	}
}
