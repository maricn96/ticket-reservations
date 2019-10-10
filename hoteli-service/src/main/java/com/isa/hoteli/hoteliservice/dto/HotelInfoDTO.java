package com.isa.hoteli.hoteliservice.dto;

public class HotelInfoDTO {

	private Long id;	
	private String naziv;	
	private String adresa;	
	private String opis;
	private float ocena;
	private float lat;
	private float lng;
	
	public HotelInfoDTO() {

	}
	
	public HotelInfoDTO(Long id, String naziv, String adresa, String opis, float ocena, float lat, float lng) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
		this.lat = lat;
		this.lng = lng;
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
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public float getOcena() {
		return ocena;
	}
	public void setOcena(float ocena) {
		this.ocena = ocena;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}
	
	
	
}
