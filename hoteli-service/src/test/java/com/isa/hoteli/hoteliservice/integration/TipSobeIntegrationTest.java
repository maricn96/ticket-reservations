package com.isa.hoteli.hoteliservice.integration;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.TipSobe;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TipSobeIntegrationTest {
	
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_HOTELA, 1l, true, "a", null, null, null, null, null, null, 0);
	
	private MockMvc mockMvc;
	
	@MockBean
	private KorisnikService ks;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getTypesSuccess() throws Exception {		
		mockMvc.perform(get("/tip_sobe/all")).andExpect(status().isOk());
	}
	
	@Test
	public void getTypesFromHotelSuccess() throws Exception {		
		mockMvc.perform(get("/tip_sobe/all/2")).andExpect(status().isOk())/*.andExpect(jsonPath("$", hasSize(1)))*/;
	}
	
	@Test
	public void getTypeByIdSuccess() throws Exception {		
		mockMvc.perform(get("/tip_sobe/5")).andExpect(status().isOk());
	}
	
	/*@Test
	@Transactional
	@Rollback(true)
	public void testDeleteType() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		mockMvc.perform(delete("/tip_sobe/7")).andExpect(status().isOk());
	}*/
	
	/*@Test
	@Transactional
	@Rollback(true)
	public void testUpdateType() throws Exception {
		Hotel hotel = new Hotel(1l, "qwert", "q", "q", "q", 0f, 0f);
		TipSobe ts = new TipSobe(7l, "b", hotel);
		String s = objectMapper.writeValueAsString(ts);
		this.mockMvc.perform(put("/tip_sobe/7").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}*/
	
	/*@Test
	@Transactional
	@Rollback(true)
	public void testCreateType() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		Hotel hotel = new Hotel(1l, "qwert", "q", "q", "q", 0f, 0f);
		TipSobe ts = new TipSobe(7l, "b", hotel);
		String s = objectMapper.writeValueAsString(ts);
		this.mockMvc.perform(post("/tip_sobe/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk());
	}*/

}
