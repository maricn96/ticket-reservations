package com.isa.hoteli.hoteliservice.avio.dto;

import com.isa.hoteli.hoteliservice.avio.model.Klasa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KlasaDTO 
{
	private long idKlase;
	private String naziv;
	
	public KlasaDTO(Klasa klasa)
	{
		this.idKlase = klasa.getIdKlase();
		this.naziv = klasa.getNaziv();
	}
}
