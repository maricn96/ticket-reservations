package com.isa.hoteli.hoteliservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.model.OcenaHotel;

@Repository
public interface OcenaHotelRepository extends JpaRepository<OcenaHotel, Long>{

	@Query(value = "SELECT AVG(ocena) FROM ocena_hotel WHERE hotel_id = ?1", nativeQuery=true)
	float prosek(Long id);
	
	@Query(value = "SELECT * FROM ocena_hotel WHERE hotel_id = ?1", nativeQuery=true)
	List<OcenaHotel> ocene(Long id);
	
	@Query(value = "SELECT * FROM ocena_hotel WHERE rezervacija_id = ?1", nativeQuery=true)
	OcenaHotel vecOcenjeno(Long id);
}
