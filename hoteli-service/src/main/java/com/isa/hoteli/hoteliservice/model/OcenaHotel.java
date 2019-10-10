package com.isa.hoteli.hoteliservice.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.isa.hoteli.hoteliservice.dto.OcenaHotelDTO;
/*
{
    "ocena": 3,
    "rezervacijaId": 1,
    "korisnikId": 2,
    "hotelId": 1
}
*/
@Entity
public class OcenaHotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int ocena;
	private Date datumOcene;
	private Long rezervacijaId;
	private Long korisnikId;
	private Long hotelId;
	
	public OcenaHotel() {

	}
	
	public OcenaHotel(OcenaHotelDTO ocena) {
		super();
		this.id = ocena.getId();
		this.ocena = ocena.getOcena();
		this.datumOcene = ocena.getDatumOcene();
		this.rezervacijaId = ocena.getRezervacijaId();
		this.korisnikId = ocena.getKorisnikId();
		this.hotelId = ocena.getHotelId();
	}
	
	public OcenaHotel(Long id, int ocena, Date datumOcene, Long rezervacijaId, Long korisnikId, Long hotelId) {
		super();
		this.id = id;
		this.ocena = ocena;
		this.datumOcene = datumOcene;
		this.rezervacijaId = rezervacijaId;
		this.korisnikId = korisnikId;
		this.hotelId = hotelId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getOcena() {
		return ocena;
	}
	public void setOcena(int ocena) {
		this.ocena = ocena;
	}
	public Date getDatumOcene() {
		return datumOcene;
	}
	public void setDatumOcene(Date datumOcene) {
		this.datumOcene = datumOcene;
	}
	public Long getRezervacijaId() {
		return rezervacijaId;
	}
	public void setRezervacijaId(Long rezervacijaId) {
		this.rezervacijaId = rezervacijaId;
	}
	public Long getKorisnikId() {
		return korisnikId;
	}
	public void setKorisnikId(Long korisnikId) {
		this.korisnikId = korisnikId;
	}
	public Long getHotelId() {
		return hotelId;
	}
	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}
	
}
