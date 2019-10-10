package com.isa.hoteli.hoteliservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.controller.KorisnikController;
import com.isa.hoteli.hoteliservice.avio.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.controller.HotelController;
import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(KorisnikController.class)
public class KorisnikControllerTest {
	
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.KORISNIK, null, true, "a", null, null, null, null, null, null, 0);
	private Korisnik k1 = new Korisnik(2l, "b", "b", "b", "b", "b", "b", true, Rola.KORISNIK, null, true, "a", null, null, null, null, null, null, 0);
	private List<Korisnik> korisnici = new ArrayList<>();
	private KorisnikDTO kDTO = new KorisnikDTO(1l, "a", "a", "a", "a", "a", "a", true, Rola.KORISNIK, null, true, "a", null, null, 0);
	private KorisnikDTO k1DTO = new KorisnikDTO(2l, "b", "b", "b", "b", "b", "b", true, Rola.KORISNIK, null, true, "a", null, null, 0);
	private List<KorisnikDTO> korisniciDTO = new ArrayList<>();
	
	@MockBean
	private KorisnikService ks;
	
	@MockBean
	private JwtTokenUtils jwt;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		korisnici.add(k);
		korisnici.add(k1);
	}

	@Test
	public void getUsersSuccess() throws Exception {
		when(ks.getUsers()).thenReturn(korisnici);
		MvcResult result = this.mockMvc.perform(get("/korisnik/all")).andExpect(status().isOk()).andReturn();
		List<KorisnikDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<KorisnikDTO>>() {});
		assertThat(rets.size(), is(2));
		for (Korisnik korisnik : korisnici) {
			korisniciDTO.add(new KorisnikDTO(korisnik));
		}
		assertThat(korisniciDTO.equals(rets));
		verify(ks, times(1)).getUsers();
		verifyNoMoreInteractions(ks);
	}
	
	@Test 
	public void getUserByIdSuccess() throws Exception {
		when(ks.getUserById(1l)).thenReturn(k);
		MvcResult result = this.mockMvc.perform(get("/korisnik/1")).andExpect(status().isOk()).andReturn();
		KorisnikDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), KorisnikDTO.class);
		assertThat(new KorisnikDTO(k).equals(dto));
		verify(ks, times(2)).getUserById(1l);
		verifyNoMoreInteractions(ks);
	}
	
	@Test 
	public void getUserByEmailSuccess() throws Exception {
		when(ks.getUserByEmail("a")).thenReturn(k);
		MvcResult result = this.mockMvc.perform(get("/korisnik/all/a")).andExpect(status().isOk()).andReturn();
		KorisnikDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), KorisnikDTO.class);
		assertThat(new KorisnikDTO(k).equals(dto));
		verify(ks, times(2)).getUserByEmail("a");
		verifyNoMoreInteractions(ks);
	}
	
	@Test 
	public void getUserByEmailFailed() throws Exception {
		when(ks.getUserByEmail("a")).thenReturn(null);
		this.mockMvc.perform(get("/korisnik/all/a")).andExpect(status().is4xxClientError());
		verify(ks, times(1)).getUserByEmail("a");
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void createUserSuccess() throws Exception{
		when(ks.createKorisnika(Mockito.any(Korisnik.class))).thenReturn(kDTO);
		String s = objectMapper.writeValueAsString(kDTO);
		MvcResult result = this.mockMvc.perform(post("/korisnik/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		KorisnikDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), KorisnikDTO.class);
		assertThat(dto.equals(kDTO));
		verify(ks, times(1)).createKorisnika(Mockito.any(Korisnik.class));
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void deleteUserSuccess() throws Exception {
		when(ks.deleteUser(1l)).thenReturn("Obrisano");
		MvcResult result = this.mockMvc.perform(delete("/korisnik/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals("Obrisano"));
		verify(ks, times(1)).deleteUser(1l);
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void updateUserSuccess() throws Exception {
		when(ks.updateKorisnika(Mockito.any(Korisnik.class), Mockito.any(Long.class))).thenReturn(kDTO);
		String s = objectMapper.writeValueAsString(kDTO);
		MvcResult result = this.mockMvc.perform(put("/korisnik/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(kDTO));
		verify(ks, times(1)).updateKorisnika(Mockito.any(Korisnik.class), Mockito.any(Long.class));
		verifyNoMoreInteractions(ks);
	}
	
}
