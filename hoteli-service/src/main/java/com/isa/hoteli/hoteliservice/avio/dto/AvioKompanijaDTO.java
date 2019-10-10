package com.isa.hoteli.hoteliservice.avio.dto;

import java.util.List;

import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvioKompanijaDTO
{
	private long idAvioKompanije;
	private String naziv;
	private String adresa;
	private String opis;
	private float lat;
	private float lng;
	
    private List<DestinacijaDTO> destinacijeNaKojimaPosluje;

	public AvioKompanijaDTO(AvioKompanija avio) 
	{
		super();
		this.idAvioKompanije = avio.getIdAvioKompanije();
		this.naziv = avio.getNaziv();
		this.adresa = avio.getAdresa();
		this.opis = avio.getOpis();
		this.destinacijeNaKojimaPosluje = null;
	}

	public AvioKompanijaDTO(long idAvioKompanije, String naziv, String adresa, String opis) {
		super();
		this.idAvioKompanije = idAvioKompanije;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}
	
	
    
    
}
