package com.isa.hoteli.hoteliservice.services;

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

import com.isa.hoteli.hoteliservice.avio.model.Klasa;
import com.isa.hoteli.hoteliservice.avio.repository.KlasaRepository;
import com.isa.hoteli.hoteliservice.avio.service.KlasaService;


@RunWith(SpringRunner.class)
public class KlasaServiceTest
{
	private Klasa klasa1 = new Klasa(1l, "a", null);
	private Klasa klasa2 = new Klasa(2l, "b", null);
	private List<Klasa> klase = new ArrayList<>();
	
	@Mock
	private KlasaRepository klasaRepo;
	
	@InjectMocks
	private KlasaService klasaService;
	
	@Before
	public void setUp() {
		klase.add(klasa1);
		klase.add(klasa2);
	}
	
	@Test
	public void traziByIdSuccess()
	{
		when(klasaRepo.getOne(1l)).thenReturn(klasa1);
		Klasa kartaM = klasaService.traziById(1l);
		assertEquals(kartaM, klasa1);
		verify(klasaRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(klasaRepo);
	}
	
	@Test
	public void traziSveSuccess()
	{
		when(klasaRepo.findAll()).thenReturn(klase);
		List<Klasa> karteM = klasaService.traziSve();
		assertEquals(karteM, klase);
		verify(klasaRepo, times(1)).findAll();
		verifyNoMoreInteractions(klasaRepo);
	}
	
	@Test
	public void saveOneSuccess()
	{
		when(klasaRepo.save(klasa1)).thenReturn(klasa1);
		Klasa kartaM = klasaService.saveOne(klasa1);
		assertEquals(kartaM, klasa1);
		verify(klasaRepo, times(1)).save(klasa1);
	}
	
	@Test
	public void updateOneSuccess()
	{
		when(klasaRepo.getOne(1l)).thenReturn(klasa1);
		when(klasaRepo.save(klasa1)).thenReturn(klasa1);
		Klasa kartaM = klasaService.updateOne(1l, klasa1);
		assertEquals(kartaM, klasa1);
		verify(klasaRepo, times(1)).getOne(1l);
		verify(klasaRepo, times(1)).save(klasa1);
		verifyNoMoreInteractions(klasaRepo);
	}
	
	@Test
	public void updateOneFailed()
	{
		when(klasaRepo.getOne(1l)).thenReturn(null);
		klasaService.updateOne(1l, klasa1);
		verify(klasaRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(klasaRepo);
	}
	
	@Test
	public void deleteOneSuccess()
	{
		when(klasaRepo.getOne(1l)).thenReturn(klasa1);
		doNothing().when(klasaRepo).deleteById(1l);
		boolean res = klasaService.deleteOne(1l);
		assertEquals(true, res);
		verify(klasaRepo, times(1)).getOne(1l);
		verify(klasaRepo, times(1)).deleteById(1l);
		verifyNoMoreInteractions(klasaRepo);
	}
}
