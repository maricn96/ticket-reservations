package com.isa.hoteli.hoteliservice.dto;

import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;

public class CenovnikDTO {

	private Long id;	
	private float cena;
    private Hotel hotel;
    private HotelskaSoba hotelskaSoba;
    
	public CenovnikDTO() {

	}
	
	public CenovnikDTO(Long id, float cena, Hotel hotel, HotelskaSoba hotelskaSoba) {
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
