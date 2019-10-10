package com.isa.hoteli.hoteliservice.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.model.Hotel;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DodatnaUslugaIntegrationTest {
	
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
	public void getServicesSuccess() throws Exception {		
		mockMvc.perform(get("/usluga/all")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	public void getServiceFromHotelSuccess() throws Exception {		
		mockMvc.perform(get("/usluga/all/2")).andExpect(status().isOk());
	}
	
	@Test
	public void getServiceByIdSuccess() throws Exception {		
		mockMvc.perform(get("/usluga/3")).andExpect(status().isOk());
	}
	
	/*@Test
	@Transactional
	@Rollback(true)
	public void testDeleteService() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		mockMvc.perform(delete("/usluga/4")).andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateType() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Hotel hotel = new Hotel(1l, "qwert", "q", "q", "q", 0f, 0f);
		DodatnaUsluga du = new DodatnaUsluga(4l, "a", 400f, 5f, hotel);
		String s = objectMapper.writeValueAsString(du);
		this.mockMvc.perform(put("/usluga/4").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateService() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Hotel hotel = new Hotel(1l, "qwert", "q", "q", "q", 0f, 0f);
		DodatnaUsluga du = new DodatnaUsluga(4l, "a", 300f, 5f, hotel);
		String s = objectMapper.writeValueAsString(du);
		this.mockMvc.perform(post("/usluga/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}*/

}
