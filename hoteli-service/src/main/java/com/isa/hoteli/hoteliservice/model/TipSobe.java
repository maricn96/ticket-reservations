package com.isa.hoteli.hoteliservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isa.hoteli.hoteliservice.dto.TipSobeDTO;

@Entity
public class TipSobe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String naziv;
	
	@ManyToOne
    @JoinColumn(name="hotel_id", nullable=false)
    private Hotel hotel;
	
	@OneToMany(mappedBy="tipSobe", orphanRemoval = true, cascade = CascadeType.PERSIST)
	private List<HotelskaSoba> hotelskaSobaList;

	public TipSobe() {

	}
	
	public TipSobe(TipSobeDTO ts) {
		super();
		this.id = ts.getId();
		this.naziv = ts.getNaziv();
		this.hotel = ts.getHotel();
		//this.hotelskaSobaList = ts.getHotelskaSobaList();
	}
	
	public TipSobe(Long id, String naziv, Hotel hotel/*, List<HotelskaSoba> hotelskaSobaList*/) {
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
