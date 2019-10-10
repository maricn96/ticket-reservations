package com.isa.hoteli.hoteliservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.TipSobe;

public class TipSobeDTO {

	private Long id;
	private String naziv;
    private Hotel hotel;
    private List<HotelskaSoba> hotelskaSobaList;
	
	public TipSobeDTO() {

	}
	
	public TipSobeDTO(TipSobe ts) {
		super();
		this.id = ts.getId();
		this.naziv = ts.getNaziv();
		this.hotel = ts.getHotel();
		//this.hotelskaSobaList = ts.getHotelskaSobaList();
	}
	
	public TipSobeDTO(Long id, String naziv, Hotel hotel/*, List<HotelskaSoba> hotelskaSobaList*/) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.hotel = hotel;
		//this.hotelskaSobaList = hotelskaSobaList;
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
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	@JsonIgnore
	public List<HotelskaSoba> getHotelskaSobaList() {
		return hotelskaSobaList;
	}
	@JsonIgnore
	public void setHotelskaSobaList(List<HotelskaSoba> hotelskaSobaList) {
		this.hotelskaSobaList = hotelskaSobaList;
	}
	
	
}
