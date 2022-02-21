package com.ftn.teretana.models;

public class ListaZelja {
	
	private int id;
	private Clan clan;
	private Trening trening;
	private boolean aktivan;
	
	public ListaZelja(int id, Clan clan, Trening trening, boolean aktivan) {
		super();
		this.id = id;
		this.clan = clan;
		this.trening = trening;
		this.aktivan = aktivan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	@Override
	public String toString() {
		return "ListaZelja [id=" + id + ", clan=" + clan + ", trening=" + trening + ", aktivan=" + aktivan + "]" + "\n";
	}
	
	
}
