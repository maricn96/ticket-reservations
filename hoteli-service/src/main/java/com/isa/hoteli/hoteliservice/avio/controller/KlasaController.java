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

import com.isa.hoteli.hoteliservice.avio.dto.KlasaDTO;
import com.isa.hoteli.hoteliservice.avio.service.KlasaService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/class")
public class KlasaController
{
	@Autowired
	private KlasaService klasaService;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<KlasaDTO> getKlasa(@PathVariable Long id)
	{
		System.out.println("getKlasa()");
		
		KlasaDTO klasaDto = klasaService.findById(id);
		
		return (klasaDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<KlasaDTO>(klasaDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<KlasaDTO>> getAllKlase()
	{
		System.out.println("getAllKlase()");
		
		List<KlasaDTO> listDto = klasaService.findAll();
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<KlasaDTO>>(listDto, HttpStatus.OK);
	}
	
	
	@PostMapping("/add/")
	public ResponseEntity<KlasaDTO> addKlasa(@RequestBody KlasaDTO dto)
	{
		System.out.println("addKlasa()");
		
		return (klasaService.saveOne(dto) == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<KlasaDTO>(dto, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<KlasaDTO> updateKlasa(@PathVariable("id") Long id, @RequestBody KlasaDTO dto)
	{
		System.out.println("updateKlasa()");
		
		KlasaDTO avio = klasaService.updateOne(id, dto);
		
		return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<KlasaDTO>(avio, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteKlasa(@PathVariable("id") Long id)
	{
		System.out.println("deleteKlasa()");
		
		return (!klasaService.deleteOne(id)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.OK); 
	}

}