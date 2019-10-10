package com.isa.hoteli.hoteliservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.dto.OcenaHotelDTO;
import com.isa.hoteli.hoteliservice.dto.OcenaHotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.OcenaHotel;
import com.isa.hoteli.hoteliservice.model.OcenaHotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.OcenaHotelRepository;
import com.isa.hoteli.hoteliservice.repository.OcenaHotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;
import com.isa.hoteli.hoteliservice.service.OcenaService;

@RunWith(SpringRunner.class)
public class OcenaServiceTest {
	
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.MASTER_ADMIN, null, true, "a", null, null, null, null, null, null, 0);
	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis());
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private Date datum = new Date(System.currentTimeMillis());
	private List<OcenaHotel> oceneHotela = new ArrayList<>();
	private OcenaHotel ocenaHotela1 = new OcenaHotel(1l, 3, datum, 1l, 1l, 1l);
	private OcenaHotel ocenaHotela2 = new OcenaHotel(2l, 4, datum, 1l, 1l, 1l);
	private List<OcenaHotelskaSoba> oceneSoba = new ArrayList<>();
	private OcenaHotelskaSoba ocenaSoba1 = new OcenaHotelskaSoba(1l, 3, datum, 1l, 1l, 1l);
	private OcenaHotelskaSoba ocenaSoba2 = new OcenaHotelskaSoba(2l, 5, datum, 1l, 1l, 1l);
	private Rezervacije r = new Rezervacije(1l, datumOd, datumDo, 200, 2, soba1, k, hotel1);

	
	@Mock
	private OcenaHotelRepository ocenaHotelRepository;
	
	@Mock
	private OcenaHotelskaSobaRepository ocenaHotelskaSobaRepository;
	
	@Mock
	private RezervacijeRepository rezervacijeRepository;
	
	@InjectMocks
	private OcenaService ocenaService;
	
	@Before
	public void setUp() {
		oceneHotela.add(ocenaHotela1);
		oceneHotela.add(ocenaHotela2);
		oceneSoba.add(ocenaSoba1);
		oceneSoba.add(ocenaSoba2);
	}
	
	@Test
	public void getAllHotelRatingsSuccess() {
		when(ocenaHotelRepository.findAll()).thenReturn(oceneHotela);
		List<OcenaHotel> ocene = ocenaService.getHotelRatings();
		assertEquals(ocene, oceneHotela);
		verify(ocenaHotelRepository, times(1)).findAll();
		verifyNoMoreInteractions(ocenaHotelRepository);
	}
	
	@Test
	public void getAllHotelRoomRatingsSuccess() {
		when(ocenaHotelskaSobaRepository.findAll()).thenReturn(oceneSoba);
		List<OcenaHotelskaSoba> ocene = ocenaService.getRoomRatings();
		assertEquals(ocene, oceneSoba);
		verify(ocenaHotelskaSobaRepository, times(1)).findAll();
		verifyNoMoreInteractions(ocenaHotelskaSobaRepository);
	}
	
	@Test
	public void createHotelRatingSuccess() {
		when(rezervacijeRepository.getOne(1l)).thenReturn(r);
		when(ocenaHotelRepository.vecOcenjeno(1l)).thenReturn(null);
		when(ocenaHotelRepository.save(ocenaHotela1)).thenReturn(ocenaHotela1);
		OcenaHotelDTO ocena = ocenaService.createHotelRating(ocenaHotela1);
		assertThat(ocena.equals(new OcenaHotelDTO(ocenaHotela1)));
		verify(rezervacijeRepository, times(1)).getOne(1l);
		verify(ocenaHotelRepository, times(1)).vecOcenjeno(1l);
		verify(ocenaHotelRepository, times(1)).save(ocenaHotela1);
		verifyNoMoreInteractions(rezervacijeRepository);
		verifyNoMoreInteractions(ocenaHotelRepository);		
	}
	
	@Test
	public void createHotelRatingFailed() {
		when(rezervacijeRepository.getOne(1l)).thenReturn(null);
		OcenaHotelDTO ocena = ocenaService.createHotelRating(ocenaHotela1);
		assertNull(ocena);
		verify(rezervacijeRepository, times(1)).getOne(1l);
		verifyNoMoreInteractions(rezervacijeRepository);		
	}
	
	@Test
	public void createHotelRoomRatingSuccess() {
		when(rezervacijeRepository.getOne(1l)).thenReturn(r);
		when(ocenaHotelskaSobaRepository.vecOcenjeno(1l)).thenReturn(null);
		when(ocenaHotelskaSobaRepository.save(ocenaSoba1)).thenReturn(ocenaSoba1);
		OcenaHotelskaSobaDTO ocena = ocenaService.createHotelRoomRating(ocenaSoba1);
		assertThat(ocena.equals(new OcenaHotelskaSobaDTO(ocenaSoba1)));
		verify(rezervacijeRepository, times(1)).getOne(1l);
		verify(ocenaHotelskaSobaRepository, times(1)).vecOcenjeno(1l);
		verify(ocenaHotelskaSobaRepository, times(1)).save(ocenaSoba1);
		verifyNoMoreInteractions(rezervacijeRepository);
		verifyNoMoreInteractions(ocenaHotelRepository);	
	}
	
	@Test
	public void createHotelRoomRatingFailed() {
		when(rezervacijeRepository.getOne(1l)).thenReturn(null);
		OcenaHotelskaSobaDTO ocena = ocenaService.createHotelRoomRating(ocenaSoba1);
		assertNull(ocena);
		verify(rezervacijeRepository, times(1)).getOne(1l);
		verifyNoMoreInteractions(rezervacijeRepository);		
	}
	
	@Test
	public void getMeanHotelRatingSuccess() {
		when(ocenaHotelRepository.ocene(1l)).thenReturn(oceneHotela);
		when(ocenaHotelRepository.prosek(1l)).thenReturn(3.4f);
		float f = ocenaService.getMeanHotelRating(1l);
		Assert.assertEquals(3.4f, f, 0.0f);
		verify(ocenaHotelRepository, times(1)).ocene(1l);
		verify(ocenaHotelRepository, times(1)).prosek(1l);
		verifyNoMoreInteractions(ocenaHotelRepository);	
	}
	
	@Test
	public void getMeanHotelRoomRatingSuccess() {
		when(ocenaHotelskaSobaRepository.ocene(1l)).thenReturn(oceneSoba);
		when(ocenaHotelskaSobaRepository.prosek(1l)).thenReturn(3.4f);
		float f = ocenaService.getMeanRoomRating(1l);
		Assert.assertEquals(3.4f, f, 0.0f);
		verify(ocenaHotelskaSobaRepository, times(1)).ocene(1l);
		verify(ocenaHotelskaSobaRepository, times(1)).prosek(1l);
		verifyNoMoreInteractions(ocenaHotelskaSobaRepository);	
	}

}
