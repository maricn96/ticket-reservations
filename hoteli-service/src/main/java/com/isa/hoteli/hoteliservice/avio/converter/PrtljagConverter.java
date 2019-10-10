
package com.isa.hoteli.hoteliservice.avio.converter;

import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.avio.dto.PrtljagDTO;
import com.isa.hoteli.hoteliservice.avio.model.Prtljag;

@Component
public class PrtljagConverter 
{
	
	public PrtljagDTO convertToDTO(Prtljag model)
	{
		PrtljagDTO dto = new PrtljagDTO();
		
		dto.setIdPrtljaga(model.getIdPrtljaga());
		dto.setTezina(model.getTezina());
		dto.setOpis(model.getOpis());
		
		return dto;
	}
	
	public Prtljag convertFromDTO(PrtljagDTO dto)
	{
		Prtljag model = new Prtljag();
		
		model.setIdPrtljaga(dto.getIdPrtljaga());
		model.setTezina(dto.getTezina());
		model.setOpis(dto.getOpis());
		
		return model;
		
	}
}
