package com.isa.hoteli.hoteliservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isa.hoteli.hoteliservice.model.Cenovnik;
import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.TipSobe;

public class HotelDTO {

	private Long id;	
	private String naziv;	
	private String adresa;	
	private String opis;
	private List<Cenovnik> cenovnikList;
	private List<HotelskaSoba> hotelskaSobaList;
	private List<DodatnaUsluga> dodatnaUslugaList;
	private List<TipSobe> tipSobeList;
	private String konfiguracija;
	private float lat;
	private float lng;
	
	public HotelDTO() {

	}
	
	public HotelDTO(Hotel hotel) {
		super();
		this.id = hotel.getId();
		this.naziv = hotel.getNaziv();
		this.adresa = hotel.getAdresa();
		this.opis = hotel.getOpis();
		/*this.cenovnikList = hotel.getCenovnikList();
		this.hotelskaSobaList = hotel.getHotelskaSobaList();
		this.dodatnaUslugaList = hotel.getDodatnaUslugaList();
		//this.tipSobeList = hotel.getTipSobeList();*/
		this.konfiguracija = hotel.getKonfiguracija();
		this.lat = hotel.getLat();
		this.lng = hotel.getLng();
	}
	
	public HotelDTO(Long id, String naziv, String adresa, String opis/*, List<Cenovnik> cenovnikList,
			List<HotelskaSoba> hotelskaSobaList, List<DodatnaUsluga> dodatnaUslugaList, List<TipSobe> tipSobeList*/,
			String konfiguracija, float lat, float lng) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		/*this.cenovnikList = cenovnikList;
		this.hotelskaSobaList = hotelskaSobaList;
		this.dodatnaUslugaList = dodatnaUslugaList;
		//this.tipSobeList = tipSobeList;*/
		this.konfiguracija = konfiguracija;
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
	@JsonIgnore
	public List<Cenovnik> getCenovnikList() {
		return cenovnikList;
	}
	@JsonIgnore
	public void setCenovnikList(List<Cenovnik> cenovnikList) {
		this.cenovnikList = cenovnikList;
	}
	@JsonIgnore
	public List<HotelskaSoba> getHotelskaSobaList() {
		return hotelskaSobaList;
	}
	@JsonIgnore
	public void setHotelskaSobaList(List<HotelskaSoba> hotelskaSobaList) {
		this.hotelskaSobaList = hotelskaSobaList;
	}
	@JsonIgnore
	public List<DodatnaUsluga> getDodatnaUslugaList() {
		return dodatnaUslugaList;
	}
	@JsonIgnore
	public void setDodatnaUslugaList(List<DodatnaUsluga> dodatnaUslugaList) {
		this.dodatnaUslugaList = dodatnaUslugaList;
	}
	@JsonIgnore
	public List<TipSobe> getTipSobeList() {
		return tipSobeList;
	}
	@JsonIgnore
	public void setTipSobeList(List<TipSobe> tipSobeList) {
		this.tipSobeList = tipSobeList;
	}
	public String getKonfiguracija() {
		return konfiguracija;
	}
	public void setKonfiguracija(String konfiguracija) {
		this.konfiguracija = konfiguracija;
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
