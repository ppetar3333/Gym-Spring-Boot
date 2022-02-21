package com.ftn.teretana.models;

public class TreningTipoviLista {

	private int id;
	private TipTreninga tipTreninga;
	private Trening trening;
	private boolean aktivan;
	
	public TreningTipoviLista(int id, TipTreninga tipTreninga, Trening trening, boolean aktivan) {
		super();
		this.id = id;
		this.tipTreninga = tipTreninga;
		this.trening = trening;
		this.aktivan = aktivan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipTreninga getTipTreninga() {
		return tipTreninga;
	}

	public void setTipTreninga(TipTreninga tipTreninga) {
		this.tipTreninga = tipTreninga;
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
		return "TreningTipoviLista [id=" + id + ", tipTreninga=" + tipTreninga + ", trening=" + trening + ", aktivan="
				+ aktivan + "]" + "\n";
	}
}
