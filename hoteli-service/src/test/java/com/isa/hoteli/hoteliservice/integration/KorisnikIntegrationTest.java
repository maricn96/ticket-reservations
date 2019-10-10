package com.isa.hoteli.hoteliservice.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.TipSobe;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KorisnikIntegrationTest {
	
	private Korisnik k = new Korisnik(1l, "b", "b", "b", "b", "b", "b", true, Rola.KORISNIK, null, true, "a", null, null, null, null, null, null, 0);
	
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getUserSuccess() throws Exception {		
		mockMvc.perform(get("/korisnik/all")).andExpect(status().isOk());
	}
	
	@Test
	public void getUserByIdSuccess() throws Exception {		
		mockMvc.perform(get("/korisnik/1")).andExpect(status().isOk());
	}
	
	@Test
	public void getUserByEmailSuccess() throws Exception {		
		mockMvc.perform(get("/korisnik/all/mm")).andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateUser() throws Exception {
		String s = objectMapper.writeValueAsString(k);
		this.mockMvc.perform(post("/korisnik/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	/*@Test
	@Transactional
	@Rollback(true)
	public void testDeleteRoom() throws Exception {
		mockMvc.perform(delete("/korisnik/8")).andExpect(status().isOk());
	}*/
	
	/*@Test
	@Transactional
	@Rollback(true)
	public void testUpdateRoom() throws Exception {
		String s = objectMapper.writeValueAsString(k);
		this.mockMvc.perform(put("/korisnik/8").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}*/
}
