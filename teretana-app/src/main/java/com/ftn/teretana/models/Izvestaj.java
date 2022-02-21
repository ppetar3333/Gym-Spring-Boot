package com.ftn.teretana.models;

public class Izvestaj {

	private int id;
	private String naziv;
	private String trener;
	private int brojuZakazanihTermina;
	private double cena;
	
	public Izvestaj() {}
	
	public Izvestaj(int id, String naziv, String trener, int brojuZakazanihTermina, double cena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.trener = trener;
		this.brojuZakazanihTermina = brojuZakazanihTermina;
		this.cena = cena;
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

	public int getBrojuZakazanihTermina() {
		return brojuZakazanihTermina;
	}

	public void setBrojuZakazanihTermina(int brojuZakazanihTermina) {
		this.brojuZakazanihTermina = brojuZakazanihTermina;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	@Override
	public String toString() {
		return "Izvestaj [id=" + id + ", naziv=" + naziv + ", trener=" + trener + ", brojuZakazanihTermina="
				+ brojuZakazanihTermina + ", cena=" + cena + "]" + "\n";
	}
	
}
