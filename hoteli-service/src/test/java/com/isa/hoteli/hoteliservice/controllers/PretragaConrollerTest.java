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
import com.isa.hoteli.hoteliservice.controller.PretragaController;
import com.isa.hoteli.hoteliservice.dto.HotelInfoDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Pretraga;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;
import com.isa.hoteli.hoteliservice.service.OcenaService;
import com.isa.hoteli.hoteliservice.service.PretragaService;

@RunWith(SpringRunner.class)
@WebMvcTest(PretragaController.class)
public class PretragaConrollerTest {

	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis());
	private List<Hotel> hotels = new ArrayList<>();;
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private Hotel hotel2 = new Hotel(2l, "b", "b", "b", "b", 2f, 2f);
	private List<HotelskaSoba> sobe = new ArrayList<>();
	private List<Hotel> hotelsRet = new ArrayList<>();
	private List<HotelInfoDTO> hotelsInfoDTO = new ArrayList<>();
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private HotelskaSoba soba2 = new HotelskaSoba(2l, 2, 2, 2, 400, hotel1, null);
	private Pretraga pretraga = new Pretraga("a", datumOd, datumDo, 1, 1);
	
	@MockBean
	private PretragaService ps;
	
	@MockBean
	private OcenaService os;
	
	@MockBean
	private JwtTokenUtils jwt;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		hotels.add(hotel1);
		hotels.add(hotel2);
	}
	
	@Test
	public void getHotelsInfoSuccess() throws Exception {
		when(ps.getSearch(Mockito.any(Pretraga.class))).thenReturn(hotels);
		when(os.getMeanHotelRating(1l)).thenReturn(3f);
		when(os.getMeanHotelRating(2l)).thenReturn(3f);
		for (Hotel hotel : hotels) {
			hotelsInfoDTO.add(new HotelInfoDTO(hotel.getId(), hotel.getNaziv(), hotel.getAdresa(), hotel.getOpis(), 3f, 1f, 1f));
		}
		String s = objectMapper.writeValueAsString(pretraga);
		MvcResult result = this.mockMvc.perform(post("/pretraga/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		List<HotelInfoDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<HotelInfoDTO>>() {});
		assertThat(rets.size(), is(2));
		assertThat(hotelsInfoDTO.equals(rets));
		verify(ps, times(1)).getSearch(Mockito.any(Pretraga.class));
		verify(os, times(1)).getMeanHotelRating(1l);
		verify(os, times(1)).getMeanHotelRating(2l);
		verifyNoMoreInteractions(ps);
		verifyNoMoreInteractions(os);
	}
	
}
