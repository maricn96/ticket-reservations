package com.isa.hoteli.hoteliservice.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Pretraga;
import com.isa.hoteli.hoteliservice.model.TipSobe;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HotelskaSobaIntegrationTest {

	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_HOTELA, 1l, true, "a", null, null, null, null, null, null, 0);

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
	public void getRoomsSuccess() throws Exception {		
		mockMvc.perform(get("/sobe/admin/all")).andExpect(status().isOk());
	}
	
	@Test
	public void getRoomsInfoSuccess() throws Exception {		
		mockMvc.perform(get("/sobe/all")).andExpect(status().isOk());
	}
	
	@Test
	public void getRoomsFromHotelSuccess() throws Exception {		
		mockMvc.perform(get("/sobe/all/2")).andExpect(status().isOk());
	}
	
	@Test
	public void getRoomByIdSuccess() throws Exception {		
		mockMvc.perform(get("/sobe/4")).andExpect(status().isOk());
	}
	
	@Test
	public void getFreeRoomsSuccess() throws Exception {
		Pretraga p = new Pretraga(null,new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), -1, -1);
		String s = objectMapper.writeValueAsString(p);
		mockMvc.perform(post("/sobe/free/2").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	@Test
	public void getReservedRoomsSuccess() throws Exception {
		Pretraga p = new Pretraga(null,new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), -1, -1);
		String s = objectMapper.writeValueAsString(p);
		mockMvc.perform(post("/sobe/reserved/2").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	@Test
	public void getFastRoomsSuccess() throws Exception {
		Pretraga p = new Pretraga(null,new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), -1, -1);
		String s = objectMapper.writeValueAsString(p);
		mockMvc.perform(post("/sobe/brza/2").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	/*@Test
	@Transactional
	@Rollback(true)
	public void testDeleteRoom() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		mockMvc.perform(delete("/sobe/6")).andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateRoom() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Hotel hotel = new Hotel(1l, "qwert", "q", "q", "q", 0f, 0f);
		TipSobe ts = new TipSobe(5l, "a", hotel);
		HotelskaSoba hs = new HotelskaSoba(6l, 44, 3, 44, 3f, hotel, ts);
		String s = objectMapper.writeValueAsString(hs);
		this.mockMvc.perform(put("/sobe/6").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateRoom() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Hotel hotel = new Hotel(1l, "qwert", "q", "q", "q", 0f, 0f);
		TipSobe ts = new TipSobe(5l, "a", hotel);
		HotelskaSoba hs = new HotelskaSoba(6l, 3, 3, 3, 3f, hotel, ts);
		String s = objectMapper.writeValueAsString(hs);
		this.mockMvc.perform(post("/sobe/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}*/
}
