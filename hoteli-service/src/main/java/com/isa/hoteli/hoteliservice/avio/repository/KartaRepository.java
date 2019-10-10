package com.isa.hoteli.hoteliservice.avio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;


@EnableJpaRepositories(basePackageClasses = {Karta.class})
@Repository
public interface KartaRepository extends JpaRepository<Karta, Long>
{
	public Karta findByKorisnik(Korisnik korisnik);
}
