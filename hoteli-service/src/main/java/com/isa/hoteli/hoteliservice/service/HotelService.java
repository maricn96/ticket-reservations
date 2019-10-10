package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.repository.HotelRepository;

@Component
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	
	@Transactional(readOnly = true)
	public List<Hotel> getHotels(){
		return hotelRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Hotel getHotelById(Long id){
		return hotelRepository.getOne(id);
	}
	
	@Transactional(readOnly = false)
	public HotelDTO createHotel(Hotel hotel) {
		return new HotelDTO(hotelRepository.save(hotel));
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String deleteHotel(Long id) {
		hotelRepository.deleteById(id);
		return "Uspesno obrisan hotel sa id: " + id;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public HotelDTO updateHotel(Hotel hotel, Long id) {
		Hotel hotel1 = hotelRepository.getOne(id);
		if(hotel1!=null) {
			hotel1.setNaziv(hotel.getNaziv());
			hotel1.setAdresa(hotel.getAdresa());
			hotel1.setOpis(hotel.getOpis());
			hotel1.setKonfiguracija(hotel.getKonfiguracija());
			hotelRepository.save(hotel1);
			return new HotelDTO(hotel1);
		}
		return null;
	}
	
}
