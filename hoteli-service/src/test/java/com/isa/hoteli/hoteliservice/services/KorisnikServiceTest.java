package com.isa.hoteli.hoteliservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
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

import com.isa.hoteli.hoteliservice.avio.controller.KorisnikController;
import com.isa.hoteli.hoteliservice.avio.converter.KartaConverter;
import com.isa.hoteli.hoteliservice.avio.converter.KorisnikConverter;
import com.isa.hoteli.hoteliservice.avio.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KartaRepository;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.repository.LetRepository;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.avio.service.MailService;
import com.isa.hoteli.hoteliservice.dto.HotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
public class KorisnikServiceTest {
	
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.KORISNIK, null, true, "a", null, null, null, null, null, null, 0);
	private Korisnik k1 = new Korisnik(2l, "b", "b", "b", "b", "b", "b", true, Rola.KORISNIK, null, true, "a", null, null, null, null, null, null, 0);
	private List<Korisnik> korisnici = new ArrayList<>();
	
	@Mock
	private KorisnikRepository kr;
	
	@Mock
	private KorisnikConverter kc;
	
	@Mock
	private LetRepository lr;
	
	@Mock
	private KartaRepository kartaRepository;
	
	@Mock
	private KartaConverter kartaConverter;
	
	@Mock
	private MailService mailService;
	
	@Mock
	private JwtTokenUtils jwt;
	
	@InjectMocks
	private KorisnikService ks;
	
	@Before
	public void setUp() {
		korisnici.add(k);
		korisnici.add(k1);
	}
	
	@Test
	public void getUsersSuccess() {
		when(kr.findAll()).thenReturn(korisnici);
		List<Korisnik> users = ks.getUsers();
		assertEquals(users, korisnici);
		verify(kr, times(1)).findAll();
		verifyNoMoreInteractions(kr);
	}
	
	@Test
	public void getUserByIdSuccess() {
		when(kr.getOne(1l)).thenReturn(k);
		Korisnik user = ks.getUserById(1l);
		assertEquals(user, k);
		verify(kr, times(1)).getOne(1l);
		verifyNoMoreInteractions(kr);
	}
	
	@Test
	public void getUserByEmailSuccess() {
		when(kr.getUserByEmail("a")).thenReturn(k);
		Korisnik user = ks.getUserByEmail("a");
		assertEquals(user, k);
		verify(kr, times(1)).getUserByEmail("a");
		verifyNoMoreInteractions(kr);
	}
	
	@Test
	public void createUserSuccess() {
		when(kr.save(k)).thenReturn(k);
		when(kr.getUserByEmail("a")).thenReturn(null);
		KorisnikDTO user = ks.createUser(k);
		assertThat(user.equals(new KorisnikDTO(k)));
		verify(kr, times(1)).save(k);
		verify(kr, times(1)).getUserByEmail("a");
		verifyNoMoreInteractions(kr);
	}
	
	@Test
	public void createUserFailed() {
		when(kr.save(k)).thenReturn(k);
		when(kr.getUserByEmail("a")).thenReturn(k1);
		ks.createUser(k);
		verify(kr, times(1)).getUserByEmail("a");
		verifyNoMoreInteractions(kr);
	}
	
	@Test
	public void deleteUserSuccess() {
		doNothing().when(kr).deleteById(1l);
		String s = ks.deleteUser(1l);
		assertEquals("Uspesno obrisan korisnik sa id: 1", s);
		verify(kr, times(1)).deleteById(1l);
		verifyNoMoreInteractions(kr);
	}
	
	@Test
	public void updateUserSuccess() {
		when(kr.getOne(1l)).thenReturn(k);
		when(kr.getUserByEmail("a")).thenReturn(k);
		when(kr.save(k)).thenReturn(k);
		KorisnikDTO user = ks.updateUser(k, 1l);
		assertThat(user.equals(new KorisnikDTO(k)));
		verify(kr, times(1)).getOne(1l);
		verify(kr, times(1)).save(k);
		verify(kr, times(1)).getUserByEmail("a");
		verifyNoMoreInteractions(kr);
	}
	
	@Test
	public void updateFailedSuccess() {
		when(kr.getOne(1l)).thenReturn(k);
		when(kr.getUserByEmail("a")).thenReturn(k1);
		KorisnikDTO user = ks.updateUser(k, 1l);
		assertNull(user);
		verify(kr, times(1)).getOne(1l);
		verify(kr, times(1)).getUserByEmail("a");
		verifyNoMoreInteractions(kr);
	}
}
