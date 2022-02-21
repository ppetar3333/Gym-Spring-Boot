package com.ftn.teretana.models;

public class TipTreninga {
	
	private int id;
	private String ime;
	private String opis;
	private boolean aktivan;
	
	public TipTreninga() {}
	
	public TipTreninga(int id, String ime, String opis, boolean aktivan) {
		super();
		this.id = id;
		this.ime = ime;
		this.opis = opis;
		this.aktivan = aktivan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}


	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	@Override
	public String toString() {
		return "TipTreninga [id=" + id + ", ime=" + ime + ", opis=" + opis + ", aktivan=" + aktivan + "]" + "\n";
	}
}
