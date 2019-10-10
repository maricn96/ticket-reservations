package com.isa.hoteli.hoteliservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.model.HotelskaSoba;

@Repository
public interface HotelskaSobaRepository extends JpaRepository<HotelskaSoba, Long>{

	@Query(value = "SELECT * FROM hotelska_soba WHERE hotel_id = ?1", nativeQuery=true)
	List<HotelskaSoba> getAllFromHotel(Long id);
	
	@Query(value = "SELECT * FROM hotelska_soba WHERE hotel_id = ?1 AND broj_sobe = ?2", nativeQuery=true)
	HotelskaSoba getRoomWithNumber(Long id, int broj);
	
}
