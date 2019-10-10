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
import com.isa.hoteli.hoteliservice.controller.TipSobeController;
import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.dto.TipSobeDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.TipSobe;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;
import com.isa.hoteli.hoteliservice.service.TipSobeService;

@RunWith(SpringRunner.class)
@WebMvcTest(TipSobeController.class)
public class TipSobeControllerTest {
	
	private List<TipSobe> tipovi = new ArrayList<>();
	private List<TipSobeDTO> tipoviDTO = new ArrayList<>();
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private Hotel hotel2 = new Hotel(2l, "b", "b", "b", "b", 2f, 2f);
	private TipSobe tipSobe1 = new TipSobe(1l, "a", hotel1);
	private TipSobe tipSobe2 = new TipSobe(2l, "b", hotel1);
	private TipSobeDTO tipSobe1DTO = new TipSobeDTO(1l, "a", hotel1);
	private TipSobeDTO tipSobe2DTO = new TipSobeDTO(2l, "b", hotel1);
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_HOTELA, 1l, true, "a", null, null, null, null, null, null, 0);

	
	@MockBean
	private TipSobeService tss;
	
	@MockBean
	private JwtTokenUtils jwt;
	
	@MockBean
	private KorisnikService ks;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		tipovi.add(tipSobe1);
		tipovi.add(tipSobe2);
		tipoviDTO.add(tipSobe1DTO);
		tipoviDTO.add(tipSobe2DTO);
	}
	
	@Test
	public void getTypesSuccess() throws Exception {
		when(tss.getTypes()).thenReturn(tipovi);
		MvcResult result = this.mockMvc.perform(get("/tip_sobe/all")).andExpect(status().isOk()).andReturn();
		List<TipSobeDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<TipSobeDTO>>() {});
		assertThat(rets.size(), is(2));
		for (TipSobe ts : tipovi) {
			tipoviDTO.add(new TipSobeDTO(ts));
		}
		assertThat(tipoviDTO.equals(rets));
		verify(tss, times(1)).getTypes();
		verifyNoMoreInteractions(tss);
	}
	
	@Test
	public void getTypesFromHotelSuccess() throws Exception {
		when(tss.getTypesFromHotel(1l)).thenReturn(tipovi);
		MvcResult result = this.mockMvc.perform(get("/tip_sobe/all/1")).andExpect(status().isOk()).andReturn();
		List<TipSobeDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<TipSobeDTO>>() {});
		assertThat(rets.size(), is(2));
		for (TipSobe ts : tipovi) {
			tipoviDTO.add(new TipSobeDTO(ts));
		}
		assertThat(tipoviDTO.equals(rets));
		verify(tss, times(1)).getTypesFromHotel(1l);
		verifyNoMoreInteractions(tss);
	}
	
	@Test
	public void getTypesByIdSuccess() throws Exception {
		when(tss.getTipSobeById(1l)).thenReturn(tipSobe1);
		MvcResult result = this.mockMvc.perform(get("/tip_sobe/1")).andExpect(status().isOk()).andReturn();
		TipSobeDTO ret = objectMapper.readValue(result.getResponse().getContentAsString(), TipSobeDTO.class);
		assertThat(ret.equals(new TipSobeDTO(tipSobe1)));
		verify(tss, times(2)).getTipSobeById(1l);
		verifyNoMoreInteractions(tss);
	}
	
	@Test
	public void getPricesByIdFailed() throws Exception {
		when(tss.getTipSobeById(1l)).thenReturn(tipSobe1);
		this.mockMvc.perform(get("/tip_sobe/1")).andExpect(status().isOk()).andReturn();
		verify(tss, times(2)).getTipSobeById(1l);
		verifyNoMoreInteractions(tss);
	}
	
	@Test
	public void createTypeSuccess() throws Exception {
		when(tss.createType(Mockito.any(TipSobe.class))).thenReturn(tipSobe1DTO);
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		String s = objectMapper.writeValueAsString(tipSobe1DTO);
		MvcResult result = this.mockMvc.perform(post("/tip_sobe/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		TipSobeDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), TipSobeDTO.class);
		assertThat(dto.equals(tipSobe1DTO));
		verify(tss, times(1)).createType(Mockito.any(TipSobe.class));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(tss);
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void deleteTypeSuccess() throws Exception {
		when(tss.deleteType(1l)).thenReturn("Obrisano");
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		MvcResult result = this.mockMvc.perform(delete("/tip_sobe/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals("Obrisano"));
		verify(tss, times(1)).deleteType(1l);
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(ks);
		verifyNoMoreInteractions(tss);
		
	}
	
	@Test
	public void updateTypeSuccess() throws Exception {
		when(tss.updateType(Mockito.any(TipSobe.class), Mockito.any(Long.class))).thenReturn(tipSobe1DTO);
		String s = objectMapper.writeValueAsString(tipSobe1DTO);
		MvcResult result = this.mockMvc.perform(put("/tip_sobe/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(tipSobe1DTO));
		verify(tss, times(1)).updateType(Mockito.any(TipSobe.class), Mockito.any(Long.class));
		verifyNoMoreInteractions(tss);
	}
	
	@Test
	public void updateTypeFailed() throws Exception {
		when(tss.updateType(Mockito.any(TipSobe.class), Mockito.any(Long.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(tipSobe1DTO);
		MvcResult result = this.mockMvc.perform(put("/tip_sobe/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().is4xxClientError()).andReturn();
		assertThat(result.equals(tipSobe1DTO));
		verify(tss, times(1)).updateType(Mockito.any(TipSobe.class), Mockito.any(Long.class));
		verifyNoMoreInteractions(tss);
	}

}
