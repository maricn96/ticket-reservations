package com.isa.hoteli.hoteliservice.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

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
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.OcenaHotel;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OceneIntegrationTest {
	
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
	public void getHotelRatingsSuccess() throws Exception {		
		mockMvc.perform(get("/ocena/hotel/all")).andExpect(status().isOk());
	}
	
	@Test
	public void getRoomRatingsSuccess() throws Exception {		
		mockMvc.perform(get("/ocena/soba/all")).andExpect(status().isOk());
	}
	
	@Test
	public void getMeanHotelRatingsSuccess() throws Exception {		
		mockMvc.perform(get("/ocena/prosek/hotel/2")).andExpect(status().isOk());
	}
	
	@Test
	public void getMeanHotelRoomRatingsSuccess() throws Exception {		
		mockMvc.perform(get("/ocena/prosek/soba/4")).andExpect(status().isOk());
	}


}
