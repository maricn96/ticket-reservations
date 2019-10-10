package com.isa.hoteli.hoteliservice.integration;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import com.isa.hoteli.hoteliservice.avio.dto.KlasaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.LetDTO;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LetIntegrationTest {
	
	private String route = "/flight";
	
	private Korisnik korisnik = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_AVIO_KOMPANIJE, 1l, true, "a", null, null, null, null, null, null, 0);
	
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	private KorisnikService korisnikService;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getLetSuccess() throws Exception {		
		mockMvc.perform(get(route + "/getone/1")).andExpect(status().isOk());
	}
	
	@Test
	public void getAllLetoviSuccess() throws Exception {		
		mockMvc.perform(get(route + "/getall")).andExpect(status().isOk());
	}

	@Test
	public void getAllLetoviZaOdredjenogAdminaSuccess() throws Exception {		
		when(korisnikService.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(korisnik);
		mockMvc.perform(get(route + "/getall/1")).andExpect(status().isOk());
	}
	
	//treba paziti koji je poslednji id za bazu pa taj ispisati
	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testAddLet() throws Exception {
//		LetDTO let = new LetDTO(1l, 1l, LocalDateTime.now(), LocalDateTime.now(), 10, 10, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null);
//		String s = objectMapper.writeValueAsString(let);
//		this.mockMvc.perform(post(route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isCreated());
//	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateLet() throws Exception {
		LetDTO let = new LetDTO(2l, 1l, LocalDateTime.now(), LocalDateTime.now(), 200, 200, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null);
		String s = objectMapper.writeValueAsString(let);
		this.mockMvc.perform(put(route + "/update/2").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isCreated());
	}
	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testDeleteLet() throws Exception {
//		mockMvc.perform(delete(route + "/delete/1")).andExpect(status().isOk());
//	}
	
}
