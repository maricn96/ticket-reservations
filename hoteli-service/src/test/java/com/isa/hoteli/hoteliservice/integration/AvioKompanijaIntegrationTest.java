package com.isa.hoteli.hoteliservice.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AvioKompanijaIntegrationTest {
	
	private Korisnik korisnik = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_AVIO_KOMPANIJE, 1l, true, "a", null, null, null, null, null, null, 0);
	private String route = "/aviocompany";
	
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
	public void getAvioKompanijaSuccess() throws Exception {		
		mockMvc.perform(get(route + "/getone/2")).andExpect(status().isOk());
	}

	@Test
	public void getAllAvioKompanijeSuccess() throws Exception {		
		mockMvc.perform(get(route + "/getall")).andExpect(status().isOk());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testupdateAvioKompanija() throws Exception {
		when(korisnikService.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(korisnik);
		AvioKompanijaDTO kompanija = new AvioKompanijaDTO(1l, "a", "a", "a", 1.0f, 1.0f, null);
		String s = objectMapper.writeValueAsString(kompanija);
		this.mockMvc.perform(put(route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).andDo(print());
	}
	
	
	
}
