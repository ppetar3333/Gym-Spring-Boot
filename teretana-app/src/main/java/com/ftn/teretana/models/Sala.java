package com.ftn.teretana.models;

public class Sala {
	
	private int id;
	private String oznakaSale;
	private int kapacitet;
	private boolean popunjen;
	private boolean aktivan;
	
	public Sala() {}
	
	public Sala(int id, String oznakaSale, int kapacitet, boolean popunjen, boolean aktivan) {
		super();
		this.id = id;
		this.oznakaSale = oznakaSale;
		this.kapacitet = kapacitet;
		this.popunjen = popunjen;
		this.aktivan = aktivan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isPopunjen() {
		return popunjen;
	}

	public void setPopunjen(boolean popunjen) {
		this.popunjen = popunjen;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	@Override
	public String toString() {
		return "Sala [id=" + id + ", oznakaSale=" + oznakaSale + ", kapacitet=" + kapacitet + ", popunjen=" + popunjen
				+ ", aktivan=" + aktivan + "]" + "\n";
	}
}
