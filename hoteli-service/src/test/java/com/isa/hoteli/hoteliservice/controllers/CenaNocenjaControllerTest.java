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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.controller.CenaNocenjaController;
import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;
import com.isa.hoteli.hoteliservice.service.CenaNocenjaService;

@RunWith(SpringRunner.class)
@WebMvcTest(CenaNocenjaController.class)
public class CenaNocenjaControllerTest {
	
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis()); 
	private Date datum = new Date(System.currentTimeMillis());
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private List<CenaNocenja> cene = new ArrayList<>();
	private CenaNocenja cena1 = new CenaNocenja(1l, 20, datumOd, datumDo, soba1);
	private CenaNocenja cena2 = new CenaNocenja(1l, 40, datumOd, datumDo, soba1);
	private List<CenaNocenjaDTO> ceneDTO = new ArrayList<>();
	private CenaNocenjaDTO cena1DTO = new CenaNocenjaDTO(1l, 20, datumOd, datumDo, soba1);
	private CenaNocenjaDTO cena2DTO = new CenaNocenjaDTO(1l, 40, datumOd, datumDo, soba1);
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_HOTELA, 1l, true, "a", null, null, null, null, null, null, 0);

	
	@MockBean
	private CenaNocenjaService cns;
	
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
		cene.add(cena1);
		cene.add(cena2);
		ceneDTO.add(cena1DTO);
		ceneDTO.add(cena2DTO);
	}
	
	@Test
	public void getPricesSuccess() throws Exception {
		when(cns.getPrices()).thenReturn(cene);
		MvcResult result = this.mockMvc.perform(get("/cena/all")).andExpect(status().isOk()).andReturn();
		List<CenaNocenjaDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CenaNocenjaDTO>>() {});
		assertThat(rets.size(), is(2));
		for (CenaNocenja cn : cene) {
			ceneDTO.add(new CenaNocenjaDTO(cn));
		}
		assertThat(ceneDTO.equals(rets));
		verify(cns, times(1)).getPrices();
		verifyNoMoreInteractions(cns);
	}
	
	@Test
	public void getPricesFromHotelRoomsSuccess() throws Exception {
		when(cns.getPricesFromHotelRoom(1l)).thenReturn(cene);
		MvcResult result = this.mockMvc.perform(get("/cena/all/1")).andExpect(status().isOk()).andReturn();
		List<CenaNocenjaDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CenaNocenjaDTO>>() {});
		assertThat(rets.size(), is(2));
		for (CenaNocenja cn : cene) {
			ceneDTO.add(new CenaNocenjaDTO(cn));
		}
		assertThat(ceneDTO.equals(rets));
		verify(cns, times(1)).getPricesFromHotelRoom(1l);
		verifyNoMoreInteractions(cns);
	}
	
	@Test
	public void getPricesByIdSuccess() throws Exception {
		when(cns.getPriceById(1l)).thenReturn(cena1);
		MvcResult result = this.mockMvc.perform(get("/cena/1")).andExpect(status().isOk()).andReturn();
		CenaNocenjaDTO ret = objectMapper.readValue(result.getResponse().getContentAsString(), CenaNocenjaDTO.class);
		assertThat(ret.equals(new CenaNocenjaDTO(cena1)));
		verify(cns, times(2)).getPriceById(1l);
		verifyNoMoreInteractions(cns);
	}
	
	@Test
	public void getPricesByIdFailed() throws Exception {
		when(cns.getPriceById(1l)).thenReturn(null);
		this.mockMvc.perform(get("/cena/1")).andExpect(status().is4xxClientError());
		verify(cns, times(1)).getPriceById(1l);
		verifyNoMoreInteractions(cns);
	}
	
	@Test
	public void createPriceSuccess() throws Exception {
		when(cns.createPrice(Mockito.any(CenaNocenja.class))).thenReturn(cena1DTO);
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		String s = objectMapper.writeValueAsString(cena1DTO);
		MvcResult result = this.mockMvc.perform(post("/cena/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		CenaNocenjaDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), CenaNocenjaDTO.class);
		assertThat(dto.equals(cena1DTO));
		verify(cns, times(1)).createPrice(Mockito.any(CenaNocenja.class));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(cns);
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void createPriceFailed() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(cena1DTO);
		this.mockMvc.perform(post("/cena/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().is4xxClientError()).andReturn();
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void deletePriceSuccess() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		when(cns.getPriceById(1l)).thenReturn(cena1);
		when(cns.deletePrice(1l)).thenReturn("Obrisano");
		MvcResult result = this.mockMvc.perform(delete("/cena/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals("Obrisano"));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(cns, times(1)).getPriceById(1l);
		verify(cns, times(1)).deletePrice(1l);
		verifyNoMoreInteractions(ks);
		verifyNoMoreInteractions(cns);
	}
	
	@Test
	public void updatePriceSuccess() throws Exception {
		when(cns.updatePrice(Mockito.any(CenaNocenja.class), Mockito.any(Long.class))).thenReturn(cena1DTO);
		String s = objectMapper.writeValueAsString(cena1DTO);
		MvcResult result = this.mockMvc.perform(put("/cena/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(cena1DTO));
		verify(cns, times(1)).updatePrice(Mockito.any(CenaNocenja.class), Mockito.any(Long.class));
		verifyNoMoreInteractions(cns);
	}
	
	@Test
	public void updatePriceFailed() throws Exception {
		when(cns.updatePrice(Mockito.any(CenaNocenja.class), Mockito.any(Long.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(cena1DTO);
		MvcResult result = this.mockMvc.perform(put("/cena/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().is4xxClientError()).andReturn();
		assertThat(result.equals(cena1DTO));
		verify(cns, times(1)).updatePrice(Mockito.any(CenaNocenja.class), Mockito.any(Long.class));
		verifyNoMoreInteractions(cns);
	}
}
