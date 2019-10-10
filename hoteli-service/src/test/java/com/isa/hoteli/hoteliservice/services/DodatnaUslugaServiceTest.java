package com.isa.hoteli.hoteliservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
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

import com.isa.hoteli.hoteliservice.dto.DodatnaUslugaDTO;
import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.repository.DodatnaUslugaRepository;
import com.isa.hoteli.hoteliservice.service.DodatnaUslugaService;

@RunWith(SpringRunner.class)
public class DodatnaUslugaServiceTest {
	
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private List<DodatnaUsluga> usluge = new ArrayList<>();
	private DodatnaUsluga usluga1 = new DodatnaUsluga(1l, "a", 50, 2, hotel1);
	private DodatnaUsluga usluga2 = new DodatnaUsluga(1l, "b", 100, 5, hotel1);
	
	@Mock
	private DodatnaUslugaRepository dur;
	
	@InjectMocks
	private DodatnaUslugaService dus;
	
	@Before
	public void setUp() {
		usluge.add(usluga1);
		usluge.add(usluga2);
	}
	
	@Test
	public void getAllSuccess() {
		when(dur.findAll()).thenReturn(usluge);
		List<DodatnaUsluga> services = dus.getServices();
		assertEquals(usluge, services);
		verify(dur, times(1)).findAll();
		verifyNoMoreInteractions(dur);
	}
	
	@Test
	public void getAllFromHotelSuccess() {
		when(dur.getAllFromHotel(1l)).thenReturn(usluge);
		List<DodatnaUsluga> services = dus.getServicesFromHotel(1l);
		assertEquals(usluge, services);
		verify(dur, times(1)).getAllFromHotel(1l);
		verifyNoMoreInteractions(dur);
	}
	
	@Test
	public void getServiceByIdSuccess() {
		when(dur.getOne(1l)).thenReturn(usluga1);
		DodatnaUsluga usluga = dus.getServiceById(1l);
		assertEquals(usluga1, usluga);
		verify(dur, times(1)).getOne(1l);
		verifyNoMoreInteractions(dur);
	}
	
	@Test
	public void createServiceSuccess() {
		when(dur.save(usluga1)).thenReturn(usluga1);
		DodatnaUslugaDTO dto = dus.createService(usluga1);
		assertThat(dto.equals(new DodatnaUslugaDTO(usluga1)));
		verify(dur, times(1)).save(usluga1);
		verifyNoMoreInteractions(dur);
	}
	
	@Test
	public void updateServiceSuccess() {
		when(dur.getOne(1l)).thenReturn(usluga1);
		when(dur.save(usluga1)).thenReturn(usluga1);
		DodatnaUslugaDTO dto = dus.updateService(usluga1, 1l);
		assertThat(dto.equals(new DodatnaUslugaDTO(usluga1)));
		verify(dur, times(1)).getOne(1l);
		verify(dur, times(1)).save(usluga1);
		verifyNoMoreInteractions(dur);
	}
	
	@Test
	public void updateServiceFailed() {
		when(dur.getOne(1l)).thenReturn(null);
		dus.updateService(usluga1, 1l);
		verify(dur, times(1)).getOne(1l);
		verifyNoMoreInteractions(dur);
	}
	
	@Test
	public void deleteServiceSuccess() {
		doNothing().when(dur).deleteById(1l);
		String s = dus.deleteService(1l);
		assertEquals("Uspesno obrisana usluga sa id: 1", s);
		verify(dur, times(1)).deleteById(1l);
		verifyNoMoreInteractions(dur);
	}
}
