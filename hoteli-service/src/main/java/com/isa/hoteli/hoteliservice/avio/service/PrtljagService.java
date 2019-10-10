package com.isa.hoteli.hoteliservice.avio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.avio.converter.PrtljagConverter;
import com.isa.hoteli.hoteliservice.avio.dto.PrtljagDTO;
import com.isa.hoteli.hoteliservice.avio.model.Prtljag;
import com.isa.hoteli.hoteliservice.avio.repository.PrtljagRepository;

@Service
public class PrtljagService
{
	@Autowired
	PrtljagRepository prtljagRepo;
	
	@Autowired
	PrtljagConverter prtljagConv;
	
	
	@Transactional(readOnly = true)
	public PrtljagDTO findById(Long id)
	{
		Optional<Prtljag> prtljag = prtljagRepo.findById(id);
		
		if(prtljag.isPresent())
			return prtljagConv.convertToDTO(prtljag.get());
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public Prtljag traziById(Long id)
	{
		Prtljag prtljag = prtljagRepo.getOne(id);
		
		if(prtljag != null)
			return prtljag;
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public List<PrtljagDTO> findAll()
	{
		Optional<List<Prtljag>> list = Optional.of(prtljagRepo.findAll());
		
		List<PrtljagDTO> listDto = new ArrayList<PrtljagDTO>();
		
		if(list.isPresent())
		{
			for(Prtljag prtljag : list.get())
				listDto.add(prtljagConv.convertToDTO(prtljag));
			
			return listDto;
		}
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public List<Prtljag> traziSve()
	{
		return prtljagRepo.findAll();
	}
	
	@Transactional(readOnly = false)
	public PrtljagDTO saveOne(PrtljagDTO dto)
	{
		Optional<Prtljag> prtljag = prtljagRepo.findById(dto.getIdPrtljaga());
						
		if(prtljag.isPresent())
			return null;
		else
		{			
			prtljagRepo.save(prtljagConv.convertFromDTO(dto));
			return dto;
		}
	}
	
	@Transactional(readOnly = false)
	public Prtljag saveOne(Prtljag dto)
	{
		Prtljag prtljag = prtljagRepo.getOne(dto.getIdPrtljaga());
						
		if(prtljag != null)
			return null;
		else
		{			
			prtljagRepo.save(dto);
			return dto;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public PrtljagDTO updateOne(Long id, PrtljagDTO dto)
	{
		Optional<Prtljag> prtljag = prtljagRepo.findById(id);
		
		if(prtljag.isPresent())
		{
			prtljag.get().setIdPrtljaga(prtljagConv.convertFromDTO(dto).getIdPrtljaga());
			prtljag.get().setTezina(prtljagConv.convertFromDTO(dto).getTezina());
			prtljag.get().setOpis(prtljagConv.convertFromDTO(dto).getOpis());
			
			prtljagRepo.save(prtljag.get());
			
			return prtljagConv.convertToDTO(prtljag.get());
		}
		else
			return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Prtljag updateOne(Long id, Prtljag dto)
	{
		Prtljag prtljag = prtljagRepo.getOne(id);
		
		if(prtljag != null)
		{
			prtljag.setIdPrtljaga(dto.getIdPrtljaga());
			prtljag.setTezina(dto.getTezina());
			prtljag.setOpis(dto.getOpis());
			
			prtljagRepo.save(prtljag);
			
			return prtljag;
		}
		else
			return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean deleteOne(Long id)
	{
		Prtljag prtljag = prtljagRepo.getOne(id);
		
		if(prtljag != null)
		{
			prtljagRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
}