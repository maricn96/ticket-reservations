package com.isa.hoteli.hoteliservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.model.RentACar;
import com.isa.hoteli.hoteliservice.service.RentACarService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rent")
public class RentACarController {
	
	@Autowired
	private RentACarService rentACarService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<RentACar> createRent(@RequestBody RentACar rentACar){
		RentACar rentACar2 = rentACarService.createRentACar(rentACar);
		if(rentACar2!=null) {
			return new ResponseEntity<>(rentACar2, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	public ResponseEntity<RentACar> createRentAdmin(@PathVariable Long id, @RequestBody RentACar newRent, HttpServletRequest req){
		Korisnik korisnik = korisnikService.zaTokene(req);
		if(korisnik!=null && korisnik.getRola().equals(Rola.MASTER_ADMIN)) {
			RentACar createdRent = rentACarService.createRentACar(newRent);
			Korisnik k = korisnikService.getUserById(id);
			if(createdRent!=null) {
				k.setZaduzenZaId(createdRent.getId());
				korisnikRepository.save(k);
				return new ResponseEntity<>(createdRent, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
