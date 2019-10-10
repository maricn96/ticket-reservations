package com.isa.hoteli.hoteliservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Cenovnik;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.model.TipSobe;

public class HotelskaSobaDTO {

	private Long id;	
	private int brojSobe;	
	private int sprat;	
	private int brojKreveta;
	private float originalnaCena;
	private Hotel hotel;
	private TipSobe tipSobe;
	private List<Cenovnik> cenovnikList;
	private List<CenaNocenja> cenaNocenjaList;
	private List<Rezervacije> rezervacijeList;
	
	public HotelskaSobaDTO() {

	}
	
	public HotelskaSobaDTO(HotelskaSoba soba) {
		super();
		this.id = soba.getId();
		this.brojSobe = soba.getBrojSobe();
		this.sprat = soba.getSprat();
		this.brojKreveta = soba.getBrojKreveta();
		this.hotel = soba.getHotel();
		this.tipSobe = soba.getTipSobe();
		this.originalnaCena = soba.getOriginalnaCena();
		/*this.cenovnikList = cenovnikList;
		this.cenaNocenjaList = cenaNocenjaList;
		this.rezervacijeList = rezervacijeList;*/
	}
	
	public HotelskaSobaDTO(Long id, int brojSobe, int sprat, int brojKreveta, float originalnaCena, Hotel hotel, TipSobe tipSobe/*,
			List<Cenovnik> cenovnikList, List<CenaNocenja> cenaNocenjaList, List<Rezervacije> rezervacijeList*/) {
		super();
		this.id = id;
		this.brojSobe = brojSobe;
		this.sprat = sprat;
		this.brojKreveta = brojKreveta;
		this.hotel = hotel;
		this.tipSobe = tipSobe;
		this.originalnaCena = originalnaCena;
		/*this.cenovnikList = cenovnikList;
		this.cenaNocenjaList = cenaNocenjaList;
		this.rezervacijeList = rezervacijeList;*/
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getBrojSobe() {
		return brojSobe;
	}
	public void setBrojSobe(int brojSobe) {
		this.brojSobe = brojSobe;
	}
	public int getSprat() {
		return sprat;
	}
	public void setSprat(int sprat) {
		this.sprat = sprat;
	}
	public int getBrojKreveta() {
		return brojKreveta;
	}
	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	public TipSobe getTipSobe() {
		return tipSobe;
	}
	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
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
	public List<CenaNocenja> getCenaNocenjaList() {
		return cenaNocenjaList;
	}
	@JsonIgnore
	public void setCenaNocenjaList(List<CenaNocenja> cenaNocenjaList) {
		this.cenaNocenjaList = cenaNocenjaList;
	}
	@JsonIgnore
	public List<Rezervacije> getRezervacijeList() {
		return rezervacijeList;
	}
	@JsonIgnore
	public void setRezervacijeList(List<Rezervacije> rezervacijeList) {
		this.rezervacijeList = rezervacijeList;
	}

	public float getOriginalnaCena() {
		return originalnaCena;
	}

	public void setOriginalnaCena(float originalnaCena) {
		this.originalnaCena = originalnaCena;
	}
	
	
	
}
