package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.dto.TipSobeDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.TipSobe;
import com.isa.hoteli.hoteliservice.repository.HotelRepository;
import com.isa.hoteli.hoteliservice.repository.TipSobeRepository;

@Component
public class TipSobeService {

	@Autowired
	private TipSobeRepository tipSobeRepository;
	
	@Transactional(readOnly = true)
	public List<TipSobe> getTypes(){
		return tipSobeRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<TipSobe> getTypesFromHotel(Long id){
		return tipSobeRepository.getAllFromHotel(id);
	}
	
	@Transactional(readOnly = true)
	public TipSobe getTipSobeById(Long id){
		return tipSobeRepository.getOne(id);
	}
	
	@Transactional(readOnly = false)
	public TipSobeDTO createType(TipSobe tipSobe) {
		return new TipSobeDTO(tipSobeRepository.save(tipSobe));
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String deleteType(Long id) {
		tipSobeRepository.deleteById(id);
		return "Uspesno obrisan tip sa id: " + id;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public TipSobeDTO updateType(TipSobe tipSobe, Long id) {
		TipSobe tipSobe1 = tipSobeRepository.getOne(id);
		if(tipSobe1!=null) {
			tipSobe1.setNaziv(tipSobe.getNaziv());
			tipSobe1.setHotel(tipSobe.getHotel());
			tipSobeRepository.save(tipSobe1);
			return new TipSobeDTO(tipSobe1);
		}
		return null;
	}
	
}
