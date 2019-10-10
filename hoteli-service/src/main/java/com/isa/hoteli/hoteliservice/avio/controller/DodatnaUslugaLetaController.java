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

import com.isa.hoteli.hoteliservice.avio.dto.DodatnaUslugaLetaDTO;
import com.isa.hoteli.hoteliservice.avio.service.DodatnaUslugaLetaService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/service")
public class DodatnaUslugaLetaController 
{
	@Autowired
	private DodatnaUslugaLetaService uslugaService;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<DodatnaUslugaLetaDTO> getDodatnaUsluga(@PathVariable Long id)
	{
		System.out.println("getDodatnaUsluga()");
		
		DodatnaUslugaLetaDTO uslDto = uslugaService.findById(id);
		
		return (uslDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<DodatnaUslugaLetaDTO>(uslDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<DodatnaUslugaLetaDTO>> getAllDodatneUsluge()
	{
		System.out.println("getAllDodatneUsluge()");
		
		List<DodatnaUslugaLetaDTO> listDto = uslugaService.findAll();
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<DodatnaUslugaLetaDTO>>(listDto, HttpStatus.OK);
	}
	
	
	@PostMapping("/add/")
	public ResponseEntity<DodatnaUslugaLetaDTO> addDodatnaUsluga(@RequestBody DodatnaUslugaLetaDTO dto)
	{
		System.out.println("addDodatnaUsluga()");
		
		return (uslugaService.saveOne(dto) == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<DodatnaUslugaLetaDTO>(dto, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<DodatnaUslugaLetaDTO> updateDodatnaUsluga(@PathVariable("id") Long id, @RequestBody DodatnaUslugaLetaDTO dto)
	{
		System.out.println("updateDodatnaUsluga()");
		
		DodatnaUslugaLetaDTO avio = uslugaService.updateOne(id, dto);
		
		return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<DodatnaUslugaLetaDTO>(avio, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteDodatnaUsluga(@PathVariable("id") Long id)
	{
		System.out.println("deleteDodatnaUsluga()");
		
		return (!uslugaService.deleteOne(id)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.OK); 
	}
}
