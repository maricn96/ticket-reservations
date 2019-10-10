package com.isa.hoteli.hoteliservice.avio.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isa.hoteli.hoteliservice.avio.dto.KorisnikDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Korisnik 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private String grad;
	private String telefon;
	private boolean aktiviran;
	private Rola rola;
	private Long zaduzenZaId;
	private boolean prviPutLogovan;
	private String brojPasosa;
	
	@OneToMany(mappedBy="korisnik")
    private List<Karta> spisakRezervisanihKarata;
	
	@OneToMany(mappedBy="korisnikKojiSaljePozivnicu")
    private List<Karta> spisakKarataZaPrijatelje;
	
	//treba dodati rezervacije (rezervacije je entitet povezan sa kartom)
	
	@ManyToMany
	@JoinTable(
	  name = "korisnik_prijatelji", 
	  joinColumns = @JoinColumn(name = "korisnik_id"), 
	  inverseJoinColumns = @JoinColumn(name = "prijatelj_id"))
	private List<Korisnik> prijateljiKorisnika;
	
	@ManyToMany(mappedBy = "prijateljiKorisnika")
	private List<Korisnik> korisnici;
	
	/*
	 * ZAHTEVI KORISNIKA
	 */
	@ManyToMany
	@JoinTable(
	  name = "korisnik_zahtevi", 
	  joinColumns = @JoinColumn(name = "korisnik_id"), 
	  inverseJoinColumns = @JoinColumn(name = "prijatelj_id"))
	private List<Korisnik> zahteviKorisnika;
	
	@ManyToMany(mappedBy = "zahteviKorisnika")
	private List<Korisnik> korisniciZaht;
	
	private int bodovi;

	
	public Korisnik(KorisnikDTO korisnik) {
		super();
		this.id = korisnik.getId();
		this.email = korisnik.getEmail();
		this.lozinka = korisnik.getLozinka();
		this.ime = korisnik.getIme();
		this.prezime = korisnik.getPrezime();
		this.grad = korisnik.getGrad();
		this.telefon = korisnik.getTelefon();
		this.aktiviran = korisnik.isAktiviran();
		this.rola = korisnik.getRola();
		this.zaduzenZaId = korisnik.getZaduzenZaId();
		this.prviPutLogovan = korisnik.isPrviPutLogovan();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getLozinka() {
		return lozinka;
	}



	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}



	public String getIme() {
		return ime;
	}



	public void setIme(String ime) {
		this.ime = ime;
	}



	public String getPrezime() {
		return prezime;
	}



	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}



	public String getGrad() {
		return grad;
	}



	public void setGrad(String grad) {
		this.grad = grad;
	}



	public String getTelefon() {
		return telefon;
	}



	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}



	public boolean isAktiviran() {
		return aktiviran;
	}



	public void setAktiviran(boolean aktiviran) {
		this.aktiviran = aktiviran;
	}



	public Rola getRola() {
		return rola;
	}



	public void setRola(Rola rola) {
		this.rola = rola;
	}



	public Long getZaduzenZaId() {
		return zaduzenZaId;
	}



	public void setZaduzenZaId(Long zaduzenZaId) {
		this.zaduzenZaId = zaduzenZaId;
	}



	public boolean isPrviPutLogovan() {
		return prviPutLogovan;
	}



	public void setPrviPutLogovan(boolean prviPutLogovan) {
		this.prviPutLogovan = prviPutLogovan;
	}



	public String getBrojPasosa() {
		return brojPasosa;
	}



	public void setBrojPasosa(String brojPasosa) {
		this.brojPasosa = brojPasosa;
	}


	@JsonIgnore
	public List<Karta> getSpisakRezervisanihKarata() {
		return spisakRezervisanihKarata;
	}


	@JsonIgnore
	public void setSpisakRezervisanihKarata(List<Karta> spisakRezervisanihKarata) {
		this.spisakRezervisanihKarata = spisakRezervisanihKarata;
	}


	@JsonIgnore
	public List<Karta> getSpisakKarataZaPrijatelje() {
		return spisakKarataZaPrijatelje;
	}


	@JsonIgnore
	public void setSpisakKarataZaPrijatelje(List<Karta> spisakKarataZaPrijatelje) {
		this.spisakKarataZaPrijatelje = spisakKarataZaPrijatelje;
	}


	@JsonIgnore
	public List<Korisnik> getPrijateljiKorisnika() {
		return prijateljiKorisnika;
	}


	@JsonIgnore
	public void setPrijateljiKorisnika(List<Korisnik> prijateljiKorisnika) {
		this.prijateljiKorisnika = prijateljiKorisnika;
	}


	@JsonIgnore
	public List<Korisnik> getKorisnici() {
		return korisnici;
	}

	@JsonIgnore
	public void setKorisnici(List<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}


	@JsonIgnore
	public List<Korisnik> getZahteviKorisnika() {
		return zahteviKorisnika;
	}


	@JsonIgnore
	public void setZahteviKorisnika(List<Korisnik> zahteviKorisnika) {
		this.zahteviKorisnika = zahteviKorisnika;
	}


	@JsonIgnore
	public List<Korisnik> getKorisniciZaht() {
		return korisniciZaht;
	}


	@JsonIgnore
	public void setKorisniciZaht(List<Korisnik> korisniciZaht) {
		this.korisniciZaht = korisniciZaht;
	}
	
	public int getBodovi() {
		return this.bodovi;
	}
	
	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}
	
	
	
}
