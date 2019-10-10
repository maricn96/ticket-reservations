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
import com.isa.hoteli.hoteliservice.dto.TipSobeDTO;
import com.isa.hoteli.hoteliservice.model.TipSobe;
import com.isa.hoteli.hoteliservice.service.TipSobeService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tip_sobe")
public class TipSobeController {

	@Autowired
	private TipSobeService tipSobeService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<TipSobeDTO>> getTypes(){
		List<TipSobeDTO> dto = new ArrayList<>();
		List<TipSobe> tipovi = tipSobeService.getTypes();
		for (TipSobe tip : tipovi) {
			dto.add(new TipSobeDTO(tip));
		}
		return new ResponseEntity<List<TipSobeDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/all/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<TipSobeDTO>> getTypesFromHotel(@PathVariable("id") Long id){
		List<TipSobeDTO> dto = new ArrayList<>();
		List<TipSobe> tipovi = tipSobeService.getTypesFromHotel(id);
		for (TipSobe tip : tipovi) {
			dto.add(new TipSobeDTO(tip));
		}
		return new ResponseEntity<List<TipSobeDTO>>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<TipSobeDTO> getTypeById(@PathVariable("id") Long id){
		if(tipSobeService.getTipSobeById(id)!=null) {
			TipSobeDTO dto = new TipSobeDTO(tipSobeService.getTipSobeById(id));
			return new ResponseEntity<TipSobeDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<TipSobeDTO> createType(@RequestBody TipSobeDTO dto, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA) && k.getZaduzenZaId()==dto.getHotel().getId()) {
			TipSobe tipSobe = new TipSobe(dto);
			TipSobeDTO returnType = tipSobeService.createType(tipSobe);
			if(returnType!=null) {
				return new ResponseEntity<>(returnType, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTypeById(@PathVariable("id") Long id, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.ADMIN_HOTELA)) {
			return new ResponseEntity<String>(tipSobeService.deleteType(id), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TipSobeDTO> updateTypeById(@PathVariable("id") Long id, @RequestBody TipSobeDTO dto){
		TipSobe tipSobe = new TipSobe(dto);
		TipSobeDTO returnTip = tipSobeService.updateType(tipSobe, id);
		if(returnTip!=null) {
			return new ResponseEntity<>(returnTip, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
}
