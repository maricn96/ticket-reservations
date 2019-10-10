package com.isa.hoteli.hoteliservice.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.dto.OcenaHotelDTO;
import com.isa.hoteli.hoteliservice.dto.OcenaHotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.OcenaHotel;
import com.isa.hoteli.hoteliservice.model.OcenaHotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.OcenaHotelRepository;
import com.isa.hoteli.hoteliservice.repository.OcenaHotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;

@Component
public class OcenaService {
	
	@Autowired
	private OcenaHotelRepository ocenaHotelRepository;
	
	@Autowired
	private OcenaHotelskaSobaRepository ocenaHotelskaSobaRepository;
	
	@Autowired
	private RezervacijeRepository rezervacijeRepository;
	
	@Transactional(readOnly = true)
	public List<OcenaHotel> getHotelRatings(){
		return ocenaHotelRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<OcenaHotelskaSoba> getRoomRatings(){
		return ocenaHotelskaSobaRepository.findAll();
	}
	
	@Transactional(readOnly = false)
	public OcenaHotelDTO createHotelRating(OcenaHotel ocena) {
		Rezervacije r = rezervacijeRepository.getOne(ocena.getRezervacijaId());
		if(r!=null && r.getDatumOd().before(new Date(System.currentTimeMillis()))) {
			OcenaHotel o = ocenaHotelRepository.vecOcenjeno(ocena.getRezervacijaId());
			if(o==null) {
				return new OcenaHotelDTO(ocenaHotelRepository.save(ocena));
			}
			return null;
		}
		return null;//rezervacija ne postoji
	}
	
	@Transactional(readOnly = false)
	public OcenaHotelskaSobaDTO createHotelRoomRating(OcenaHotelskaSoba ocena) {
		Rezervacije r = rezervacijeRepository.getOne(ocena.getRezervacijaId());
		if(r!=null && r.getDatumOd().before(new Date(System.currentTimeMillis()))) {
			OcenaHotelskaSoba o = ocenaHotelskaSobaRepository.vecOcenjeno(ocena.getRezervacijaId());
			if(o==null) {
				return new OcenaHotelskaSobaDTO(ocenaHotelskaSobaRepository.save(ocena));
			}
			return null; //vec ocenjena soba
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	public float getMeanHotelRating(Long id) {
		if(!ocenaHotelRepository.ocene(id).isEmpty()) {
			float mean = ocenaHotelRepository.prosek(id);
			return mean;
		}else {
			return (float) 0.0;
		}
	}
	
	@Transactional(readOnly = true)
	public float getMeanRoomRating(Long id) {
		if(!ocenaHotelskaSobaRepository.ocene(id).isEmpty()) {
			float mean = ocenaHotelskaSobaRepository.prosek(id);
			return mean;
		}else {
			return (float) 0.0;
		}
	}

}
