package com.isa.hoteli.hoteliservice.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.dto.HotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.CenaNocenjaRepository;
import com.isa.hoteli.hoteliservice.repository.HotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;

@Component
public class HotelskaSobaService {
	
	@Autowired
	private HotelskaSobaRepository hotelskaSobaRepository;
	
	@Autowired
	private RezervacijeRepository rezervacijeRepository;
	
	@Autowired
	private CenaNocenjaRepository cenaNocenjaRepository;
	
	@Transactional(readOnly = true)
	public List<HotelskaSoba> getRooms(){
		return hotelskaSobaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<HotelskaSoba> getRoomsFromHotel(Long id){
		return hotelskaSobaRepository.getAllFromHotel(id);
	}
	
	@Transactional(readOnly = true)
	public List<HotelskaSoba> getFreeRoomsFromHotel(Long id, Date datumOd, Date datumDo){
		List<HotelskaSoba> sobe = hotelskaSobaRepository.getAllFromHotel(id);
		List<HotelskaSoba> returnList = new ArrayList<>();
		for (HotelskaSoba hotelskaSoba : sobe) {
			if(rezervacijeRepository.findKonfliktRezervacije(hotelskaSoba.getId(), datumOd, datumDo).isEmpty()) {
				returnList.add(hotelskaSoba);
			}
		}
		return returnList;

	}
	
	@Transactional(readOnly = true)
	public HotelskaSoba getRoomById(Long id){
		return hotelskaSobaRepository.getOne(id);
	}
	
	@Transactional(readOnly = false)
	public HotelskaSobaDTO createRoom(HotelskaSoba soba) {
		if(hotelskaSobaRepository.getRoomWithNumber(soba.getHotel().getId(), soba.getBrojSobe())==null) {
			return new HotelskaSobaDTO(hotelskaSobaRepository.save(soba));
		}
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String deleteRoom(Long id) {
		Date datum = new Date(System.currentTimeMillis());
		if(rezervacijeRepository.neMozeMenjatiBrisati(id, datum).isEmpty()) {
			hotelskaSobaRepository.deleteById(id);
			return "Uspesno obrisana soba sa id: " + id;
		}else {
			return "Soba se ne moze obrisati jer postoje rezervacije za nju.";
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public HotelskaSobaDTO updateRoom(HotelskaSoba soba, Long id) {
		Date datum = new Date(System.currentTimeMillis());
		List<Rezervacije> lista = rezervacijeRepository.neMozeMenjatiBrisati(id, datum);
		if(lista.isEmpty()){
			HotelskaSoba soba1 = hotelskaSobaRepository.getOne(id);
			if(soba1!=null) {
				soba1.setBrojKreveta(soba.getBrojKreveta());
				soba1.setBrojSobe(soba.getBrojSobe());
				soba1.setSprat(soba.getSprat());
				soba1.setOriginalnaCena(soba.getOriginalnaCena());
				soba1.setTipSobe(soba.getTipSobe());
				soba1.setHotel(soba.getHotel());
				soba1.setVersion(soba1.getVersion()+1l);
				hotelskaSobaRepository.save(soba1);
				return new HotelskaSobaDTO(soba1);
			}
		}
		
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public HotelskaSobaDTO updateRoomPrice(HotelskaSoba soba, Long id) {
			HotelskaSoba soba1 = hotelskaSobaRepository.getOne(id);
			if(soba1!=null) {
				soba1.setOriginalnaCena(soba.getOriginalnaCena());
				soba1.setVersion(soba1.getVersion()+1l);
				hotelskaSobaRepository.save(soba1);
				return new HotelskaSobaDTO(soba1);
			}
		
		return null;
	}
	
	@Transactional(readOnly = true)
	public List<HotelskaSoba> getAllFreeRoomsFromHotel(Long id, Date datumOd, Date datumDo){
		List<HotelskaSoba> sobe = hotelskaSobaRepository.getAllFromHotel(id);
		List<HotelskaSoba> returnList = new ArrayList<>();
		for (HotelskaSoba hotelskaSoba : sobe) {
			if(rezervacijeRepository.findKonfliktRezervacije(hotelskaSoba.getId(), datumOd, datumDo).isEmpty()){
				returnList.add(hotelskaSoba);
			}
		}
		return returnList;
	}

	@Transactional(readOnly = true)
	public List<HotelskaSoba> getAllReservedRoomsFromHotel(Long id, Date datumOd, Date datumDo){
		List<HotelskaSoba> sobe = hotelskaSobaRepository.getAllFromHotel(id);
		List<HotelskaSoba> returnList = new ArrayList<>();
		for (HotelskaSoba hotelskaSoba : sobe) {
			if(!rezervacijeRepository.findKonfliktRezervacije(hotelskaSoba.getId(), datumOd, datumDo).isEmpty()){
				returnList.add(hotelskaSoba);
			}
		}
		return returnList;
	}
	
	@Transactional(readOnly = true)
	public List<HotelskaSoba> getAllFreeRoomsFromHotelWithDiscount(Long id, Date datumOd, Date datumDo){
		List<HotelskaSoba> sobe = hotelskaSobaRepository.getAllFromHotel(id);
		List<HotelskaSoba> returnList = new ArrayList<>();
		List<HotelskaSoba> returnList2 = new ArrayList<>();
		for (HotelskaSoba hotelskaSoba : sobe) {
			if(rezervacijeRepository.findKonfliktRezervacije(hotelskaSoba.getId(), datumOd, datumDo).isEmpty()){
				returnList.add(hotelskaSoba);
			}
		}
		for (HotelskaSoba hotelskaSoba : returnList) {
			if(cenaNocenjaRepository.getValidFromHotelRoom(hotelskaSoba.getId(), datumOd)!=null) {
				if(cenaNocenjaRepository.getValidFromHotelRoom(hotelskaSoba.getId(), datumOd).getCenaNocenja()<hotelskaSoba.getOriginalnaCena()) {
					returnList2.add(hotelskaSoba);
				}
			}
		}
		return returnList2;
	}
	
	@Transactional(readOnly = true)
	public List<HotelskaSoba> getAllFreeRoomsFromHotelWithoutDiscount(Long id, Date datumOd, Date datumDo){
		List<HotelskaSoba> sobe = hotelskaSobaRepository.getAllFromHotel(id);
		List<HotelskaSoba> returnList = new ArrayList<>();
		List<HotelskaSoba> returnList2 = new ArrayList<>();
		for (HotelskaSoba hotelskaSoba : sobe) {
			if(rezervacijeRepository.findKonfliktRezervacije(hotelskaSoba.getId(), datumOd, datumDo).isEmpty()){
				returnList.add(hotelskaSoba);
			}
		}
		for (HotelskaSoba hotelskaSoba : returnList) {
			if(cenaNocenjaRepository.getValidFromHotelRoom(hotelskaSoba.getId(), datumOd)!=null) {
				if(cenaNocenjaRepository.getValidFromHotelRoom(hotelskaSoba.getId(), datumOd).getCenaNocenja()>=hotelskaSoba.getOriginalnaCena()) {
					returnList2.add(hotelskaSoba);
				}
			}else {
				returnList2.add(hotelskaSoba);
			}
		}
		return returnList2;
	}
	
	/*
	 * 
	 * vraca cenu nocenja koja je manja od originalne za neku sobu
	 * 
	 * */
	@Transactional(readOnly = true)
	public float getPriceIfDiscount(Long id, Date datumOd, Date datumDo){
		HotelskaSoba soba = hotelskaSobaRepository.getOne(id);
		List<HotelskaSoba> sobe = getAllFreeRoomsFromHotelWithDiscount(soba.getHotel().getId(), datumOd, datumDo);
		for (HotelskaSoba hotelskaSoba : sobe) {
			if(soba.getId()==hotelskaSoba.getId()){
				if(cenaNocenjaRepository.getValidFromHotelRoom(soba.getId(), datumOd)!=null) {
					if(cenaNocenjaRepository.getValidFromHotelRoom(soba.getId(), datumOd).getCenaNocenja()<hotelskaSoba.getOriginalnaCena()) {
						return cenaNocenjaRepository.getValidFromHotelRoom(soba.getId(), datumOd).getCenaNocenja();
					}
				}
			}
		}
		return -1.0f;
	}
	
}
