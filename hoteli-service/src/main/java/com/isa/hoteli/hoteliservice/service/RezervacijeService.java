package com.isa.hoteli.hoteliservice.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.dto.RezervacijeDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.HotelRepository;
import com.isa.hoteli.hoteliservice.repository.HotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;

@Component
public class RezervacijeService {
	
	@Autowired
	private RezervacijeRepository rezervacijeRepository;
	
	@Autowired
	private HotelskaSobaRepository hotelskaSobaRepository;
	
	@Transactional(readOnly = true)
	public List<Rezervacije> getRezervations(){
		return rezervacijeRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Rezervacije> getReservationsFromHotelRoom(Long id){
		return rezervacijeRepository.getAllFromHotelRoom(id);
	}
	
	@Transactional(readOnly = true)
	public List<Rezervacije> getReservationsFromUser(Long id){
		return rezervacijeRepository.findByKorisnik(id);
	}
	
	@Transactional(readOnly = true)
	public Rezervacije getReservationById(Long id){
		return rezervacijeRepository.getOne(id);
	}
	
	@Transactional(readOnly = false)
	public RezervacijeDTO createReservation(Rezervacije obj) {
		HotelskaSoba hs = hotelskaSobaRepository.getOne(obj.getHotelskaSoba().getId());
		List<Rezervacije> lista = rezervacijeRepository.findKonfliktRezervacije(obj.getHotelskaSoba().getId(), obj.getDatumOd(), obj.getDatumDo());
		if(hs!=null && lista.isEmpty()) {
			obj.setHotel(hs.getHotel());
			return new RezervacijeDTO(rezervacijeRepository.save(obj));
		}
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String deleteReservation(Long id) {
		Rezervacije rezervacije = rezervacijeRepository.getOne(id);
		if(rezervacije!=null) {
			java.util.Date sadasnjost = new java.util.Date();//koji je danas datum
			Date datumOd = rezervacije.getDatumOd();//datum kada pocinje rezervacija
			Calendar c = Calendar.getInstance(); 
			c.setTime(sadasnjost); 
			c.add(Calendar.DATE, 2);//dodamo max dana za otkazivanje na danas
			sadasnjost = c.getTime();
			if(sadasnjost.before(datumOd)) {
				rezervacijeRepository.deleteById(id);
				return "Uspesno obrisana rezervacija sa id: " + id;
			}
			return null;
		}
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public RezervacijeDTO updateReservation(Rezervacije obj, Long id) {
		Rezervacije obj1 = rezervacijeRepository.getOne(id);
		if(obj1!=null) {
			obj1.setKorisnik(obj.getKorisnik());
			obj1.setDatumDo(obj.getDatumDo());
			obj1.setDatumOd(obj.getDatumOd());
			obj1.setUkupnaCena(obj.getUkupnaCena());
			obj1.setBrojOsoba(obj.getBrojOsoba());
			obj1.setHotelskaSoba(obj.getHotelskaSoba());
			rezervacijeRepository.save(obj1);
			return new RezervacijeDTO(obj1);
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	public int dnevnaPosecenost(Long id, Date date) {
		if( rezervacijeRepository.dnevnaPosecenost(id, date)!=null) {
			return rezervacijeRepository.dnevnaPosecenost(id, date);
		}
		return 0;
	}
	
	@Transactional(readOnly = true)
	public int nedeljnaPosecenost(Long id, Date date) {
		LocalDate ld = date.toLocalDate();
		LocalDate nedeljaKasnije = ld.plusWeeks(1);
		java.sql.Date datumDo = java.sql.Date.valueOf( nedeljaKasnije );
		if(rezervacijeRepository.nedeljnaMesecnaPosecenost(id, date, datumDo)!=null) {
			return rezervacijeRepository.nedeljnaMesecnaPosecenost(id, date, datumDo);
		}
		return 0;
	}
	
	@Transactional(readOnly = true)
	public int mesecnaPosecenost(Long id, Date date) {
		LocalDate ld = date.toLocalDate();
		LocalDate mesecKasnije = ld.plusMonths(1);
		java.sql.Date datumDo = java.sql.Date.valueOf( mesecKasnije );
		if(rezervacijeRepository.nedeljnaMesecnaPosecenost(id, date, datumDo)!=null) {
			return rezervacijeRepository.nedeljnaMesecnaPosecenost(id, date, datumDo);
		}
		return 0;
	}
	
	@Transactional(readOnly = true)
	public float nedeljniPrihod(Long id, Date date) {
		LocalDate ld = date.toLocalDate();
		LocalDate nedeljaKasnije = ld.plusWeeks(1);
		java.sql.Date datumDo = java.sql.Date.valueOf( nedeljaKasnije );
		if(rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo)!=null) {
			return rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo);
		}
		return 0;
	}
	
	@Transactional(readOnly = true)
	public float mesecniPrihod(Long id, Date date) {
		LocalDate ld = date.toLocalDate();
		LocalDate mesecKasnije = ld.plusMonths(1);
		java.sql.Date datumDo = java.sql.Date.valueOf( mesecKasnije );
		if(rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo)!=null) {
			return rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo);
		}
		return 0;
	}
	
	@Transactional(readOnly = true)
	public float godisnjiPrihod(Long id, Date date) {
		LocalDate ld = date.toLocalDate();
		LocalDate godinaKasnije = ld.plusYears(1);
		java.sql.Date datumDo = java.sql.Date.valueOf( godinaKasnije );
		if(rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo)!=null) {
			return rezervacijeRepository.nedeljniMesecniGodisnjiPrihod(id, date, datumDo);
		}
		return 0;
	}

}
