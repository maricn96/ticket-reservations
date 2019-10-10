package com.isa.hoteli.hoteliservice.avio.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.avio.model.Let;


@EnableJpaRepositories(basePackageClasses = {Let.class})
@Repository
public interface LetRepository extends JpaRepository<Let, Long>
{
	/*
	 * Pretraga letova OD-DO po datumu
	 */
	@Query(value = "select f.id_leta, f.broj_leta, f.vreme_poletanja, f.vreme_sletanja, f.duzina_putovanja, f.broj_presedanja, f.prosecna_ocena, f.tip_puta, f.broj_osoba, f.ukupan_prihod, f.avio_kompanija, f.destinacija_poletanja, f.destinacija_sletanja from let f"
		+ " where f.vreme_poletanja > :time1 and f.vreme_sletanja < :time2 ;", nativeQuery=true)
	Optional<List<Let>> findFlightsByDate(@Param("time1") LocalDateTime time1, @Param("time2") LocalDateTime time2);
	
	/*
	 * Pretraga letova po destinacijama (preko naziva destinacije)
	 */
	@Query(value = "select distinct f.id_leta, f.broj_leta, f.vreme_poletanja, f.vreme_sletanja, f.duzina_putovanja, f.broj_presedanja, f.prosecna_ocena, f.tip_puta, f.broj_osoba, f.ukupan_prihod, f.avio_kompanija, f.destinacija_poletanja, f.destinacija_sletanja from let f where destinacija_poletanja = :takeOffDest and destinacija_sletanja = :landingDest ;", nativeQuery = true)
	Optional<List<Let>> findFlightsByDestination(@Param("takeOffDest") Long takeOffDest, @Param("landingDest") Long landingDest);

	/*
	 * Pretraga letova po tipu leta
	 */
	@Query(value = "select distinct f.id_leta, f.broj_leta, f.vreme_poletanja, f.vreme_sletanja, f.duzina_putovanja, f.broj_presedanja, f.prosecna_ocena, f.tip_puta, f.broj_osoba, f.ukupan_prihod, f.avio_kompanija, f.destinacija_poletanja, f.destinacija_sletanja from let f where f.tip_puta = :type ;", nativeQuery = true)
	Optional<List<Let>> findFlightsByType(@Param("type") String type);

	/*
	 * Pronalazi srednju ocenu za jedan let
	 */
	@Query(value = "select avg(t.ocena) from karta t, let f where f.id_leta = :id ;", nativeQuery = true)
	Optional<Float> findAverageRating(@Param("id") Long id);
	
	@Query(value = "select avg(t.ocena) from karta t, let f where f.id_leta = :id ;", nativeQuery = true)
	Float findAverageRatingTwin(@Param("id") Long id);

	public Let findByBrojLeta(Long brojLeta);
	public Boolean existsByBrojLeta(Long brojLeta);
	
}
