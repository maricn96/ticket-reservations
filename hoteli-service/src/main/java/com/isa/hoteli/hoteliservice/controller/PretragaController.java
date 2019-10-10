package com.isa.hoteli.hoteliservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.dto.HotelInfoDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.Pretraga;
import com.isa.hoteli.hoteliservice.service.HotelService;
import com.isa.hoteli.hoteliservice.service.OcenaService;
import com.isa.hoteli.hoteliservice.service.PretragaService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/pretraga")
public class PretragaController {
	
	@Autowired
	private PretragaService pretragaService;
	
	@Autowired
	private OcenaService ocenaService;
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<List<HotelInfoDTO>> getHotels(@RequestBody Pretraga pretraga){
		List<HotelInfoDTO> hoteliDTO = new ArrayList<>();
		List<Hotel> hoteli = pretragaService.getSearch(pretraga);
		for (Hotel hotel : hoteli) {
			hoteliDTO.add(new HotelInfoDTO(hotel.getId(), hotel.getNaziv(), hotel.getAdresa(), hotel.getOpis(), ocenaService.getMeanHotelRating(hotel.getId()), hotel.getLat(), hotel.getLng()));
		}
		return new ResponseEntity<List<HotelInfoDTO>>(hoteliDTO, HttpStatus.OK);
	}

}
