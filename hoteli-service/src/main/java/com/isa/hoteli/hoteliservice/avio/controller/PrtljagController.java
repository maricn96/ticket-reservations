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

import com.isa.hoteli.hoteliservice.avio.dto.PrtljagDTO;
import com.isa.hoteli.hoteliservice.avio.service.PrtljagService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/luggage")
public class PrtljagController 
{
	@Autowired
	private PrtljagService prtljagService;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<PrtljagDTO> getPrtljag(@PathVariable Long id)
	{
		System.out.println("getPrtljag()");
		
		PrtljagDTO prtljagDto = prtljagService.findById(id);
		
		return (prtljagDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<PrtljagDTO>(prtljagDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<PrtljagDTO>> getAllPrtljag()
	{
		System.out.println("getAllPrtljag()");
		
		List<PrtljagDTO> listDto = prtljagService.findAll();
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<PrtljagDTO>>(listDto, HttpStatus.OK);
	}
	
	
	@PostMapping("/add/")
	public ResponseEntity<PrtljagDTO> addPrtljag(@RequestBody PrtljagDTO dto)
	{
		System.out.println("addPrtljag()");
		
		return (prtljagService.saveOne(dto) == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<PrtljagDTO>(dto, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<PrtljagDTO> updatePrtljag(@PathVariable("id") Long id, @RequestBody PrtljagDTO dto)
	{
		System.out.println("updatePrtljag()");
		
		PrtljagDTO avio = prtljagService.updateOne(id, dto);
		
		return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<PrtljagDTO>(avio, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deletePrtljag(@PathVariable("id") Long id)
	{
		System.out.println("deletePrtljag()");
		
		return (!prtljagService.deleteOne(id)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.OK); 
	}
}
