package com.isa.hoteli.hoteliservice.avio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class AvioKompanija 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAvioKompanije;
	
	@NotNull
	private String naziv;
	
	@NotNull
	private String adresa;
	
	private String opis;
	
	private float lat;
	
	private float lng;
	
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
	  name = "destinacije_na_kojima_posluje", 
	  joinColumns = @JoinColumn(name = "aviokompanija_id"), 
	  inverseJoinColumns = @JoinColumn(name = "destinacija_id"))
	private List<Destinacija> destinacijeNaKojimaPosluje;
	
	@OneToMany(mappedBy="aviokompanija") //kad ubacim kaskadno brisanje baca jako cudnu gresku
    private List<Let> letovi;

	public AvioKompanija(long idAvioKompanije, @NotNull String naziv, @NotNull String adresa, String opis) {
		super();
		this.idAvioKompanije = idAvioKompanije;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}
	
	

}
