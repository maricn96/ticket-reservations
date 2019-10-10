package com.isa.hoteli.hoteliservice.dto;

import java.sql.Date;

import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;

public class CenaNocenjaDTO {

	private Long id;
	private float cenaNocenja;
	private Date datumOd;
	private Date datumDo;
    private HotelskaSoba hotelskaSoba;
    
	public CenaNocenjaDTO() {

	}
	
	public CenaNocenjaDTO(CenaNocenja cena) {
		super();
		this.id = cena.getId();
		this.cenaNocenja = cena.getCenaNocenja();
		this.datumOd = cena.getDatumOd();
		this.datumDo = cena.getDatumDo();
		this.hotelskaSoba = cena.getHotelskaSoba();
	}
    
	public CenaNocenjaDTO(Long id, float cenaNocenja, Date datumOd, Date datumDo, HotelskaSoba hotelskaSoba) {
		super();
		this.id = id;
		this.cenaNocenja = cenaNocenja;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.hotelskaSoba = hotelskaSoba;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public float getCenaNocenja() {
		return cenaNocenja;
	}
	public void setCenaNocenja(float cenaNocenja) {
		this.cenaNocenja = cenaNocenja;
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
	public HotelskaSoba getHotelskaSoba() {
		return hotelskaSoba;
	}
	public void setHotelskaSoba(HotelskaSoba hotelskaSoba) {
		this.hotelskaSoba = hotelskaSoba;
	}

}
