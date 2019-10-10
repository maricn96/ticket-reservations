package com.isa.hoteli.hoteliservice.avio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.hoteli.hoteliservice.avio.model.Klasa;


@Repository
public interface KlasaRepository extends JpaRepository<Klasa, Long>
{

}
