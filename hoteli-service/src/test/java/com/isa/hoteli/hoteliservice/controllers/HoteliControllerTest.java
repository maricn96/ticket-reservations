package com.isa.hoteli.hoteliservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.controller.HotelController;
import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.dto.HotelInfoDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Pretraga;
import com.isa.hoteli.hoteliservice.model.TipSobe;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;
import com.isa.hoteli.hoteliservice.service.HotelService;
import com.isa.hoteli.hoteliservice.service.HotelskaSobaService;
import com.isa.hoteli.hoteliservice.service.OcenaService;

@RunWith(SpringRunner.class)
@WebMvcTest(HotelController.class)
public class HoteliControllerTest {

	private List<Hotel> hotels = new ArrayList<>();;
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private Hotel hotel2 = new Hotel(2l, "b", "b", "b", "b", 2f, 2f);
	private List<HotelDTO> hotelsDTO = new ArrayList<>();
	private HotelDTO hotel1DTO = new HotelDTO(1l, "a", "a", "a", "a", 1f, 1f);
	private HotelDTO hotel2DTO = new HotelDTO(2l, "b", "b", "b", "b", 2f, 2f);
	private List<HotelInfoDTO> hotelsInfoDTO = new ArrayList<>();
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private List<HotelskaSoba> sobe = new ArrayList<>();
	private HotelskaSoba hotelskaSoba1= new HotelskaSoba(1l, 1, 1, 1, 1, hotel1, null);
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.MASTER_ADMIN, 1l, true, "a", null, null, null, null, null, null, 0);

	
	@MockBean
	private HotelService hotelService;
	
	@MockBean
	private OcenaService ocenaService;
	
	@MockBean
	private HotelskaSobaService hss;
	
	@MockBean
	private KorisnikService ks;
	
	@MockBean
	private KorisnikRepository kr;
	
	@MockBean
	private JwtTokenUtils jwt;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		hotels.add(hotel1);
		hotels.add(hotel2);
		hotelsDTO.add(hotel1DTO);
		hotelsDTO.add(hotel2DTO);
		request.addParameter("parameterName", "someValue");
		sobe.add(hotelskaSoba1);
	}
	
	@Test
	public void getHotelsSuccess() throws Exception {
		when(hotelService.getHotels()).thenReturn(hotels);
		MvcResult result = this.mockMvc.perform(get("/hotel/test/all")).andExpect(status().isOk()).andReturn();
		List<HotelDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<HotelDTO>>() {});
		assertThat(rets.size(), is(2));
		for (Hotel hotel : hotels) {
			hotelsDTO.add(new HotelDTO(hotel));
		}
		assertThat(hotelsDTO.equals(rets));
		verify(hotelService, times(1)).getHotels();
		verifyNoMoreInteractions(hotelService);
	}
	
	@Test
	public void getHotelsInfoSuccess() throws Exception {
		when(hotelService.getHotels()).thenReturn(hotels);
		when(ocenaService.getMeanHotelRating(1l)).thenReturn((float) 1);
		when(ocenaService.getMeanHotelRating(2l)).thenReturn((float) 1);
		for (Hotel hotel : hotels) {
			hotelsInfoDTO.add(new HotelInfoDTO(hotel.getId(), hotel.getNaziv(), hotel.getAdresa(), hotel.getOpis(), 1, 1, 1));
		}
		MvcResult result = this.mockMvc.perform(get("/hotel/all")).andExpect(status().isOk()).andReturn();
		List<HotelInfoDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<HotelInfoDTO>>() {});
		assertThat(rets.size(), is(2));
		assertThat(hotelsInfoDTO.equals(rets));
		verify(hotelService, times(1)).getHotels();
		verify(ocenaService, times(1)).getMeanHotelRating(1l);
		verify(ocenaService, times(1)).getMeanHotelRating(2l);
		verifyNoMoreInteractions(hotelService);
		verifyNoMoreInteractions(ocenaService);
	}
	
	@Test 
	public void getHotelByIdSuccess() throws Exception {
		when(hotelService.getHotelById(1l)).thenReturn(hotel1);
		MvcResult result = this.mockMvc.perform(get("/hotel/1")).andExpect(status().isOk()).andReturn();
		HotelDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), HotelDTO.class);
		assertThat(new HotelDTO(hotel1).equals(dto));
		verify(hotelService, times(2)).getHotelById(1l);
		verifyNoMoreInteractions(hotelService);
	}
	
	@Test 
	public void getHotelByIdFailed() throws Exception {
		when(hotelService.getHotelById(1l)).thenReturn(null);
		this.mockMvc.perform(get("/hotel/1")).andExpect(status().is4xxClientError()).andReturn();
		verify(hotelService, times(1)).getHotelById(1l);
		verifyNoMoreInteractions(hotelService);
	}
	
	@Test
	public void createHotelSuccess() throws Exception{
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		when(hotelService.createHotel(Mockito.any(Hotel.class))).thenReturn(hotel1DTO);
		String s = objectMapper.writeValueAsString(hotel1DTO);
		MvcResult result = this.mockMvc.perform(post("/hotel/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		HotelDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), HotelDTO.class);
		assertThat(dto.equals(hotel1DTO));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(hotelService, times(1)).createHotel(Mockito.any(Hotel.class));
		verifyNoMoreInteractions(hotelService);
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void createHotelFailed() throws Exception{
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		when(hotelService.createHotel(Mockito.any(Hotel.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(hotel1DTO);
		this.mockMvc.perform(post("/hotel/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().is4xxClientError()).andReturn();
		verify(hotelService, times(1)).createHotel(Mockito.any(Hotel.class));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(hotelService);
		verifyNoMoreInteractions(ks);
	}
	
	
}
