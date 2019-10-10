package com.isa.hoteli.hoteliservice.dto;

import java.sql.Date;

import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;

public class RezervacijeDTO {
	
	private Long id;
	private Date datumOd;
	private Date datumDo;
	private float ukupnaCena;
	private int brojOsoba;
	private HotelskaSoba hotelskaSoba;
    private Korisnik korisnik;
    private Hotel hotel;
    
	public RezervacijeDTO() {

	}
	
	public RezervacijeDTO(Rezervacije rezervacija) {
		super();
		this.id = rezervacija.getId();
		this.datumOd = rezervacija.getDatumOd();
		this.datumDo = rezervacija.getDatumDo();
		this.ukupnaCena = rezervacija.getUkupnaCena();
		this.brojOsoba = rezervacija.getBrojOsoba();
		this.hotelskaSoba = rezervacija.getHotelskaSoba();
		this.korisnik = rezervacija.getKorisnik();
		this.hotel = rezervacija.getHotel();
	}
    
	public RezervacijeDTO(Long id, Date datumOd, Date datumDo, float ukupnaCena, int brojOsoba, HotelskaSoba hotelskaSoba, Korisnik korisnik, Hotel hotel) {
		super();
		this.id = id;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.hotelskaSoba = hotelskaSoba;
		this.brojOsoba = brojOsoba;
		this.korisnik = korisnik;
		this.ukupnaCena = ukupnaCena;
		this.hotel = hotel;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public float getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(float ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public int getBrojOsoba() {
		return brojOsoba;
	}

	public void setBrojOsoba(int brojOsoba) {
		this.brojOsoba = brojOsoba;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
    
    
    
}
