package com.isa.hoteli.hoteliservice.avio.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlanjePozivniceZaRezervacijuDTO 
{
	private List<KartaDTO> listaKarata;
	private List<KorisnikDTO> listaPrijatelja;
	private List<String> brojeviPasosa;
}
