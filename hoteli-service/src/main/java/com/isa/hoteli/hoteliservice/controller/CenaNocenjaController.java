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
import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.service.CenaNocenjaService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/cena")
public class CenaNocenjaController {
	
	@Autowired
	private CenaNocenjaService cenaNocenjaService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<CenaNocenjaDTO>> getPrices(){
		List<CenaNocenjaDTO> dto = new ArrayList<>();
		List<CenaNocenja> lista = cenaNocenjaService.getPrices();
		for (CenaNocenja item : lista) {
			dto.add(new CenaNocenjaDTO(item));
		}
		return new ResponseEntity<List<CenaNocenjaDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/all/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<CenaNocenjaDTO>> getPricesFromHotelRooms(@PathVariable("id") Long id){
		List<CenaNocenjaDTO> dto = new ArrayList<>();
		List<CenaNocenja> lista = cenaNocenjaService.getPricesFromHotelRoom(id);
		for (CenaNocenja item : lista) {
			dto.add(new CenaNocenjaDTO(item));
		}
		return new ResponseEntity<List<CenaNocenjaDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<CenaNocenjaDTO> getPricesById(@PathVariable("id") Long id){
		if(cenaNocenjaService.getPriceById(id)!=null) {
			CenaNocenjaDTO dto = new CenaNocenjaDTO(cenaNocenjaService.getPriceById(id));
			return new ResponseEntity<CenaNocenjaDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<CenaNocenjaDTO> createPrice(@RequestBody CenaNocenjaDTO dto, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==dto.getHotelskaSoba().getHotel().getId()) {
			CenaNocenja obj = new CenaNocenja(dto);
			CenaNocenjaDTO returnType = cenaNocenjaService.createPrice(obj);
			if(returnType!=null) {
				return new ResponseEntity<>(returnType, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePriceById(@PathVariable("id") Long id, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==cenaNocenjaService.getPriceById(id).getHotelskaSoba().getHotel().getId()) {
			return new ResponseEntity<String>(cenaNocenjaService.deletePrice(id), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CenaNocenjaDTO> updatePriceById(@PathVariable("id") Long id, @RequestBody CenaNocenjaDTO dto){
		CenaNocenja obj = new CenaNocenja(dto);
		CenaNocenjaDTO returnTip = cenaNocenjaService.updatePrice(obj, id);
		if(returnTip!=null) {
			return new ResponseEntity<>(returnTip, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
