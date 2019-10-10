package com.isa.hoteli.hoteliservice.avio.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.isa.hoteli.hoteliservice.avio.model.Let;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LetDTO 
{
	private long idLeta;
	private long brojLeta;
	private LocalDateTime vremePoletanja;
	private LocalDateTime vremeSletanja;
	private int duzinaPutovanja; 
	private int brojPresedanja;
	private float prosecnaOcena;
	private String tipPuta;
	private int brojOsoba;
	private int brojMesta;
	private float ukupanPrihod;
	private float cenaKarte;
	
    private AvioKompanijaDTO aviokompanija;
    private DestinacijaDTO destinacijaPoletanja;
    private DestinacijaDTO destinacijaSletanja;
	private List<DestinacijaDTO> destinacijePresedanja;
    private List<PrtljagDTO> tipoviPrtljagaPoLetu;
	private List<KlasaDTO> klaseKojeLetSadrzi;
	private List<DodatnaUslugaLetaDTO> dodatneUslugeKojeLetSadrzi;
	
	public LetDTO(Let let)
	{
		this.idLeta = let.getIdLeta();
		this.brojLeta = let.getBrojLeta();
		this.vremePoletanja = let.getVremePoletanja();
		this.vremeSletanja = let.getVremeSletanja();
		this.duzinaPutovanja = let.getDuzinaPutovanja();
		this.brojPresedanja = let.getBrojPresedanja();
		this.prosecnaOcena = let.getProsecnaOcena();
		this.tipPuta = let.getTipPuta();
		this.brojMesta = let.getBrojMesta();
		this.ukupanPrihod = let.getUkupanPrihod();
		this.cenaKarte = let.getCenaKarte();
	}
}
