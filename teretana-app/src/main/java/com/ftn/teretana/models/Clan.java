package com.ftn.teretana.models;

import java.sql.Date;
import java.time.LocalDateTime;

import com.ftn.teretana.enums.TipKorisnika;

public class Clan extends Korisnik{

	public Clan() {}
	
	public Clan(int id, String korisnickoIme, String lozinka, String email, String ime, String prezime,
			Date datumRodjenja, String adresa, long brojTelefona, LocalDateTime datumRegistracije,
			TipKorisnika tipKorisnika, boolean blokiran, boolean aktivan) {
		super(id, korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije,
				tipKorisnika, blokiran, aktivan);
	}
}
