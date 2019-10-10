package com.isa.hoteli.hoteliservice.dto;

import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.TipSobe;

public class HotelskaSobaInfoDTO {
	
	private Long id;	
	private int brojSobe;	
	private int sprat;	
	private int brojKreveta;
	private float originalnaCena;
	private Hotel hotel;
	private TipSobe tipSobe;
	private float cenaNocenja;
	private float ocena;
	
	public HotelskaSobaInfoDTO() {

	}
	
	public HotelskaSobaInfoDTO(Long id, int brojSobe, int sprat, int brojKreveta, float originalnaCena, Hotel hotel,
			TipSobe tipSobe, float cenaNocenja, float ocena) {
		super();
		this.id = id;
		this.brojSobe = brojSobe;
		this.sprat = sprat;
		this.brojKreveta = brojKreveta;
		this.originalnaCena = originalnaCena;
		this.hotel = hotel;
		this.tipSobe = tipSobe;
		this.cenaNocenja = cenaNocenja;
		this.ocena = ocena;
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
	public float getOriginalnaCena() {
		return originalnaCena;
	}
	public void setOriginalnaCena(float originalnaCena) {
		this.originalnaCena = originalnaCena;
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
	public float getCenaNocenja() {
		return cenaNocenja;
	}
	public void setCenaNocenja(float cenaNocenja) {
		this.cenaNocenja = cenaNocenja;
	}
	public float getOcena() {
		return ocena;
	}
	public void setOcena(float ocena) {
		this.ocena = ocena;
	}
	
	

}
