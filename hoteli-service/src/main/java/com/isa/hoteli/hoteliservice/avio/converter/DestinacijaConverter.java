package com.isa.hoteli.hoteliservice.avio.converter;

import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.avio.dto.DestinacijaDTO;
import com.isa.hoteli.hoteliservice.avio.model.Destinacija;

@Component
public class DestinacijaConverter
{
	
	public DestinacijaDTO convertToDTO(Destinacija model)
	{
		DestinacijaDTO dto = new DestinacijaDTO();
		
		dto.setIdDestinacije(model.getIdDestinacije());
		dto.setNaziv(model.getNaziv());
		dto.setInformacije(model.getInformacije());
		
		return dto;
	}
	
	public Destinacija convertFromDTO(DestinacijaDTO dto)
	{
		Destinacija model = new Destinacija();
		
		model.setIdDestinacije(dto.getIdDestinacije());
		model.setNaziv(dto.getNaziv());
		model.setInformacije(dto.getInformacije());
		
		return model;
		
	}
}
