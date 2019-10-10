package com.isa.hoteli.hoteliservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.controller.DodatnaUslugaController;
import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.dto.DodatnaUslugaDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;
import com.isa.hoteli.hoteliservice.service.DodatnaUslugaService;

@RunWith(SpringRunner.class)
@WebMvcTest(DodatnaUslugaController.class)
public class DodatnaUslugaControllerTest {
	
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private List<DodatnaUsluga> usluge = new ArrayList<>();
	private DodatnaUsluga usluga1 = new DodatnaUsluga(1l, "a", 50, 2, hotel1);
	private DodatnaUsluga usluga2 = new DodatnaUsluga(1l, "b", 100, 5, hotel1);
	private List<DodatnaUslugaDTO> uslugeDTO = new ArrayList<>();
	private DodatnaUslugaDTO usluga1DTO = new DodatnaUslugaDTO(1l, "a", 50, 2, hotel1);
	private DodatnaUslugaDTO usluga2DTO = new DodatnaUslugaDTO(1l, "b", 100, 5, hotel1);
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_HOTELA, 1l, true, "a", null, null, null, null, null, null, 0);

	@MockBean
	private DodatnaUslugaService dus;
	
	@MockBean
	private KorisnikService ks;
	
	@MockBean
	private JwtTokenUtils jwt;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		usluge.add(usluga1);
		usluge.add(usluga2);
	}
	
	@Test
	public void getServicesSuccess() throws Exception {
		when(dus.getServices()).thenReturn(usluge);
		MvcResult result = this.mockMvc.perform(get("/usluga/all")).andExpect(status().isOk()).andReturn();
		List<DodatnaUslugaDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<DodatnaUslugaDTO>>() {});
		assertThat(rets.size(), is(2));
		for (DodatnaUsluga du : usluge) {
			uslugeDTO.add(new DodatnaUslugaDTO(du));
		}
		assertThat(uslugeDTO.equals(rets));
		verify(dus, times(1)).getServices();
		verifyNoMoreInteractions(dus);
	}
	
	@Test
	public void getServicesFromHotelRoomsSuccess() throws Exception {
		when(dus.getServicesFromHotel(1l)).thenReturn(usluge);
		MvcResult result = this.mockMvc.perform(get("/usluga/all/1")).andExpect(status().isOk()).andReturn();
		List<DodatnaUslugaDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<DodatnaUslugaDTO>>() {});
		assertThat(rets.size(), is(2));
		for (DodatnaUsluga du : usluge) {
			uslugeDTO.add(new DodatnaUslugaDTO(du));
		}
		assertThat(uslugeDTO.equals(rets));
		verify(dus, times(1)).getServicesFromHotel(1l);
		verifyNoMoreInteractions(dus);
	}
	
	@Test
	public void getServiceByIdSuccess() throws Exception {
		when(dus.getServiceById(1l)).thenReturn(usluga1);
		MvcResult result = this.mockMvc.perform(get("/usluga/1")).andExpect(status().isOk()).andReturn();
		DodatnaUslugaDTO ret = objectMapper.readValue(result.getResponse().getContentAsString(), DodatnaUslugaDTO.class);
		assertThat(ret.equals(new DodatnaUslugaDTO(usluga1)));
		verify(dus, times(2)).getServiceById(1l);
		verifyNoMoreInteractions(dus);
	}

	@Test
	public void createServiceSuccess() throws Exception {
		when(dus.createService(Mockito.any(DodatnaUsluga.class))).thenReturn(usluga1DTO);
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		String s = objectMapper.writeValueAsString(usluga1DTO);
		MvcResult result = this.mockMvc.perform(post("/usluga/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		DodatnaUslugaDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), DodatnaUslugaDTO.class);
		assertThat(dto.equals(usluga1DTO));
		verify(dus, times(1)).createService(Mockito.any(DodatnaUsluga.class));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(dus);
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void createServiceFailed() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(usluga1DTO);
		this.mockMvc.perform(post("/usluga/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().is4xxClientError()).andReturn();
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void deleteServiceSuccess() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		when(dus.getServiceById(1l)).thenReturn(usluga1);
		when(dus.deleteService(1l)).thenReturn("Obrisano");
		MvcResult result = this.mockMvc.perform(delete("/usluga/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals("Obrisano"));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(dus, times(1)).getServiceById(1l);
		verify(dus, times(1)).deleteService(1l);
		verifyNoMoreInteractions(ks);
		verifyNoMoreInteractions(dus);
	}
	
	@Test
	public void updateServiceSuccess() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		when(dus.updateService(Mockito.any(DodatnaUsluga.class), Mockito.any(Long.class))).thenReturn(usluga1DTO);
		String s = objectMapper.writeValueAsString(usluga1DTO);
		MvcResult result = this.mockMvc.perform(put("/usluga/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(usluga1DTO));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(dus, times(1)).updateService(Mockito.any(DodatnaUsluga.class), Mockito.any(Long.class));
		verifyNoMoreInteractions(dus);
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void updateServiceFailed() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		when(dus.updateService(Mockito.any(DodatnaUsluga.class), Mockito.any(Long.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(usluga1DTO);
		MvcResult result = this.mockMvc.perform(put("/usluga/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().is4xxClientError()).andReturn();
		assertThat(result.equals(usluga1DTO));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(dus, times(1)).updateService(Mockito.any(DodatnaUsluga.class), Mockito.any(Long.class));
		verifyNoMoreInteractions(dus);
		verifyNoMoreInteractions(ks);
	}
}
