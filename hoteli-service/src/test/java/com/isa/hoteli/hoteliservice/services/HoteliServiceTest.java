package com.isa.hoteli.hoteliservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.dto.TipSobeDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.repository.HotelRepository;
import com.isa.hoteli.hoteliservice.service.HotelService;

@RunWith(SpringRunner.class)
public class HoteliServiceTest {
	
	private List<Hotel> hotels = new ArrayList<>();;
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private Hotel hotel2 = new Hotel(2l, "b", "b", "b", "b", 2f, 2f);
	private HotelDTO dto;
	
	@Mock
	private HotelRepository hotelRepository;
	
	@InjectMocks
	private HotelService hotelService;
	
	@Before
	public void setUp() {
		hotels.add(hotel1);
		hotels.add(hotel2);		
	}
	
	@Test
	public void getAllSuccess() {
		when(hotelRepository.findAll()).thenReturn(hotels);
		List<Hotel> hoteli = hotelService.getHotels();
		assertEquals(hotels, hoteli);
		verify(hotelRepository, times(1)).findAll();
		verifyNoMoreInteractions(hotelRepository);
	}
	
	@Test
	public void getHotelByIdSuccess() {
		when(hotelRepository.getOne(1l)).thenReturn(hotel1);
		Hotel hotel = hotelService.getHotelById(1l);
		assertEquals(hotel1, hotel);
		verify(hotelRepository, times(1)).getOne(1l);
		verifyNoMoreInteractions(hotelRepository);
	}
	
	@Test
	public void createHotelSuccess() {
		when(hotelRepository.save(hotel1)).thenReturn(hotel1);
		HotelDTO hotel = hotelService.createHotel(hotel1);
		assertThat(hotel.equals(new HotelDTO(hotel1)));
		verify(hotelRepository, times(1)).save(hotel1);
		verifyNoMoreInteractions(hotelRepository);
	}
	
	@Test
	public void updateHotelSuccess() {
		when(hotelRepository.getOne(1l)).thenReturn(hotel1);
		when(hotelRepository.save(hotel1)).thenReturn(hotel1);
		HotelDTO hotel = hotelService.updateHotel(hotel1, 1l);
		assertThat(hotel.equals(new HotelDTO(hotel1)));
		verify(hotelRepository, times(1)).getOne(1l);
		verify(hotelRepository, times(1)).save(hotel1);
		verifyNoMoreInteractions(hotelRepository);
	}
	
	@Test
	public void updateHotelFailed() {
		when(hotelRepository.getOne(1l)).thenReturn(null);
		hotelService.updateHotel(hotel1, 1l);
		verify(hotelRepository, times(1)).getOne(1l);
		verifyNoMoreInteractions(hotelRepository);
	}
	
	@Test
	public void deleteHotelSuccess() {
		doNothing().when(hotelRepository).deleteById(1l);
		String s = hotelService.deleteHotel(1l);
		assertEquals("Uspesno obrisan hotel sa id: 1", s);
		verify(hotelRepository, times(1)).deleteById(1l);
		verifyNoMoreInteractions(hotelRepository);
	}
}
