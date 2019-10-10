package com.isa.hoteli.hoteliservice.avio.model;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Karta
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idKarte;
	
	@NotNull
	private float cena;
	
	@Range(min=0, max=5)
	private int ocena;
	
	@NotNull
	private boolean brzaRezervacija;
	
	@NotNull
	private float popust;
	
	private String brojPasosa;
	
	private Long idHotelRezervacije;
	
	
	
	@ManyToOne
    @JoinColumn(name="id_leta", nullable=false)
    private Let let;
	
	//REZERVACIJA UBACENA U KARTU
	
	@Value("${some.key:2000:01:01T00:00:00}")
	private LocalDateTime vremeRezervisanja;
	
	@ManyToOne
    @JoinColumn(name="id_korisnika", nullable=true)
    private Korisnik korisnik;
	
	@ManyToOne
    @JoinColumn(name="id_korisnika_koji_salje_poz", nullable=true)
    private Korisnik korisnikKojiSaljePozivnicu;
	
	@Version
	private int version;
	
	
}
