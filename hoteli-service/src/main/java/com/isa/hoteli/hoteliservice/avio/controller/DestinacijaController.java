package com.isa.hoteli.hoteliservice.avio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.avio.dto.DestinacijaDTO;
import com.isa.hoteli.hoteliservice.avio.service.DestinacijaService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/destination")
public class DestinacijaController
{
	@Autowired
	private DestinacijaService destService;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<DestinacijaDTO> getDestinacija(@PathVariable Long id)
	{
		System.out.println("getDestinacija()");
		
		DestinacijaDTO destDto = destService.findById(id);
		
		return (destDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<DestinacijaDTO>(destDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<DestinacijaDTO>> getAllDestinacije()
	{
		System.out.println("getAllDestinacije()");
		
		List<DestinacijaDTO> listDto = destService.findAll();
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<DestinacijaDTO>>(listDto, HttpStatus.OK);
	}
	
	@GetMapping("/getalldestsbycompany/{avioid}")
	public ResponseEntity<List<DestinacijaDTO>> getAllDestinacijeByAvioKompanija(@PathVariable("avioid") Long idAvioKompanije)
	{
		System.out.println("getAllDestinacijeByAvioKompanija()");
		
		List<DestinacijaDTO> listDto = destService.getAllDestinacijeByAvioKompanija(idAvioKompanije);
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<DestinacijaDTO>>(listDto, HttpStatus.OK);
	}
	
	
	@PostMapping("/add/")
	public ResponseEntity<DestinacijaDTO> addDestinacija(@RequestBody DestinacijaDTO dto)
	{
		System.out.println("addDestinacija()");
		
		return (destService.saveOne(dto) == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<DestinacijaDTO>(dto, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<DestinacijaDTO> updateDestinacija(@PathVariable("id") Long id, @RequestBody DestinacijaDTO dto)
	{
		System.out.println("updateDestinacija()");
		
		DestinacijaDTO avio = destService.updateOne(id, dto);
		
		return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<DestinacijaDTO>(avio, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteDestinacija(@PathVariable("id") Long id)
	{
		System.out.println("deleteDestinacija()");
		
		return (!destService.deleteOne(id)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.OK); 
	}

}