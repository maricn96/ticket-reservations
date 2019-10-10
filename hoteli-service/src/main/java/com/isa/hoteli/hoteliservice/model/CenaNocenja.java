package com.isa.hoteli.hoteliservice.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;

/*{
    "cenaNocenja": 30,
    "datumOd": "2005-02-02",
    "datumDo": "2005-03-03",
    "hotelskaSoba": {
        "id": 2
    }
}*/

@Entity
public class CenaNocenja {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private float cenaNocenja;
	private Date datumOd;
	private Date datumDo;
	
	@ManyToOne
    @JoinColumn(name="hotelskaSoba_id", nullable=false)
    private HotelskaSoba hotelskaSoba;

	public CenaNocenja() {

	}
	
	public CenaNocenja(CenaNocenjaDTO cena) {
		super();
		this.id = cena.getId();
		this.cenaNocenja = cena.getCenaNocenja();
		this.datumOd = cena.getDatumOd();
		this.datumDo = cena.getDatumDo();
		this.hotelskaSoba = cena.getHotelskaSoba();
	}
	
	public CenaNocenja(Long id, float cenaNocenja, Date datumOd, Date datumDo, HotelskaSoba hotelskaSoba) {
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
