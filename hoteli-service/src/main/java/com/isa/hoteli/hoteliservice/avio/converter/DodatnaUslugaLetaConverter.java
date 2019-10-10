package com.isa.hoteli.hoteliservice.avio.converter;

import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.avio.dto.DodatnaUslugaLetaDTO;
import com.isa.hoteli.hoteliservice.avio.model.DodatnaUslugaLeta;

@Component
public class DodatnaUslugaLetaConverter
{

	public DodatnaUslugaLetaDTO convertToDTO(DodatnaUslugaLeta model)
	{
		DodatnaUslugaLetaDTO dto = new DodatnaUslugaLetaDTO();
		
		dto.setIdDodatneUsluge(model.getIdDodatneUsluge());
		dto.setNaziv(model.getNaziv());
		
		return dto;
	}
	
	public DodatnaUslugaLeta convertFromDTO(DodatnaUslugaLetaDTO dto)
	{
		DodatnaUslugaLeta model = new DodatnaUslugaLeta();
		
		model.setIdDodatneUsluge(dto.getIdDodatneUsluge());
		model.setNaziv(dto.getNaziv());
		
		return model;
		
	}
}
