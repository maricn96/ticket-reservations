package com.isa.hoteli.hoteliservice.avio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.avio.model.Destinacija;


@Repository
public interface DestinacijaRepository extends JpaRepository<Destinacija, Long>
{
	

}
