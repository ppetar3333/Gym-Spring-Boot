package com.ftn.teretana.models;

import java.beans.Transient;

import com.ftn.teretana.enums.NivoTreninga;
import com.ftn.teretana.enums.VrstaTreninga;

public class Trening {
	
	private int id;
	private String naziv;
	private String trener;
	private String opis;
	private TipTreninga tipTreninga;
	private double cena;
	private VrstaTreninga vrstaTreninga;
	private NivoTreninga nivoTreninga;
	private String slika;
	private int trajanjeTreninga;
	private double prosecnaOcena;
	private boolean aktivan;
	
	public Trening() {}
	
	public Trening(int id, String naziv, String trener, String opis, TipTreninga tipTreninga, double cena,
			VrstaTreninga vrstaTreninga, NivoTreninga nivoTreninga, String slika, int trajanjeTreninga,
			double prosecnaOcena, boolean aktivan) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.trener = trener;
		this.opis = opis;
		this.tipTreninga = tipTreninga;
		this.cena = cena;
		this.vrstaTreninga = vrstaTreninga;
		this.nivoTreninga = nivoTreninga;
		this.slika = slika;
		this.trajanjeTreninga = trajanjeTreninga;
		this.prosecnaOcena = prosecnaOcena;
		this.aktivan = aktivan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getTrener() {
		return trener;
	}

	public void setTrener(String trener) {
		this.trener = trener;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public TipTreninga getTipTreninga() {
		return tipTreninga;
	}

	public void setTipTreninga(TipTreninga tipTreninga) {
		this.tipTreninga = tipTreninga;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public VrstaTreninga getVrstaTreninga() {
		return vrstaTreninga;
	}

	public void setVrstaTreninga(VrstaTreninga vrstaTreninga) {
		this.vrstaTreninga = vrstaTreninga;
	}

	public NivoTreninga getNivoTreninga() {
		return nivoTreninga;
	}

	public void setNivoTreninga(NivoTreninga nivoTreninga) {
		this.nivoTreninga = nivoTreninga;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}
	
	@Transient
	public String getSlikaPath() {
		if(slika == null) return null;
		
		return "/treninzi-slike/" + slika;
	}

	public int getTrajanjeTreninga() {
		return trajanjeTreninga;
	}

	public void setTrajanjeTreninga(int trajanjeTreninga) {
		this.trajanjeTreninga = trajanjeTreninga;
	}

	public double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	@Override
	public String toString() {
		return "Trening [id=" + id + ", naziv=" + naziv + ", trener=" + trener + ", opis=" + opis + ", tipTreninga="
				+ tipTreninga + ", cena=" + cena + ", vrstaTreninga=" + vrstaTreninga + ", nivoTreninga=" + nivoTreninga
				+ ", slika=" + slika + ", trajanjeTreninga=" + trajanjeTreninga + ", prosecnaOcena=" + prosecnaOcena
				+ ", aktivan=" + aktivan + "]" + "\n";
	}
}
