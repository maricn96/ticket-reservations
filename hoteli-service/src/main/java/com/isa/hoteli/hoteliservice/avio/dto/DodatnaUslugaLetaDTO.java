package com.isa.hoteli.hoteliservice.avio.dto;

import com.isa.hoteli.hoteliservice.avio.model.DodatnaUslugaLeta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DodatnaUslugaLetaDTO 
{
	private long idDodatneUsluge;
	private String naziv;
	
	public DodatnaUslugaLetaDTO(DodatnaUslugaLeta usluga)
	{
		this.idDodatneUsluge = usluga.getIdDodatneUsluge();
		this.naziv = usluga.getNaziv();
	}
	
	
//	@ManyToMany(mappedBy = "dodatne_usluge_koje_let_sadrzi")
//	private List<Let> letovi;
	
}
