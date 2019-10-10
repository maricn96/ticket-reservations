package com.isa.hoteli.hoteliservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

import com.isa.hoteli.hoteliservice.avio.dto.KartaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.avio.dto.SlanjePozivniceZaRezervacijuDTO;
import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KartaRepository;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.repository.LetRepository;
import com.isa.hoteli.hoteliservice.avio.service.KartaService;


@RunWith(SpringRunner.class)
public class KartaServiceTest
{
	private Karta karta1 = new Karta(1l, 100, 5, false, 0, "a", null, null, LocalDateTime.now(), null, null, 0);
	private Karta karta2 = new Karta(2l, 200, 1, true, 0, "b", null, null, LocalDateTime.now(), null, null, 0);
	private List<Karta> karte = new ArrayList<>();
	private Korisnik korisnik = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.KORISNIK, null, true, "a", null, null, null, null, null, null, 0);
	private Let let1 = new Let(1l, 1l, LocalDateTime.now(), LocalDateTime.now(), 10, 10, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null, null);
	private LocalDateTime datumZaRezervisanje = LocalDateTime.now();
	
	private KartaDTO karta1Dto = new KartaDTO(1l, 100, 5, false, 0, "a", null, null, LocalDateTime.now(), null, null, 0);
	private KartaDTO karta2Dto = new KartaDTO(2l, 200, 1, true, 0, "b", null, null, LocalDateTime.now(), null, null, 0);
	private List<KartaDTO> karteDto = new ArrayList<>();
	
	//ZA REZERVISANJE
	private String rezervisane = "REZERVISANE";
	private KorisnikDTO korisnikUser = new KorisnikDTO(3l, "r", "r", "r", "r", "r", "r", true, Rola.KORISNIK, 1l, false, "a", null, null, 0);
	private KorisnikDTO korisnikFriend = new KorisnikDTO(4l, "rr", "rr", "rr", "rr", "rr", "rr", true, Rola.KORISNIK, 1l, false, "a", null, null, 0);
	private SlanjePozivniceZaRezervacijuDTO pozivnica = new SlanjePozivniceZaRezervacijuDTO();
	private String pasos1 = "aaa";
	private List<String> brojeviPasosa = new ArrayList<>();
	private List<KorisnikDTO> listaPrijatelja = new ArrayList<>();
	
	@Mock
	private KartaRepository kartaRepo;
	
	@Mock
	private KorisnikRepository korisnikRepo;
	
	@Mock
	private LetRepository letRepo;
	
	@InjectMocks
	private KartaService kartaService;
	
	@Before
	public void setUp() {
		datumZaRezervisanje.minusYears(20);
		karta1.setVremeRezervisanja(datumZaRezervisanje);
		karta1.setKorisnik(korisnik);
		karta1.setLet(let1);
		karte.add(karta1);
		karte.add(karta2);
		karteDto.add(karta1Dto);
		karteDto.add(karta2Dto);
		
		listaPrijatelja.add(korisnikFriend);
		brojeviPasosa.add(pasos1);
		pozivnica.setBrojeviPasosa(brojeviPasosa);
		pozivnica.setListaKarata(karteDto);
		pozivnica.setListaPrijatelja(listaPrijatelja);
	}
	
	@Test
	public void traziByIdSuccess()
	{
		when(kartaRepo.getOne(1l)).thenReturn(karta1);
		Karta kartaM = kartaService.traziById(1l);
		assertEquals(kartaM, karta1);
		verify(kartaRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(kartaRepo);
	}
	
	@Test
	public void traziSveSuccess()
	{
		when(kartaRepo.findAll()).thenReturn(karte);
		List<Karta> karteM = kartaService.traziSve();
		assertEquals(karteM, karte);
		verify(kartaRepo, times(1)).findAll();
		verifyNoMoreInteractions(kartaRepo);
	}
	
	@Test
	public void saveOneSuccess()
	{
		when(kartaRepo.save(karta1)).thenReturn(karta1);
		Karta kartaM = kartaService.saveOne(karta1);
		assertEquals(kartaM, karta1);
		verify(kartaRepo, times(1)).save(karta1);
	}
	
	@Test
	public void updateOneSuccess()
	{
		when(kartaRepo.getOne(1l)).thenReturn(karta1);
		when(kartaRepo.save(karta1)).thenReturn(karta1);
		Karta kartaM = kartaService.updateOne(1l, karta1);
		assertEquals(kartaM, karta1);
		verify(kartaRepo, times(1)).getOne(1l);
		verify(kartaRepo, times(1)).save(karta1);
		verifyNoMoreInteractions(kartaRepo);
	}
	
	@Test
	public void updateOneFailed()
	{
		when(kartaRepo.getOne(1l)).thenReturn(null);
		kartaService.updateOne(1l, karta1);
		verify(kartaRepo, times(1)).getOne(1l);
		verifyNoMoreInteractions(kartaRepo);
	}
	
	@Test
	public void deleteOneSuccess()
	{
		when(kartaRepo.getOne(1l)).thenReturn(karta1);
		doNothing().when(kartaRepo).deleteById(1l);
		boolean res = kartaService.deleteOne(1l);
		assertEquals(true, res);
		verify(kartaRepo, times(1)).getOne(1l);
		verify(kartaRepo, times(1)).deleteById(1l);
		verifyNoMoreInteractions(kartaRepo);
	}
	
	@Test
	public void brzaRezervacijaJedneKarteSuccess()
	{
		when(kartaRepo.getOne(1l)).thenReturn(karta1);
		when(korisnikRepo.getOne(1l)).thenReturn(korisnik);
		when(kartaRepo.save(karta1)).thenReturn(karta1);
		Boolean retval = kartaService.brzaRezervacijaJedneKarteTwin(1l, 1l);
		assertTrue(retval);
		verify(kartaRepo, times(1)).getOne(1l);
		verify(korisnikRepo, times(1)).getOne(1l);
		verify(kartaRepo, times(1)).save(karta1);
		verifyNoMoreInteractions(kartaRepo);
		verifyNoMoreInteractions(korisnikRepo);
	}
	
	
	//baca null zbog konverzije BEAN-DTO
//	@Test
//	public void rezervisiViseKarataSuccess()
//	{
//		when(korisnikRepo.getOne(1l)).thenReturn(korisnik);
//		when(kartaRepo.save(karta1)).thenReturn(karta1);
//		when(letRepo.getOne(1l)).thenReturn(let1);
//		when(letRepo.save(let1)).thenReturn(let1);
//		when(kartaRepo.save(karta1)).thenReturn(karta1);
//		String retval = kartaService.rezervisiViseKarataTwin(1l, pozivnica);
//		assertEquals(retval, "REZERVISANE");
//		verify(korisnikRepo, times(1)).getOne(1l);
//		verify(kartaRepo, times(1)).save(karta1);
//		verify(letRepo, times(1)).getOne(1l);
//		verify(letRepo, times(1)).save(let1);
//		verify(kartaRepo, times(1)).save(karta1);
//		verifyNoMoreInteractions(korisnikRepo);
//		verifyNoMoreInteractions(kartaRepo);
//		verifyNoMoreInteractions(letRepo);
//	}

	
}
