package com.isa.hoteli.hoteliservice.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.isa.hoteli.hoteliservice.avio.dto.DestinacijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.KartaDTO;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KartaIntegrationTest {
	
	private String route = "/ticket";
	
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
	public void getKartaSuccess() throws Exception {		
		mockMvc.perform(get(route + "/getone/2")).andExpect(status().isOk());
	}
	
	@Test
	public void getAllKarteSuccess() throws Exception {		
		mockMvc.perform(get(route + "/getall")).andExpect(status().isOk());
	}
	
	@Test
	public void getAllNerezervisaneKarteSuccess() throws Exception {		
		mockMvc.perform(get(route + "/getfree/2")).andExpect(status().isOk());
	}
	
	//treba paziti koji je poslednji id za bazu pa taj ispisati
	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testAddKarta() throws Exception {
//		KartaDTO karta = new KartaDTO(1l, 10, 5, false, 0, "a", null, LocalDateTime.now(), null, null);
//		String s = objectMapper.writeValueAsString(karta);
//		this.mockMvc.perform(post(route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isCreated());
//	}
//	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testUpdateKarta() throws Exception {
//		KartaDTO karta = new KartaDTO(1l, 100, 5, false, 0, "aezakmi", null, LocalDateTime.now(), null, null);
//		String s = objectMapper.writeValueAsString(karta);
//		this.mockMvc.perform(put(route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isCreated());
//	}
//	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testDeleteKarta() throws Exception {
//		mockMvc.perform(delete(route + "/delete/1")).andExpect(status().isOk());
//	}
	
}
