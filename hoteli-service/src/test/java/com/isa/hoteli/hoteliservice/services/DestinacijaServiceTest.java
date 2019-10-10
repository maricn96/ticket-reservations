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

import com.isa.hoteli.hoteliservice.avio.dto.DestinacijaDTO;
import com.isa.hoteli.hoteliservice.avio.model.Destinacija;
import com.isa.hoteli.hoteliservice.avio.repository.DestinacijaRepository;
import com.isa.hoteli.hoteliservice.avio.service.DestinacijaService;


@RunWith(SpringRunner.class)
public class DestinacijaServiceTest
{
	private Destinacija destinacija1 = new Destinacija(1l, "a", "a", null, null, null, null, null);
	private Destinacija destinacija2 = new Destinacija(2l, "b", "b", null, null, null, null, null);
	private List<Destinacija> destinacije = new ArrayList<>();
	
	@Mock
	private DestinacijaRepository destRepo;
	
	@InjectMocks
	private DestinacijaService destService;
	
	@Before
	public void setUp() {
		destinacije.add(destinacija1);
		destinacije.add(destinacija2);
	}
	
	@Test
	public void traziByIdSuccess()
	{
		when(destRepo.getOne(1l)).thenReturn(destinacija1);
		Destinacija dest = destService.traziById(1l);
		assertEquals(dest, destinacija1);
		verify(destRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(destRepo);
	}
	
	@Test
	public void traziSveSuccess()
	{
		when(destRepo.findAll()).thenReturn(destinacije);
		List<Destinacija> dests = destService.traziSve();
		assertEquals(dests, destinacije);
		verify(destRepo, times(1)).findAll();
		verifyNoMoreInteractions(destRepo);
	}
	
	@Test
	public void saveOneSuccess()
	{
		when(destRepo.save(destinacija1)).thenReturn(destinacija1);
		Destinacija dest = destService.saveOne(destinacija1);
		assertEquals(dest, destinacija1);
		verify(destRepo, times(1)).save(destinacija1);
	}
	
	@Test
	public void updateOneSuccess()
	{
		when(destRepo.getOne(1l)).thenReturn(destinacija1);
		when(destRepo.save(destinacija1)).thenReturn(destinacija1);
		Destinacija dest = destService.updateOne(1l, destinacija1);
		assertEquals(dest, destinacija1);
		verify(destRepo, times(1)).getOne(1l);
		verify(destRepo, times(1)).save(destinacija1);
		verifyNoMoreInteractions(destRepo);
	}
	
	@Test
	public void updateOneFailed()
	{
		when(destRepo.getOne(1l)).thenReturn(null);
		destService.updateOne(1l, destinacija1);
		verify(destRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(destRepo);
	}
	
	@Test
	public void deleteOneSuccess()
	{
		when(destRepo.getOne(1l)).thenReturn(destinacija1);
		doNothing().when(destRepo).deleteById(1l);
		boolean res = destService.deleteOne(1l);
		assertEquals(true, res);
		verify(destRepo, times(1)).getOne(1l);
		verify(destRepo, times(1)).deleteById(1l);
		verifyNoMoreInteractions(destRepo);
	}
}
