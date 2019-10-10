package com.isa.hoteli.hoteliservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;
import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.repository.KartaRepository;
import com.isa.hoteli.hoteliservice.avio.repository.KlasaRepository;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.repository.LetRepository;
import com.isa.hoteli.hoteliservice.avio.service.KlasaService;
import com.isa.hoteli.hoteliservice.avio.service.LetService;


@RunWith(SpringRunner.class)
public class LetServiceTest
{
	private Let let1 = new Let(1l, 1l, LocalDateTime.now(), LocalDateTime.now(), 10, 10, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null, null);
	private Let let2 = new Let(2l, 2l, LocalDateTime.now(), LocalDateTime.now(), 20, 20, 1, "b", 20, 20, 20, 20, null, null, null, null, null, null, null, null);
	private List<Let> letovi = new ArrayList<>();
	private Korisnik korisnik = new Korisnik();
	private AvioKompanija kompanija = new AvioKompanija();
	private Float srednjaOcena = 1f;
	
	private LocalDateTime vremeRezervisanja;
	private Karta karta1 = new Karta(0l, 10, 0, false, 0, "0", null, null, vremeRezervisanja, null, null, 0);
	
	@Mock
	private KartaRepository kartaRepo;
	
	@Mock
	private LetRepository letRepo;
	
	@Mock
	private KorisnikRepository korisnikRepo;
	
	@InjectMocks
	private LetService letService;
	
	@Before
	public void setUp() {
		
		kompanija.setIdAvioKompanije(1l);
		let1.setAviokompanija(kompanija);
		karta1.setLet(let1);
		karta1.setVremeRezervisanja(LocalDateTime.of(2000, 10, 10, 10, 10));
		karta1.setKorisnik(korisnik);
		karta1.setKorisnikKojiSaljePozivnicu(korisnik);
		letovi.add(let1);
		letovi.add(let2);
	}
	
	@Test
	public void traziByIdSuccess()
	{
		when(letRepo.getOne(1l)).thenReturn(let1);
		Let letM = letService.traziById(1l);
		assertEquals(letM, let1);
		verify(letRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(letRepo);
	}
	
	@Test
	public void traziSveSuccess()
	{
		when(letRepo.findAll()).thenReturn(letovi);
		List<Let> letM = letService.traziSve();
		assertEquals(letM, letovi);
		verify(letRepo, times(1)).findAll();
		verifyNoMoreInteractions(letRepo);
	}
	
	@Test
	public void saveOneSuccess()
	{
		when(korisnikRepo.getOne(1l)).thenReturn(korisnik);
		when(letRepo.save(let1)).thenReturn(let1);
		when(letRepo.findByBrojLeta(1l)).thenReturn(let1);
		when(kartaRepo.save(karta1)).thenReturn(karta1);
		Let letM = letService.saveOne(let1);
		assertEquals(letM, let1);
		verify(korisnikRepo, times(1)).getOne(1l);
		verify(letRepo, times(1)).save(let1);
		verify(letRepo, times(1)).findByBrojLeta(1l);
		verify(kartaRepo, times(10)).save(karta1);
		verifyNoMoreInteractions(korisnikRepo);
		verifyNoMoreInteractions(kartaRepo);
	}
	
	@Test
	public void updateOneSuccess()
	{
		when(letRepo.getOne(1l)).thenReturn(let1);
		when(letRepo.save(let1)).thenReturn(let1);
		Let letM = letService.updateOne(1l, let1);
		assertEquals(letM, let1);
		verify(letRepo, times(1)).getOne(1l);
		verify(letRepo, times(1)).save(let1);
		verifyNoMoreInteractions(letRepo);
	}
	
	@Test
	public void updateOneFailed()
	{
		when(letRepo.getOne(1l)).thenReturn(null);
		letService.updateOne(1l, let1);
		verify(letRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(letRepo);
	}
	
	@Test
	public void deleteOneSuccess()
	{
		when(letRepo.getOne(1l)).thenReturn(let1);
		doNothing().when(letRepo).deleteById(1l);
		boolean res = letService.deleteOne(1l);
		assertEquals(true, res);
		verify(letRepo, times(1)).getOne(1l);
		verify(letRepo, times(1)).deleteById(1l);
		verifyNoMoreInteractions(letRepo);
	}
	
	@Test
	public void getIdKompanijeSuccess()
	{
		when(letRepo.findAll()).thenReturn(letovi);
		Long letM = letService.getIdKompanije(1l);
		assertNotNull(letM);
		verify(letRepo, times(1)).findAll();
		verifyNoMoreInteractions(letRepo);
	}
	
	@Test
	public void getSrednjaOcenaLetaSuccess()
	{
		when(letRepo.findAverageRatingTwin(1l)).thenReturn(srednjaOcena);
		when(letRepo.getOne(1l)).thenReturn(let1);
		Float avg = letService.getSrednjaOcenaLetaTwin(1l);
		assertEquals(avg, srednjaOcena);
		verify(letRepo, times(1)).findAverageRatingTwin(1l);
		verify(letRepo, times(1)).getOne(1l);
		
	}
}
