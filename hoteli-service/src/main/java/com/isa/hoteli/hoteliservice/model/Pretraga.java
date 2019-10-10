package com.isa.hoteli.hoteliservice.model;

import java.sql.Date;

public class Pretraga {

	private String nazivAdresa;
	private Date datumOd;
	private Date datumDo;
	private int brojGostiju;
	private int brojSoba;
	
	public Pretraga() {

	}
	
	public Pretraga(String nazivAdresa, Date datumOd, Date datumDo, int brojGostiju, int brojSoba) {
		super();
		this.nazivAdresa = nazivAdresa;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.brojGostiju = brojGostiju;
		this.brojSoba = brojSoba;
	}
	
	public String getNazivAdresa() {
		return nazivAdresa;
	}
	public void setNazivAdresa(String nazivAdresa) {
		this.nazivAdresa = nazivAdresa;
	}
	public Date getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}
	public Date getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}
	public int getBrojGostiju() {
		return brojGostiju;
	}
	public void setBrojGostiju(int brojGostiju) {
		this.brojGostiju = brojGostiju;
	}
	public int getBrojSoba() {
		return brojSoba;
	}
	public void setBrojSoba(int brojSoba) {
		this.brojSoba = brojSoba;
	}
	
	
	
}
