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

import com.isa.hoteli.hoteliservice.avio.model.Prtljag;
import com.isa.hoteli.hoteliservice.avio.repository.PrtljagRepository;
import com.isa.hoteli.hoteliservice.avio.service.PrtljagService;


@RunWith(SpringRunner.class)
public class PrtljagServiceTest
{
	private Prtljag prtljag1 = new Prtljag(1l, 1, "a", null);
	private Prtljag prtljag2 = new Prtljag(2l, 2, "b", null);
	private List<Prtljag> klase = new ArrayList<>();
	
	@Mock
	private PrtljagRepository prtljagRepo;
	
	@InjectMocks
	private PrtljagService prtljagService;
	
	@Before
	public void setUp() {
		klase.add(prtljag1);
		klase.add(prtljag2);
	}
	
	@Test
	public void traziByIdSuccess()
	{
		when(prtljagRepo.getOne(1l)).thenReturn(prtljag1);
		Prtljag prtljagM = prtljagService.traziById(1l);
		assertEquals(prtljagM, prtljag1);
		verify(prtljagRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(prtljagRepo);
	}
	
	@Test
	public void traziSveSuccess()
	{
		when(prtljagRepo.findAll()).thenReturn(klase);
		List<Prtljag> karteM = prtljagService.traziSve();
		assertEquals(karteM, klase);
		verify(prtljagRepo, times(1)).findAll();
		verifyNoMoreInteractions(prtljagRepo);
	}
	
	@Test
	public void saveOneSuccess()
	{
		when(prtljagRepo.save(prtljag1)).thenReturn(prtljag1);
		Prtljag prtljagM = prtljagService.saveOne(prtljag1);
		assertEquals(prtljagM, prtljag1);
		verify(prtljagRepo, times(1)).save(prtljag1);
	}
	
	@Test
	public void updateOneSuccess()
	{
		when(prtljagRepo.getOne(1l)).thenReturn(prtljag1);
		when(prtljagRepo.save(prtljag1)).thenReturn(prtljag1);
		Prtljag prtljagM = prtljagService.updateOne(1l, prtljag1);
		assertEquals(prtljagM, prtljag1);
		verify(prtljagRepo, times(1)).getOne(1l);
		verify(prtljagRepo, times(1)).save(prtljag1);
		verifyNoMoreInteractions(prtljagRepo);
	}
	
	@Test
	public void updateOneFailed()
	{
		when(prtljagRepo.getOne(1l)).thenReturn(null);
		prtljagService.updateOne(1l, prtljag1);
		verify(prtljagRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(prtljagRepo);
	}
	
	@Test
	public void deleteOneSuccess()
	{
		when(prtljagRepo.getOne(1l)).thenReturn(prtljag1);
		doNothing().when(prtljagRepo).deleteById(1l);
		boolean res = prtljagService.deleteOne(1l);
		assertEquals(true, res);
		verify(prtljagRepo, times(1)).getOne(1l);
		verify(prtljagRepo, times(1)).deleteById(1l);
		verifyNoMoreInteractions(prtljagRepo);
	}
}
