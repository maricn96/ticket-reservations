package com.isa.hoteli.hoteliservice.model;

import java.sql.Date;

public class PretragaSoba {

	private Date datumOd;
	private Date datumDo;
	private float cenaOd;
	private float cenaDo;
	private TipSobe tipSobe;
	
	public PretragaSoba() {

	}
	
	public PretragaSoba(Date datumOd, Date datumDo, float cenaOd, float cenaDo, TipSobe tipSobe) {
		super();
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.cenaOd = cenaOd;
		this.cenaDo = cenaDo;
		this.tipSobe = tipSobe;
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
	public float getCenaOd() {
		return cenaOd;
	}
	public void setCenaOd(float cenaOd) {
		this.cenaOd = cenaOd;
	}
	public float getCenaDo() {
		return cenaDo;
	}
	public void setCenaDo(float cenaDo) {
		this.cenaDo = cenaDo;
	}
	public TipSobe getTipSobe() {
		return tipSobe;
	}
	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}
	
	
	
}
