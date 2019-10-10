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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.BrojKarataDnevnoDTO;
import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;
import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.repository.AvioKompanijaRepository;
import com.isa.hoteli.hoteliservice.avio.repository.KartaRepository;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;

@RunWith(SpringRunner.class)
public class AvioKompanijaServiceTest 
{
	private List<AvioKompanija> kompanije = new ArrayList<>();
	private AvioKompanija kompanija1 = new AvioKompanija(1l, "a", "a", "a", 1.0f, 1.0f, null, null);
	private AvioKompanija kompanija2 = new AvioKompanija(2l, "b", "b", "b", 1.0f, 1.0f, null, null);
	private Float srednjaOcena = 1f;
	
	private List<Karta> karte = new ArrayList<>();
	private Karta karta1 = new Karta(1l, 100, 5, false, 0, "a", null, null, LocalDateTime.now(), null, null, 0);
	
	private LocalDate datumOd = LocalDate.now();
	private LocalDate datumDo = LocalDate.now();
	
	@Mock
	private AvioKompanijaRepository avioRepo;
	
	@Mock
	private KartaRepository kartaRepo;
	
	@InjectMocks
	private AvioKompanijaService avioService;
	
	
	@Before
	public void setUp()
	{
		kompanije.add(kompanija1);
		kompanije.add(kompanija2);
		karte.add(karta1);
	}
	
	@Test
	public void findByIdSuccess()
	{
		when(avioRepo.getOne(1l)).thenReturn(kompanija1);
		AvioKompanija kompanijaM = avioService.findById(1l);
		assertEquals(kompanijaM, kompanija1);
		verify(avioRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(avioRepo);
	}
	
	@Test
	public void traziSveSuccess()
	{
		when(avioRepo.findAll()).thenReturn(kompanije);
		List<AvioKompanija> kompanijeM = avioService.traziSve();
		assertEquals(kompanijeM, kompanije);
		verify(avioRepo, times(1)).findAll();
		verifyNoMoreInteractions(avioRepo);
	}
	
	@Test
	public void saveOneSuccess()
	{
		when(avioRepo.save(kompanija1)).thenReturn(kompanija1);
		AvioKompanija kompanijaM = avioService.saveOne(kompanija1);
		assertEquals(kompanijaM, kompanija1);
		verify(avioRepo, times(1)).save(kompanija1);
	}
	
	@Test
	public void updateOneSuccess()
	{
		when(avioRepo.getOne(1l)).thenReturn(kompanija1);
		when(avioRepo.save(kompanija1)).thenReturn(kompanija1);
		AvioKompanija kompanijaM = avioService.updateOne(1l, kompanija1);
		assertThat(kompanijaM.equals(kompanija1));
		verify(avioRepo, times(1)).getOne(1l);
		verify(avioRepo, times(1)).save(kompanija1);
		verifyNoMoreInteractions(avioRepo);
	}
	
	@Test
	public void updateOneFailed()
	{
		when(avioRepo.getOne(1l)).thenReturn(null);
		avioService.updateOne(1l, new AvioKompanijaDTO(kompanija1));
		verify(avioRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(avioRepo);
	}
	
	@Test
	public void deleteOneSuccess()
	{
		when(avioRepo.getOne(1l)).thenReturn(kompanija1);
		doNothing().when(avioRepo).deleteById(1l);
		boolean res = avioService.deleteOne(1l);
		assertEquals(true, res);
		verify(avioRepo, times(1)).getOne(1l);
		verify(avioRepo, times(1)).deleteById(1l);
		verifyNoMoreInteractions(avioRepo);
	}
	
	@Test
	public void getSrednjaOcenaAviokompanijeSuccess()
	{
		when(avioRepo.findAverageRating(1l)).thenReturn(srednjaOcena);
		Float avg = avioService.getSrednjaOcenaAvioKompanije(1l);
		assertEquals(srednjaOcena, avg);
		verify(avioRepo, times(1)).findAverageRating(1l);
		verifyNoMoreInteractions(avioRepo);
	}
	
	@Test
	public void getSrednjaOcenaAviokompanijeFailed()
	{
		when(avioRepo.findAverageRating(1l)).thenReturn(null);
		Float avg = avioService.getSrednjaOcenaAvioKompanije(1l);
		verify(avioRepo, times(1)).findAverageRating(1l);
		verifyNoMoreInteractions(avioRepo);
	}
	
	//nema sta da se mokuje
//	@Test
//	public void getBrojProdatihKarataDnevnoSuccess()
//	{
//		List<BrojKarataDnevnoDTO> karteM = avioService.getBrojProdatihKarataDnevno(1l);
//		assertNotNull(karteM);
//	}
//	
//	@Test
//	public void getBrojProdatihKarataNedeljnoSuccess()
//	{
//		List<BrojKarataDnevnoDTO> karteM = avioService.getBrojProdatihKarataNedeljno(1l);
//		assertNotNull(karteM);
//	}
//	
//	@Test
//	public void getBrojProdatihKarataMesecnoSuccess()
//	{
//		List<BrojKarataDnevnoDTO> karteM = avioService.getBrojProdatihKarataMesecno(1l);
//		assertNotNull(karteM);
//	}
}
