package com.isa.hoteli.hoteliservice.avio.model;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destinacija
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDestinacije;
	
	private String naziv;
	
	private String informacije;
	
	@ManyToMany(mappedBy = "destinacijeNaKojimaPosluje")
	private List<AvioKompanija> kompanija;
	
	@ManyToMany(mappedBy = "klaseKojeLetSadrzi")
	private List<Let> destinacijeNaKojimaPosluje;
	
	
	@OneToMany(mappedBy = "destinacijaPoletanja")
    private List<Let> poletanje;
	
	@OneToMany(mappedBy = "destinacijaSletanja")
    private List<Let> sletanje;
	
	@ManyToMany(mappedBy = "destinacijePresedanja")
	private List<Let> letovi;

	public Destinacija(long idDestinacije, String naziv, String informacije) {
		super();
		this.idDestinacije = idDestinacije;
		this.naziv = naziv;
		this.informacije = informacije;
	}
	
	
}
