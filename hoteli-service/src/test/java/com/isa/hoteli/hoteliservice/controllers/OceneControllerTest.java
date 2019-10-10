package com.isa.hoteli.hoteliservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
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
import com.isa.hoteli.hoteliservice.controller.OcenaController;
import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.dto.OcenaHotelDTO;
import com.isa.hoteli.hoteliservice.dto.OcenaHotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.OcenaHotel;
import com.isa.hoteli.hoteliservice.model.OcenaHotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;
import com.isa.hoteli.hoteliservice.service.OcenaService;

@RunWith(SpringRunner.class)
@WebMvcTest(OcenaController.class)
public class OceneControllerTest {
	
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.MASTER_ADMIN, null, true, "a", null, null, null, null, null, null, 0);
	private Korisnik k1 = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.KORISNIK, null, true, "a", null, null, null, null, null, null, 0);
	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis());
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private Date datum = new Date(System.currentTimeMillis());
	private List<OcenaHotel> oceneHotela = new ArrayList<>();
	private List<OcenaHotelDTO> oceneHotelaDTO = new ArrayList<>();
	private List<OcenaHotelskaSobaDTO> oceneSobaDTO = new ArrayList<>();
	private OcenaHotel ocenaHotela1 = new OcenaHotel(1l, 3, datum, 1l, 1l, 1l);
	private OcenaHotelDTO ocenaHotela1DTO = new OcenaHotelDTO(1l, 3, datum, 1l, 1l, 1l);
	private OcenaHotel ocenaHotela2 = new OcenaHotel(2l, 4, datum, 1l, 1l, 1l);
	private List<OcenaHotelskaSoba> oceneSoba = new ArrayList<>();
	private OcenaHotelskaSoba ocenaSoba1 = new OcenaHotelskaSoba(1l, 3, datum, 1l, 1l, 1l);
	private OcenaHotelskaSobaDTO ocenaSoba1DTO = new OcenaHotelskaSobaDTO(1l, 3, datum, 1l, 1l, 1l);
	private OcenaHotelskaSoba ocenaSoba2 = new OcenaHotelskaSoba(2l, 5, datum, 1l, 1l, 1l);
	private Rezervacije r = new Rezervacije(1l, datumOd, datumDo, 200, 2, soba1, k, hotel1);
	
	@MockBean
	private OcenaService os;
	
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
		oceneHotela.add(ocenaHotela1);
		oceneHotela.add(ocenaHotela2);
		oceneSoba.add(ocenaSoba1);
		oceneSoba.add(ocenaSoba2);
	}
	
	@Test
	public void getHotelRatingsSuccess() throws Exception {
		when(os.getHotelRatings()).thenReturn(oceneHotela);
		MvcResult result = this.mockMvc.perform(get("/ocena/hotel/all")).andExpect(status().isOk()).andReturn();
		List<OcenaHotelDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<OcenaHotelDTO>>() {});
		assertThat(rets.size(), is(2));
		for (OcenaHotel oh : oceneHotela) {
			oceneHotelaDTO.add(new OcenaHotelDTO(oh));
		}
		assertThat(oceneHotelaDTO.equals(rets));
		verify(os, times(1)).getHotelRatings();
		verifyNoMoreInteractions(os);
	}
	
	@Test
	public void getHotelRoomRatingsSuccess() throws Exception {
		when(os.getRoomRatings()).thenReturn(oceneSoba);
		MvcResult result = this.mockMvc.perform(get("/ocena/soba/all")).andExpect(status().isOk()).andReturn();
		List<OcenaHotelskaSobaDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<OcenaHotelskaSobaDTO>>() {});
		assertThat(rets.size(), is(2));
		for (OcenaHotelskaSoba oh : oceneSoba) {
			oceneSobaDTO.add(new OcenaHotelskaSobaDTO(oh));
		}
		assertThat(oceneSobaDTO.equals(rets));
		verify(os, times(1)).getRoomRatings();
		verifyNoMoreInteractions(os);
	}
	
	@Test
	public void createHotelRatingSuccess() throws Exception {
		when(os.createHotelRating(Mockito.any(OcenaHotel.class))).thenReturn(ocenaHotela1DTO);
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k1);
		String s = objectMapper.writeValueAsString(ocenaHotela1DTO);
		MvcResult result = this.mockMvc.perform(post("/ocena/hotel/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		OcenaHotelDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), OcenaHotelDTO.class);
		assertThat(dto.equals(ocenaHotela1DTO));
		verify(os, times(1)).createHotelRating(Mockito.any(OcenaHotel.class));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(ks);
		verifyNoMoreInteractions(os);
	}
	
	@Test
	public void createRoomRatingSuccess() throws Exception {
		when(os.createHotelRoomRating(Mockito.any(OcenaHotelskaSoba.class))).thenReturn(ocenaSoba1DTO);
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k1);
		String s = objectMapper.writeValueAsString(ocenaSoba1DTO);
		MvcResult result = this.mockMvc.perform(post("/ocena/soba/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		OcenaHotelskaSobaDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), OcenaHotelskaSobaDTO.class);
		assertThat(dto.equals(ocenaSoba1DTO));
		verify(os, times(1)).createHotelRoomRating(Mockito.any(OcenaHotelskaSoba.class));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(ks);
		verifyNoMoreInteractions(os);
	}
	
	@Test
	public void hotelMeanByIdSuccess() throws Exception {
		when(os.getMeanHotelRating(1l)).thenReturn(3f);
		MvcResult result = this.mockMvc.perform(get("/ocena/prosek/hotel/1")).andExpect(status().isOk()).andReturn();
		Float dto = objectMapper.readValue(result.getResponse().getContentAsString(), Float.class);
		assertThat(dto.equals(3f));
		verify(os, times(1)).getMeanHotelRating(1l);
		verifyNoMoreInteractions(os);
	}
	
	@Test
	public void roomMeanByIdSuccess() throws Exception {
		when(os.getMeanRoomRating(1l)).thenReturn(3f);
		MvcResult result = this.mockMvc.perform(get("/ocena/prosek/soba/1")).andExpect(status().isOk()).andReturn();
		Float dto = objectMapper.readValue(result.getResponse().getContentAsString(), Float.class);
		assertThat(dto.equals(3f));
		verify(os, times(1)).getMeanRoomRating(1l);
		verifyNoMoreInteractions(os);
	}

}
