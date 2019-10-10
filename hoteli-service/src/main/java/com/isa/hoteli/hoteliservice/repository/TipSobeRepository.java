package com.isa.hoteli.hoteliservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.model.TipSobe;

@Repository
public interface TipSobeRepository extends JpaRepository<TipSobe, Long>{

	@Query(value = "SELECT * FROM tip_sobe WHERE hotel_id = ?1", nativeQuery=true)
	List<TipSobe> getAllFromHotel(Long id);
	
}
