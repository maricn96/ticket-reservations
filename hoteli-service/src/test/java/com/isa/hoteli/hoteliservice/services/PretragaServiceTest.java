package com.isa.hoteli.hoteliservice.services;

import static org.junit.Assert.assertEquals;
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

import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Pretraga;
import com.isa.hoteli.hoteliservice.repository.HotelRepository;
import com.isa.hoteli.hoteliservice.service.HotelskaSobaService;
import com.isa.hoteli.hoteliservice.service.PretragaService;

@RunWith(SpringRunner.class)
public class PretragaServiceTest {

	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis());
	private List<Hotel> hotels = new ArrayList<>();;
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private Hotel hotel2 = new Hotel(2l, "b", "b", "b", "b", 2f, 2f);
	private List<HotelskaSoba> sobe = new ArrayList<>();
	private List<Hotel> hotelsRet = new ArrayList<>();
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private HotelskaSoba soba2 = new HotelskaSoba(2l, 2, 2, 2, 400, hotel1, null);
	private Pretraga pretraga = new Pretraga("a", datumOd, datumDo, 1, 1);
	
	@Mock
	private HotelRepository hotelRepository;
	
	@Mock
	private HotelskaSobaService hotelskaSobaService;
	
	@InjectMocks
	private PretragaService pretragaService;
	
	@Before
	public void setUp() {
		sobe.add(soba1);
		sobe.add(soba2);
		hotelsRet.add(hotel1);
		hotels.add(hotel1);
		hotels.add(hotel2);
	}
	
	@Test
	public void searchSuccess() {
		when(hotelRepository.findAll()).thenReturn(hotels);
		when(hotelskaSobaService.getFreeRoomsFromHotel(1l, datumOd, datumDo)).thenReturn(sobe);
		List<Hotel> hoteli = pretragaService.getSearch(pretraga);
		assertEquals(hotelsRet, hoteli);
		verify(hotelRepository, times(1)).findAll();
		verify(hotelskaSobaService, times(3)).getFreeRoomsFromHotel(1l, datumOd, datumDo);
		verifyNoMoreInteractions(hotelRepository);
		verifyNoMoreInteractions(hotelskaSobaService);
	}
	
}
