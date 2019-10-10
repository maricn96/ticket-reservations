package com.isa.hoteli.hoteliservice.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.dto.HotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.repository.CenaNocenjaRepository;

@Component
public class CenaNocenjaService {
	
	@Autowired
	private CenaNocenjaRepository cenaNocenjaRepository;
	
	@Transactional(readOnly = true)
	public List<CenaNocenja> getPrices(){
		return cenaNocenjaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<CenaNocenja> getPricesFromHotelRoom(Long id){
		return cenaNocenjaRepository.getAllFromHotelRoom(id);
	}
	
	@Transactional(readOnly = true)
	public float getValidPriceFromHotelRoom(Long id){
		if(!cenaNocenjaRepository.getAllFromHotelRoom(id).isEmpty()) {
			Date date = new Date(System.currentTimeMillis());
			CenaNocenja cn = cenaNocenjaRepository.getValidFromHotelRoom(id, date);
			if(cn!=null) {
				return cn.getCenaNocenja();
			}else {
				return (float) -1.0;
			}
		}else {
			return (float) -1.0;
		}
	}
	
	@Transactional(readOnly = true)
	public CenaNocenja getPriceById(Long id){
		return cenaNocenjaRepository.getOne(id);
	}
	
	@Transactional(readOnly = false)
	public CenaNocenjaDTO createPrice(CenaNocenja obj) {
		//proveravam da li vec postoji cena u istom datumskom intervalu
		if(!cenaNocenjaRepository.findKonfliktCeneNocenja(obj.getHotelskaSoba().getId(), obj.getDatumOd(), obj.getDatumDo()).isEmpty()) {
			return null;
		}
		return new CenaNocenjaDTO(cenaNocenjaRepository.save(obj));
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String deletePrice(Long id) {
		cenaNocenjaRepository.deleteById(id);
		return "Uspesno obrisana cena sa id: " + id;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public CenaNocenjaDTO updatePrice(CenaNocenja obj, Long id) {
		CenaNocenja obj1 = cenaNocenjaRepository.getOne(id);
		if(obj1!=null) {
			obj1.setCenaNocenja(obj.getCenaNocenja());
			//staviti datumska ogranicenja ako budem koristio
			obj1.setDatumDo(obj.getDatumDo());
			obj1.setDatumOd(obj.getDatumOd());
			obj1.setHotelskaSoba(obj.getHotelskaSoba());
			cenaNocenjaRepository.save(obj1);
			return new CenaNocenjaDTO(obj1);
		}
		return null;
	}
}
