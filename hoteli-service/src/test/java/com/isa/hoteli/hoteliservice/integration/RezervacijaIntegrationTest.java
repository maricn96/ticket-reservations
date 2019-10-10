package com.isa.hoteli.hoteliservice.integration;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Posecenost;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.model.TipSobe;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RezervacijaIntegrationTest {

	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_HOTELA, 1l, true, "a", null, null, null, null, null, null, 0);
	private Korisnik k1 = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.KORISNIK, null, true, "a", null, null, null, null, null, null, 0);

	
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	private KorisnikService ks;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getReservationsSuccess() throws Exception {		
		mockMvc.perform(get("/rezervacija/all")).andExpect(status().isOk());
	}
	
	@Test
	public void getReservationsFromHotelSuccess() throws Exception {		
		mockMvc.perform(get("/rezervacija/all/4")).andExpect(status().isOk());
	}
	
	@Test
	public void getReservationsFromUserSuccess() throws Exception {		
		mockMvc.perform(get("/rezervacija/user/1")).andExpect(status().isOk())/*.andExpect(jsonPath("$", hasSize(1)))*/;
	}
	
	@Test
	public void getReservationByIdSuccess() throws Exception {		
		mockMvc.perform(get("/rezervacija/1")).andExpect(status().isOk());
	}
	
	@Test
	public void getDnevnaPosecenostSuccess() throws Exception {		
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Posecenost p = new Posecenost(1l,  new Date(System.currentTimeMillis()));
		String s = objectMapper.writeValueAsString(p);
		mockMvc.perform(post("/rezervacija/posecenost/dnevna").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	@Test
	public void getNedeljnaPosecenostSuccess() throws Exception {		
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Posecenost p = new Posecenost(1l,  new Date(System.currentTimeMillis()));
		String s = objectMapper.writeValueAsString(p);
		mockMvc.perform(post("/rezervacija/posecenost/nedeljna").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	@Test
	public void getMesecnaPosecenostSuccess() throws Exception {		
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Posecenost p = new Posecenost(1l,  new Date(System.currentTimeMillis()));
		String s = objectMapper.writeValueAsString(p);
		mockMvc.perform(post("/rezervacija/posecenost/mesecna").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	@Test
	public void getNedeljniPrihodiSuccess() throws Exception {		
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Posecenost p = new Posecenost(1l,  new Date(System.currentTimeMillis()));
		String s = objectMapper.writeValueAsString(p);
		mockMvc.perform(post("/rezervacija/prihodi/nedeljni").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	@Test
	public void getMesecniPrihodiSuccess() throws Exception {		
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Posecenost p = new Posecenost(1l,  new Date(System.currentTimeMillis()));
		String s = objectMapper.writeValueAsString(p);
		mockMvc.perform(post("/rezervacija/prihodi/mesecni").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	@Test
	public void getGodisnjiPrihodiSuccess() throws Exception {		
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Posecenost p = new Posecenost(1l,  new Date(System.currentTimeMillis()));
		String s = objectMapper.writeValueAsString(p);
		mockMvc.perform(post("/rezervacija/prihodi/godisnji").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
}
