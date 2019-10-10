package com.isa.hoteli.hoteliservice.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Rezervacije;

@Repository
public interface RezervacijeRepository extends JpaRepository<Rezervacije, Long>{

	@Query(value = "SELECT * FROM rezervacije WHERE hotelska_soba_id = ?1", nativeQuery=true)
	List<Rezervacije> getAllFromHotelRoom(Long id);
	
	@Query(value= "SELECT * FROM rezervacije WHERE (hotelska_soba_id = ?1) AND ((datum_od <= ?2 AND datum_do >= ?2) OR (datum_od <= ?3 AND datum_do >= ?3) OR (datum_od >= ?2 AND datum_do <= ?3))", nativeQuery=true)
	List<Rezervacije> findKonfliktRezervacije(Long id, Date datumOd, Date datumDo);
	
	@Query(value= "SELECT * FROM rezervacije WHERE hotelska_soba_id = ?1 AND (datum_od >= ?2 OR datum_do >= ?2)", nativeQuery=true)
	List<Rezervacije> neMozeMenjatiBrisati(Long id, Date datum);
	
	@Query(value = "SELECT SUM(broj_osoba) FROM rezervacije WHERE hotel_id = ?1 AND datum_od <= ?2 AND datum_do >= ?2", nativeQuery=true)
	Integer dnevnaPosecenost(Long id, Date date);
	
	@Query(value = "SELECT SUM(broj_osoba) FROM rezervacije WHERE (hotel_id = ?1) AND ((datum_od <= ?2 AND datum_do >= ?2) OR (datum_od <= ?3 AND datum_do >= ?3) OR (datum_od >= ?2 AND datum_do <= ?3) OR (datum_od >= ?2 AND datum_do <= ?3))", nativeQuery=true)
	Integer nedeljnaMesecnaPosecenost(Long id, Date date, Date datumDo);
	
	@Query(value = "SELECT SUM(ukupna_cena) FROM rezervacije WHERE (hotel_id = ?1) AND (datum_do >= ?2 AND datum_do <= ?3)", nativeQuery=true)
	Float nedeljniMesecniGodisnjiPrihod(Long id, Date date, Date datumDo);
	
	@Query(value = "SELECT * FROM rezervacije WHERE korisnik_id = ?1", nativeQuery=true)
	List<Rezervacije> findByKorisnik(Long id);
	
}
