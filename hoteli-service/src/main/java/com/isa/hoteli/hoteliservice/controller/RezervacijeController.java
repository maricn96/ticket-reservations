package com.isa.hoteli.hoteliservice.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.dto.RezervacijeDTO;
import com.isa.hoteli.hoteliservice.model.Posecenost;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.service.RezervacijeService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rezervacija")
public class RezervacijeController {

	@Autowired
	private RezervacijeService rezervacijeService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<RezervacijeDTO>> getReservations(){
		List<RezervacijeDTO> dto = new ArrayList<>();
		List<Rezervacije> lista = rezervacijeService.getRezervations();
		for (Rezervacije item : lista) {
			dto.add(new RezervacijeDTO(item));
		}
		return new ResponseEntity<List<RezervacijeDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/all/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<RezervacijeDTO>> getReservationsFromHotelRooms(@PathVariable("id") Long id){
		List<RezervacijeDTO> dto = new ArrayList<>();
		List<Rezervacije> lista = rezervacijeService.getReservationsFromHotelRoom(id);
		for (Rezervacije item : lista) {
			dto.add(new RezervacijeDTO(item));
		}
		return new ResponseEntity<List<RezervacijeDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<RezervacijeDTO>> getReservationsFromUser(@PathVariable("id") Long id){
		List<RezervacijeDTO> dto = new ArrayList<>();
		List<Rezervacije> lista = rezervacijeService.getReservationsFromUser(id);
		for (Rezervacije item : lista) {
			dto.add(new RezervacijeDTO(item));
		}
		return new ResponseEntity<List<RezervacijeDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<RezervacijeDTO> getReservationsById(@PathVariable("id") Long id){
		if(rezervacijeService.getReservationById(id)!=null) {
			RezervacijeDTO dto = new RezervacijeDTO(rezervacijeService.getReservationById(id));
			return new ResponseEntity<RezervacijeDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<RezervacijeDTO> createReservation(@RequestBody RezervacijeDTO dto, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.KORISNIK)){
			Rezervacije obj = new Rezervacije(dto);
			RezervacijeDTO returnType = rezervacijeService.createReservation(obj);
			if(returnType!=null) {
				return new ResponseEntity<>(returnType, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteReservationById(@PathVariable("id") Long id, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.KORISNIK)){
			return new ResponseEntity<String>(rezervacijeService.deleteReservation(id), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<RezervacijeDTO> updateReservationById(@PathVariable("id") Long id, @RequestBody RezervacijeDTO dto){
		Rezervacije obj = new Rezervacije(dto);
		RezervacijeDTO returnTip = rezervacijeService.updateReservation(obj, id);
		if(returnTip!=null) {
			return new ResponseEntity<>(returnTip, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/posecenost/dnevna", method = RequestMethod.POST)
	public int dnevnaPosecenost(@RequestBody Posecenost posecenost, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==posecenost.getId()) {
			int brojOsoba = rezervacijeService.dnevnaPosecenost(posecenost.getId(), posecenost.getDate());
			return brojOsoba;
		}
		return -1;
	}
	
	@RequestMapping(value="/posecenost/nedeljna", method = RequestMethod.POST)
	public int nedeljnaPosecenost(@RequestBody Posecenost posecenost, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==posecenost.getId()) {
			int brojOsoba = rezervacijeService.nedeljnaPosecenost(posecenost.getId(), posecenost.getDate());
			return brojOsoba;
		}
		return -1;
	}
	
	@RequestMapping(value="/posecenost/mesecna", method = RequestMethod.POST)
	public int mesecnaPosecenost(@RequestBody Posecenost posecenost, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==posecenost.getId()) {
			int brojOsoba = rezervacijeService.mesecnaPosecenost(posecenost.getId(), posecenost.getDate());
			return brojOsoba;
		}
		return -1;
	}
	
	@RequestMapping(value="/prihodi/nedeljni", method = RequestMethod.POST)
	public float nedeljniPrihodi(@RequestBody Posecenost posecenost, HttpServletRequest req){//posecenost ima iste atr koji trebaju i za ovo. 
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==posecenost.getId()) {
			float prihod = rezervacijeService.nedeljniPrihod(posecenost.getId(), posecenost.getDate());
			return prihod;
		}
		return (float) -1.0;
	}
	
	@RequestMapping(value="/prihodi/mesecni", method = RequestMethod.POST)
	public float mesecniPrihodi(@RequestBody Posecenost posecenost, HttpServletRequest req){//posecenost ima iste atr koji trebaju i za ovo. 
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==posecenost.getId()) {
			float prihod = rezervacijeService.mesecniPrihod(posecenost.getId(), posecenost.getDate());
			return prihod;
		}
		return (float) -1.0;
	}
	
	@RequestMapping(value="/prihodi/godisnji", method = RequestMethod.POST)
	public float godisnjiPrihodi(@RequestBody Posecenost posecenost, HttpServletRequest req){//posecenost ima iste atr koji trebaju i za ovo.
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==posecenost.getId()) {
			float prihod = rezervacijeService.godisnjiPrihod(posecenost.getId(), posecenost.getDate());
			return prihod;
		}
		return (float) -1.0;
	}
	
}
