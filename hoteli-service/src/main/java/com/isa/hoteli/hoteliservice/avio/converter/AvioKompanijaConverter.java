package com.isa.hoteli.hoteliservice.avio.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.DestinacijaDTO;
import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;
import com.isa.hoteli.hoteliservice.avio.model.Destinacija;
import com.isa.hoteli.hoteliservice.avio.repository.DestinacijaRepository;

@Component
public class AvioKompanijaConverter
{
	@Autowired
	private DestinacijaConverter destConv;
	
	@Autowired
	private DestinacijaRepository destRepo;
	
	public AvioKompanijaDTO convertToDTO(AvioKompanija model)
	{
		AvioKompanijaDTO dto = new AvioKompanijaDTO();
		
		dto.setIdAvioKompanije(model.getIdAvioKompanije());
		dto.setNaziv(model.getNaziv());
		dto.setAdresa(model.getAdresa());
		dto.setOpis(model.getOpis());
		dto.setLat(model.getLat());
		dto.setLng(model.getLng());
		
		List<DestinacijaDTO> destList = new ArrayList<DestinacijaDTO>();
		
		
		for(Destinacija dest : model.getDestinacijeNaKojimaPosluje())
		{
			destList.add(destConv.convertToDTO(dest));
		}
		
		dto.setDestinacijeNaKojimaPosluje(destList);
		
		return dto;
	}
	
	public AvioKompanija convertFromDTO(AvioKompanijaDTO dto)
	{
		AvioKompanija model = new AvioKompanija();
		
		model.setIdAvioKompanije(dto.getIdAvioKompanije());
		model.setNaziv(dto.getNaziv());
		model.setAdresa(dto.getAdresa());
		model.setOpis(dto.getOpis());
		model.setLat(dto.getLat());
		model.setLng(dto.getLng());
		
		List<Destinacija> destList = new ArrayList<Destinacija>();
		Optional<Destinacija> dest = destRepo.findById((long) 1);
		Optional<Destinacija> dest2 = destRepo.findById((long) 2);
		
		for(DestinacijaDTO destt : dto.getDestinacijeNaKojimaPosluje())
		{
			destList.add(destConv.convertFromDTO(destt));
		}
		
//		destList.add(dest.get());
//		destList.add(dest2.get());
		model.setDestinacijeNaKojimaPosluje(destList);
		
		return model;
		
	}

}
