package com.isa.hoteli.hoteliservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cenovnik {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private float cena;
	
	@ManyToOne
    @JoinColumn(name="hotel_id", nullable=false)
    private Hotel hotel;
	
	@ManyToOne
    @JoinColumn(name="hotelskaSoba_id", nullable=false)
    private HotelskaSoba hotelskaSoba;
	
	public Cenovnik() {

	}

	public Cenovnik(Long id, float cena, Hotel hotel, HotelskaSoba hotelskaSoba) {
		super();
		this.id = id;
		this.cena = cena;
		this.hotel = hotel;
		this.hotelskaSoba = hotelskaSoba;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getCena() {
		return cena;
	}

	public void setCena(float cena) {
		this.cena = cena;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public HotelskaSoba getHotelskaSoba() {
		return hotelskaSoba;
	}

	public void setHotelskaSoba(HotelskaSoba hotelskaSoba) {
		this.hotelskaSoba = hotelskaSoba;
	}
	
	
	

}
