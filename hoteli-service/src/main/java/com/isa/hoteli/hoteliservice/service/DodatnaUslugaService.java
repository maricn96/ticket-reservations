package com.isa.hoteli.hoteliservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.dto.DodatnaUslugaDTO;
import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.repository.DodatnaUslugaRepository;

@Component
public class DodatnaUslugaService {
	
	@Autowired
	private DodatnaUslugaRepository dodatnaUslugaRepository;
	
	@Transactional(readOnly = true)
	public List<DodatnaUsluga> getServices(){
		return dodatnaUslugaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<DodatnaUsluga> getServicesFromHotel(Long id){
		return dodatnaUslugaRepository.getAllFromHotel(id);
	}
	
	@Transactional(readOnly = true)
	public DodatnaUsluga getServiceById(Long id){
		return dodatnaUslugaRepository.getOne(id);
	}
	
	@Transactional(readOnly = false)
	public DodatnaUslugaDTO createService(DodatnaUsluga obj) {
		return new DodatnaUslugaDTO(dodatnaUslugaRepository.save(obj));
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String deleteService(Long id) {
		dodatnaUslugaRepository.deleteById(id);
		return "Uspesno obrisana usluga sa id: " + id;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public DodatnaUslugaDTO updateService(DodatnaUsluga obj, Long id) {
		DodatnaUsluga obj1 = dodatnaUslugaRepository.getOne(id);
		if(obj1!=null) {
			obj1.setCena(obj.getCena());
			obj1.setNaziv(obj.getNaziv());
			obj1.setPopust(obj.getPopust());
			obj1.setHotel(obj.getHotel());
			obj1.setVersion(obj1.getVersion()+1l);
			dodatnaUslugaRepository.save(obj1);
			return new DodatnaUslugaDTO(obj1);
		}
		return null;
	}

}
