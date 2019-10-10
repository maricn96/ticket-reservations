package com.isa.hoteli.hoteliservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Pretraga;
import com.isa.hoteli.hoteliservice.repository.HotelRepository;

@Component
public class PretragaService {
	
	@Autowired 
	private HotelRepository hotelRepository;
	
	@Autowired
	private HotelskaSobaService hotelskaSobaService;
	
	@Transactional(readOnly = true)
	public List<Hotel> getSearch(Pretraga pretraga){
		List<Hotel> returnList = new ArrayList<Hotel>();
		List<HotelskaSoba> sobe = new ArrayList<HotelskaSoba>();
		List<Hotel> lista = new ArrayList<Hotel>();
		List<Hotel> sviHoteli = hotelRepository.findAll();

		returnList.addAll(sviHoteli);
		
		if(pretraga.getNazivAdresa()!=null) {
			String nazivAdresa = pretraga.getNazivAdresa();
			for (Hotel hotel : sviHoteli) {
				if(hotel.getAdresa().contains(nazivAdresa) || hotel.getNaziv().contains(nazivAdresa)) {
					lista.add(hotel);
				}
			}
			
			returnList.clear();
			returnList.addAll(lista);
			lista.clear();
		}
		
		if(pretraga.getDatumOd()!=null && pretraga.getDatumDo()!=null) {
			for (Hotel hotel : returnList) {
				sobe = hotelskaSobaService.getFreeRoomsFromHotel(hotel.getId(), pretraga.getDatumOd(), pretraga.getDatumDo());//vraca sve sobe ovog hotela koje su slobodne
				if(!sobe.isEmpty()) {
					lista.add(hotel);
				}
			}
			
			returnList.clear();
			returnList.addAll(lista);
			lista.clear();
			
			if(pretraga.getBrojSoba()>0) {
				for (Hotel hotel : returnList) {
					sobe = hotelskaSobaService.getFreeRoomsFromHotel(hotel.getId(), pretraga.getDatumOd(), pretraga.getDatumDo());//vraca sve sobe ovog hotela koje su slobodne
					if(sobe.size()>=pretraga.getBrojSoba()) {
						lista.add(hotel);
					}
				}
				
				returnList.clear();
				returnList.addAll(lista);
				lista.clear();
			}
			
			if(pretraga.getBrojGostiju()>0) {
				int ukupno = 0;
				for (Hotel hotel : returnList) {
					ukupno = 0;
					sobe = hotelskaSobaService.getFreeRoomsFromHotel(hotel.getId(), pretraga.getDatumOd(), pretraga.getDatumDo());//vraca sve sobe ovog hotela koje su slobodne
					for (HotelskaSoba soba : sobe) {
						ukupno+=soba.getBrojKreveta();
					}
					if(ukupno>=pretraga.getBrojGostiju()) {
						lista.add(hotel);
					}
				}
				
				returnList.clear();
				returnList.addAll(lista);
				lista.clear();
				
			}
			
		}
		
		return returnList;

	}

}
