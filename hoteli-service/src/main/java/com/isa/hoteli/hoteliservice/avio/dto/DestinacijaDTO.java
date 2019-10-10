package com.isa.hoteli.hoteliservice.avio.dto;

import com.isa.hoteli.hoteliservice.avio.model.Destinacija;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinacijaDTO
{
	private long idDestinacije;
	private String naziv;
	private String informacije;
	
	public DestinacijaDTO(Destinacija dest)
	{
		this.idDestinacije = dest.getIdDestinacije();
		this.naziv = dest.getNaziv();
		this.informacije = dest.getInformacije();
	}
	
//    private AvioKompanijaDTO aviokompanija;
	
	
//	@OneToOne(mappedBy = "destinacijaPoletanja")
//    private Let poletanje;
//	
//	@OneToOne(mappedBy = "destinacijaSletanja")
//    private Let sletanje;
//	
//	@ManyToMany(mappedBy = "destinacijePresedanja")
//	private List<Let> letovi;
}
