package com.isa.hoteli.hoteliservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.isa.hoteli.hoteliservice.model.TipSobe;
import com.isa.hoteli.hoteliservice.repository.TipSobeRepository;
import com.isa.hoteli.hoteliservice.service.TipSobeService;

@RunWith(SpringRunner.class)
public class TipSobeServiceTest {
	
	private List<TipSobe> tipovi = new ArrayList<>();
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private Hotel hotel2 = new Hotel(2l, "b", "b", "b", "b", 2f, 2f);
	private TipSobe tipSobe1 = new TipSobe(1l, "a", hotel1);
	private TipSobe tipSobe2 = new TipSobe(2l, "b", hotel1);
	private TipSobeDTO dto;
	
	@Mock
	private TipSobeRepository tsr;
	
	@InjectMocks
	private TipSobeService tipSobeService;
	
	@Before
	public void setUp() {
		tipovi.add(tipSobe1);
		tipovi.add(tipSobe2);
	}
	
	@Test
	public void getAllSuccess() {
		when(tsr.findAll()).thenReturn(tipovi);
		List<TipSobe> types = tipSobeService.getTypes();
		assertEquals(tipovi, types);
		verify(tsr, times(1)).findAll();
		verifyNoMoreInteractions(tsr);
	}
	
	@Test
	public void getTypesFromHotelSuccess() {
		when(tsr.getAllFromHotel(1l)).thenReturn(tipovi);
		List<TipSobe> types = tipSobeService.getTypesFromHotel(1l);
		assertEquals(tipovi, types);
		verify(tsr, times(1)).getAllFromHotel(1l);
		verifyNoMoreInteractions(tsr);
	}
	
	@Test
	public void getTypeByIdSuccess() {
		when(tsr.getOne(1l)).thenReturn(tipSobe1);
		TipSobe tip = tipSobeService.getTipSobeById(1l);
		assertEquals(tipSobe1, tip);
		verify(tsr, times(1)).getOne(1l);
		verifyNoMoreInteractions(tsr);
	}
	
	@Test
	public void createTypeSuccess() {
		when(tsr.save(tipSobe1)).thenReturn(tipSobe1);
		TipSobeDTO tipSobeDTO = tipSobeService.createType(tipSobe1);
		assertThat(tipSobeDTO.equals(new TipSobeDTO(tipSobe1)));
		verify(tsr, times(1)).save(tipSobe1);
		verifyNoMoreInteractions(tsr);
	}
	
	@Test
	public void updateTypeSuccess() {
		when(tsr.getOne(1l)).thenReturn(tipSobe1);
		when(tsr.save(tipSobe1)).thenReturn(tipSobe1);
		TipSobeDTO tipSobeDTO = tipSobeService.updateType(tipSobe1, 1l);
		assertThat(tipSobeDTO.equals(new TipSobeDTO(tipSobe1)));
		verify(tsr, times(1)).getOne(1l);
		verify(tsr, times(1)).save(tipSobe1);
		verifyNoMoreInteractions(tsr);
	}
	
	@Test
	public void updateTypeFailed() {
		when(tsr.getOne(1l)).thenReturn(null);
		tipSobeService.updateType(tipSobe1, 1l);
		verify(tsr, times(1)).getOne(1l);
		verifyNoMoreInteractions(tsr);
	}
	
	@Test
	public void deleteTypeSuccess() {
		doNothing().when(tsr).deleteById(1l);
		String s = tipSobeService.deleteType(1l);
		assertEquals("Uspesno obrisan tip sa id: 1", s);
		verify(tsr, times(1)).deleteById(1l);
		verifyNoMoreInteractions(tsr);
	}

}
