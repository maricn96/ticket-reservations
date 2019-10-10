package com.isa.hoteli.hoteliservice.avio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;


@Repository
public interface AvioKompanijaRepository extends JpaRepository<AvioKompanija, Long>
{
	/*
	 * Pronalazi srednju ocenu za jednu aviokompaniju
	 */
	@Query(value = "select avg(l.prosecna_ocena) from let l, avio_kompanija a where a.id_avio_kompanije = :id ;", nativeQuery = true)
	Float findAverageRating(@Param("id") Long id);
	
	@Query(value = "select * from avio_kompanija order by naziv asc ;", nativeQuery = true)
	List<AvioKompanija> sortByNaziv();

	@Query(value = "select * from avio_kompanija order by adresa asc ;", nativeQuery = true)
	List<AvioKompanija> sortByGrad();
	
}
