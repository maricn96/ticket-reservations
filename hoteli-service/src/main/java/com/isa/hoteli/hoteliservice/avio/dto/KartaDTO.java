package com.isa.hoteli.hoteliservice.avio.dto;

import java.time.LocalDateTime;

import com.isa.hoteli.hoteliservice.avio.model.Karta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KartaDTO 
{
	
	private long idKarte;
	private float cena;
	private int ocena;
	private boolean brzaRezervacija;
	private float popust;
	private String brojPasosa;
	private Long idHotelRezervacije;

	private LetDTO let;
	
	private LocalDateTime vremeRezervisanja;
	private KorisnikDTO korisnik;
	private KorisnikDTO korisnikKojiSaljePozivnicu;
	
	private int version;
	
	public KartaDTO(Karta karta)
	{
		this.idKarte = karta.getIdKarte();
		this.cena = karta.getCena();
		this.ocena = karta.getOcena();
		this.brzaRezervacija = karta.isBrzaRezervacija();
		this.popust = karta.getPopust();
		this.brojPasosa = karta.getBrojPasosa();
		this.vremeRezervisanja = karta.getVremeRezervisanja();
	}
	
	
}
