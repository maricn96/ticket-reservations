package com.isa.hoteli.hoteliservice.avio.model;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prtljag
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPrtljaga;
	
	@NotNull
	private float tezina; //kg
	
	@NotNull
	private String opis;
	
	
	@ManyToMany(mappedBy = "tipoviPrtljagaPoLetu")
	private List<Let> letovi;
	
}
