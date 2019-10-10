package com.isa.hoteli.hoteliservice.avio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.avio.converter.KlasaConverter;
import com.isa.hoteli.hoteliservice.avio.dto.KlasaDTO;
import com.isa.hoteli.hoteliservice.avio.model.Klasa;
import com.isa.hoteli.hoteliservice.avio.repository.KlasaRepository;

@Service
public class KlasaService
{
	@Autowired
	KlasaRepository klasaRepo;
	
	@Autowired
	KlasaConverter klasaConv;
	
	
	@Transactional(readOnly = true)
	public KlasaDTO findById(Long id)
	{
		Optional<Klasa> klasa = klasaRepo.findById(id);
		
		if(klasa.isPresent())
			return klasaConv.convertToDTO(klasa.get());
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public Klasa traziById(Long id)
	{
		Klasa klasa = klasaRepo.getOne(id);
		
		if(klasa != null)
			return klasa;
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public List<KlasaDTO> findAll()
	{
		Optional<List<Klasa>> list = Optional.of(klasaRepo.findAll());
		
		List<KlasaDTO> listDto = new ArrayList<KlasaDTO>();
		
		if(list.isPresent())
		{
			for(Klasa klasa : list.get())
				listDto.add(klasaConv.convertToDTO(klasa));
			
			return listDto;
		}
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public List<Klasa> traziSve()
	{
		return klasaRepo.findAll();
	}
	
	@Transactional(readOnly = false)
	public KlasaDTO saveOne(KlasaDTO dto)
	{
		Optional<Klasa> klasa = klasaRepo.findById(dto.getIdKlase());
						
		if(klasa.isPresent())
			return null;
		else
		{			
			klasaRepo.save(klasaConv.convertFromDTO(dto));
			return dto;
		}
	}
	
	@Transactional(readOnly = false)
	public Klasa saveOne(Klasa dto)
	{
		Klasa klasa = klasaRepo.getOne(dto.getIdKlase());
						
		if(klasa != null)
			return null;
		else
		{			
			klasaRepo.save(dto);
			return dto;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public KlasaDTO updateOne(Long id, KlasaDTO dto)
	{
		Optional<Klasa> klasa = klasaRepo.findById(id);
		
		if(klasa.isPresent())
		{
			klasa.get().setIdKlase(klasaConv.convertFromDTO(dto).getIdKlase());
			klasa.get().setNaziv(klasaConv.convertFromDTO(dto).getNaziv());
			
			klasaRepo.save(klasa.get());
			
			return klasaConv.convertToDTO(klasa.get());
		}
		else
			return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Klasa updateOne(Long id, Klasa dto)
	{
		Klasa klasa = klasaRepo.getOne(id);
		
		if(klasa != null)
		{
			klasa.setIdKlase(dto.getIdKlase());
			klasa.setNaziv(dto.getNaziv());
			
			klasaRepo.save(klasa);
			
			return klasa;
		}
		else
			return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean deleteOne(Long id)
	{
		Klasa klasa = klasaRepo.getOne(id);
		
		if(klasa != null)
		{
			klasaRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
}