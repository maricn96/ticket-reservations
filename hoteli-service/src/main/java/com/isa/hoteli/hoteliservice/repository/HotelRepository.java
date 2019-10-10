package com.isa.hoteli.hoteliservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>{

	/*@Query(value = "SELECT * FROM dodatne_usluge", nativeQuery=true)
	List<DodatneUsluge> getAll();*/
	
}
