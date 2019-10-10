package com.isa.hoteli.hoteliservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.dto.RezervacijeDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.HotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;
import com.isa.hoteli.hoteliservice.service.RezervacijeService;

@RunWith(SpringRunner.class)
public class RezervacijaServiceTest {
	
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.MASTER_ADMIN, null, true, "a", null, null, null, null, null, null, 0);
	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis());
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private Rezervacije r1 = new Rezervacije(1l, datumOd, datumDo, 200, 2, soba1, k, hotel1);
	private Rezervacije r2 = new Rezervacije(1l, datumOd, datumDo, 200, 2, soba1, k, hotel1);
	private List<Rezervacije> rezervacije = new ArrayList<>();
	
	@Mock
	private RezervacijeRepository rezervacijeRepository;
	
	@Mock
	private HotelskaSobaRepository hotelskaSobaRepository;
	
	@InjectMocks
	private RezervacijeService rezervacijeService;
	
	@Before
	public void setUp() {
		rezervacije.add(r1);
		rezervacije.add(r2);
	}
	
	@Test
	public void getAllSuccess() {
		when(rezervacijeRepository.findAll()).thenReturn(rezervacije);
		List<Rezervacije> reservations = rezervacijeService.getRezervations();
		assertEquals(rezervacije, reservations);
		verify(rezervacijeRepository, times(1)).findAll();
		verifyNoMoreInteractions(rezervacijeRepository);
	}
	
	@Test
	public void getAllFromHotelRoomSuccess() {
		when(rezervacijeRepository.getAllFromHotelRoom(1l)).thenReturn(rezervacije);
		List<Rezervacije> reservations = rezervacijeService.getReservationsFromHotelRoom(1l);
		assertEquals(rezervacije, reservations);
		verify(rezervacijeRepository, times(1)).getAllFromHotelRoom(1l);
		verifyNoMoreInteractions(rezervacijeRepository);
	}
	
	@Test
	public void getAllFromUserSuccess() {
		when(rezervacijeRepository.findByKorisnik(1l)).thenReturn(rezervacije);
		List<Rezervacije> reservations = rezervacijeService.getReservationsFromUser(1l);
		assertEquals(rezervacije, reservations);
		verify(rezervacijeRepository, times(1)).findByKorisnik(1l);
		verifyNoMoreInteractions(rezervacijeRepository);
	}
	
	@Test
	public void getByIdSuccess() {
		when(rezervacijeRepository.getOne(1l)).thenReturn(r1);
		Rezervacije reservation = rezervacijeService.getReservationById(1l);
		assertEquals(r1, reservation);
		verify(rezervacijeRepository, times(1)).getOne(1l);
		verifyNoMoreInteractions(rezervacijeRepository);
	}
	
	@Test
	public void createReservationSuccess() {
		when(hotelskaSobaRepository.getOne(1l)).thenReturn(soba1);
		when(rezervacijeRepository.findKonfliktRezervacije(1l, datumOd, datumDo)).thenReturn(new ArrayList<>());
		when(rezervacijeRepository.save(r1)).thenReturn(r1);
		RezervacijeDTO dto = rezervacijeService.createReservation(r1);
		assertThat(dto.equals(new RezervacijeDTO(r1)));
		verify(hotelskaSobaRepository, times(1)).getOne(1l);
		verify(rezervacijeRepository, times(1)).findKonfliktRezervacije(1l, datumOd, datumDo);
		verify(rezervacijeRepository, times(1)).save(r1);
		verifyNoMoreInteractions(hotelskaSobaRepository);
		verifyNoMoreInteractions(rezervacijeRepository);
	}
	
	@Test
	public void updateReservationSuccess() {
		when(rezervacijeRepository.getOne(1l)).thenReturn(r1);
		when(rezervacijeRepository.save(r1)).thenReturn(r1);
		RezervacijeDTO dto = rezervacijeService.updateReservation(r1, 1l);
		assertThat(dto.equals(new RezervacijeDTO(r1)));
		verify(rezervacijeRepository, times(1)).getOne(1l);
		verify(rezervacijeRepository, times(1)).save(r1);
		verifyNoMoreInteractions(rezervacijeRepository);
	}
	
	@Test
	public void dnevnaPosecenostSuccess() {
		when(rezervacijeRepository.dnevnaPosecenost(1l, datumOd)).thenReturn(200);
		int a = rezervacijeService.dnevnaPosecenost(1l, datumOd);
		assertEquals(a, 200);
		verify(rezervacijeRepository, times(2)).dnevnaPosecenost(1l, datumOd);
		verifyNoMoreInteractions(rezervacijeRepository);
	}

}
