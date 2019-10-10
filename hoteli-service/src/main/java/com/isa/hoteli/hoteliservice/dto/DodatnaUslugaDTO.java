package com.isa.hoteli.hoteliservice.dto;

import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.model.Hotel;

public class DodatnaUslugaDTO {

	private Long id;	
	private String naziv;
	private float cena;
	private float popust;
    private Hotel hotel;
    
	public DodatnaUslugaDTO() {

	}
    
	public DodatnaUslugaDTO(DodatnaUsluga usluga) {
		super();
		this.id = usluga.getId();
		this.naziv = usluga.getNaziv();
		this.cena = usluga.getCena();
		this.popust = usluga.getPopust();
		this.hotel = usluga.getHotel();
	}
	
	public DodatnaUslugaDTO(Long id, String naziv, float cena, float popust, Hotel hotel) {
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
    
    
}
