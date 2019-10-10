package com.isa.hoteli.hoteliservice.avio.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.avio.dto.KartaDTO;
import com.isa.hoteli.hoteliservice.avio.model.Karta;

@Component
public class KartaConverter 
{
	@Autowired
	private LetConverter letConv;
	
	@Autowired
	private KorisnikConverter korConv;
	
	public KartaDTO convertToDTO(Karta model)
	{
		KartaDTO dto = new KartaDTO();
		
		dto.setIdKarte(model.getIdKarte());
		dto.setCena(model.getCena());
		dto.setOcena(model.getOcena());
		dto.setBrzaRezervacija(model.isBrzaRezervacija());
		dto.setPopust(model.getPopust());
		dto.setBrojPasosa(model.getBrojPasosa());
		dto.setIdHotelRezervacije(model.getIdHotelRezervacije());
		dto.setLet(letConv.convertToDTO(model.getLet()));
		dto.setVremeRezervisanja(model.getVremeRezervisanja());
		dto.setKorisnik(korConv.convertToDTO(model.getKorisnik()));
		dto.setKorisnikKojiSaljePozivnicu(korConv.convertToDTO(model.getKorisnikKojiSaljePozivnicu()));
		
		return dto;
	}
	
	public Karta convertFromDTO(KartaDTO dto)
	{
		Karta model = new Karta();
		
		model.setIdKarte(dto.getIdKarte());
		model.setCena(dto.getCena());
		model.setOcena(dto.getOcena());
		model.setBrzaRezervacija(dto.isBrzaRezervacija());
		model.setPopust(dto.getPopust());
		model.setBrojPasosa(dto.getBrojPasosa());
		model.setIdHotelRezervacije(dto.getIdHotelRezervacije());
		model.setLet(letConv.convertFromDTO(dto.getLet()));
		model.setVremeRezervisanja(dto.getVremeRezervisanja());
		model.setKorisnik(korConv.convertFromDTO(dto.getKorisnik()));
		model.setKorisnikKojiSaljePozivnicu(korConv.convertFromDTO(dto.getKorisnikKojiSaljePozivnicu()));
		
		
		return model;
		
	}
}
