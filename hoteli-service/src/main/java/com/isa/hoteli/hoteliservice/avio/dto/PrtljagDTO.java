package com.isa.hoteli.hoteliservice.avio.dto;

import com.isa.hoteli.hoteliservice.avio.model.Prtljag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrtljagDTO
{
	private long idPrtljaga;
	private float tezina;
	private String opis;
	
	public PrtljagDTO(Prtljag prt)
	{
		this.idPrtljaga = prt.getIdPrtljaga();
		this.tezina = prt.getTezina();
		this.opis = prt.getOpis();
	}
}
