package com.isa.hoteli.hoteliservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.model.RentACar;
import com.isa.hoteli.hoteliservice.repository.RentACarRepository;

@Component
public class RentACarService {
	
	@Autowired
	RentACarRepository rentACarRepository;
	
	@Transactional(readOnly = false)
	public RentACar createRentACar(RentACar rentACar) {
		return rentACarRepository.save(rentACar);
	}

}
