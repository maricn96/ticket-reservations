package com.isa.hoteli.hoteliservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.model.OcenaHotel;
import com.isa.hoteli.hoteliservice.model.OcenaHotelskaSoba;

@Repository
public interface OcenaHotelskaSobaRepository extends JpaRepository<OcenaHotelskaSoba, Long>{

	@Query(value = "SELECT AVG(ocena) FROM ocena_hotelska_soba WHERE hotelska_soba_id = ?1", nativeQuery=true)
	float prosek(Long id);
	
	@Query(value = "SELECT * FROM ocena_hotelska_soba WHERE hotelska_soba_id = ?1", nativeQuery=true)
	List<OcenaHotelskaSoba> ocene(Long id);
	
	@Query(value = "SELECT * FROM ocena_hotelska_soba WHERE rezervacija_id = ?1", nativeQuery=true)
	OcenaHotelskaSoba vecOcenjeno(Long id);
	
}
