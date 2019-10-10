package com.isa.hoteli.hoteliservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.isa.hoteli.hoteliservice.dto.DodatnaUslugaDTO;

/*
{
    "naziv": "r",
    "cena": 20,
    "popust": 2,
    "hotel": {
        "id": 1
    }
}*/

@Entity
public class DodatnaUsluga {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String naziv;
	private float cena;
	private float popust;
	
	@ManyToOne
    @JoinColumn(name="hotel_id", nullable=false)
    private Hotel hotel;
	
	@Version
	private Long version = 0l;

	public DodatnaUsluga() {
	}
	
	public DodatnaUsluga(DodatnaUslugaDTO usluga) {
		super();
		this.id = usluga.getId();
		this.naziv = usluga.getNaziv();
		this.cena = usluga.getCena();
		this.popust = usluga.getPopust();
		this.hotel = usluga.getHotel();
	}
	
	public DodatnaUsluga(Long id, String naziv, float cena, float popust, Hotel hotel) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.cena = cena;
		this.popust = popust;
		this.hotel = hotel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public float getCena() {
		return cena;
	}

	public void setCena(float cena) {
		this.cena = cena;
	}

	public float getPopust() {
		return popust;
	}

	public void setPopust(float popust) {
		this.popust = popust;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	

}
