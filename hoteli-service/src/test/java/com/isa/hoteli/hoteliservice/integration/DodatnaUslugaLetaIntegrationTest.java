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
import com.isa.hoteli.hoteliservice.avio.dto.DestinacijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.DodatnaUslugaLetaDTO;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DodatnaUslugaLetaIntegrationTest {
	
	private String route = "/service";
	
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
	public void getDodatnaUslugaSuccess() throws Exception {		
		mockMvc.perform(get(route + "/getone/1")).andExpect(status().isOk());
	}
	
	@Test
	public void getAllDodatneUslugeSuccess() throws Exception {		
		mockMvc.perform(get(route + "/getall")).andExpect(status().isOk());
	}

	
	//treba paziti koji je poslednji id za bazu pa taj ispisati
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddDodatnaUsluga() throws Exception {
		DodatnaUslugaLetaDTO usluga = new DodatnaUslugaLetaDTO(14l, "a");
		String s = objectMapper.writeValueAsString(usluga);
		this.mockMvc.perform(post(route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isCreated());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateDodatnaUsluga() throws Exception {
		DodatnaUslugaLetaDTO usluga = new DodatnaUslugaLetaDTO(6l, "b");
		String s = objectMapper.writeValueAsString(usluga);
		this.mockMvc.perform(put(route + "/update/6").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isCreated());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteDodatnaUsluga() throws Exception {
		mockMvc.perform(delete(route + "/delete/1")).andExpect(status().isOk());
	}
	
}
