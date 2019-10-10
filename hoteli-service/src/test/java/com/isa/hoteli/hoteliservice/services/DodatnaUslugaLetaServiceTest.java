package com.isa.hoteli.hoteliservice.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.avio.model.DodatnaUslugaLeta;
import com.isa.hoteli.hoteliservice.avio.repository.DodatnaUslugaLetaRepository;
import com.isa.hoteli.hoteliservice.avio.service.DodatnaUslugaLetaService;



@RunWith(SpringRunner.class)
public class DodatnaUslugaLetaServiceTest
{
	private DodatnaUslugaLeta usluga1 = new DodatnaUslugaLeta(1l, "a", null);
	private DodatnaUslugaLeta usluga2 = new DodatnaUslugaLeta(2l, "b", null);
	private List<DodatnaUslugaLeta> usluge = new ArrayList<>();
	
	@Mock
	private DodatnaUslugaLetaRepository uslugaRepo;
	
	@InjectMocks
	private DodatnaUslugaLetaService uslugaService;
	
	@Before
	public void setUp() {
		usluge.add(usluga1);
		usluge.add(usluga2);
	}
	
	@Test
	public void traziByIdSuccess()
	{
		when(uslugaRepo.getOne(1l)).thenReturn(usluga1);
		DodatnaUslugaLeta dest = uslugaService.traziById(1l);
		assertEquals(dest, usluga1);
		verify(uslugaRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(uslugaRepo);
	}
	
	@Test
	public void traziSveSuccess()
	{
		when(uslugaRepo.findAll()).thenReturn(usluge);
		List<DodatnaUslugaLeta> dests = uslugaService.traziSve();
		assertEquals(dests, usluge);
		verify(uslugaRepo, times(1)).findAll();
		verifyNoMoreInteractions(uslugaRepo);
	}
	
	@Test
	public void saveOneSuccess()
	{
		when(uslugaRepo.save(usluga1)).thenReturn(usluga1);
		DodatnaUslugaLeta dest = uslugaService.saveOne(usluga1);
		assertEquals(dest, usluga1);
		verify(uslugaRepo, times(1)).save(usluga1);
	}
	
	@Test
	public void updateOneSuccess()
	{
		when(uslugaRepo.getOne(1l)).thenReturn(usluga1);
		when(uslugaRepo.save(usluga1)).thenReturn(usluga1);
		DodatnaUslugaLeta dest = uslugaService.updateOne(1l, usluga1);
		assertEquals(dest, usluga1);
		verify(uslugaRepo, times(1)).getOne(1l);
		verify(uslugaRepo, times(1)).save(usluga1);
		verifyNoMoreInteractions(uslugaRepo);
	}
	
	@Test
	public void updateOneFailed()
	{
		when(uslugaRepo.getOne(1l)).thenReturn(null);
		uslugaService.updateOne(1l, usluga1);
		verify(uslugaRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(uslugaRepo);
	}
	
	@Test
	public void deleteOneSuccess()
	{
		when(uslugaRepo.getOne(1l)).thenReturn(usluga1);
		doNothing().when(uslugaRepo).deleteById(1l);
		boolean res = uslugaService.deleteOne(1l);
		assertEquals(true, res);
		verify(uslugaRepo, times(1)).getOne(1l);
		verify(uslugaRepo, times(1)).deleteById(1l);
		verifyNoMoreInteractions(uslugaRepo);
	}
}
