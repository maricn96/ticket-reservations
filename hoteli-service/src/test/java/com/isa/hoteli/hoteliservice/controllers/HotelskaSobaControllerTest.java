package com.isa.hoteli.hoteliservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
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
import com.isa.hoteli.hoteliservice.controller.HotelskaSobaController;
import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.dto.HotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.dto.HotelskaSobaInfoDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.DodatnaUsluga;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Pretraga;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;
import com.isa.hoteli.hoteliservice.service.CenaNocenjaService;
import com.isa.hoteli.hoteliservice.service.HotelskaSobaService;
import com.isa.hoteli.hoteliservice.service.OcenaService;

@RunWith(SpringRunner.class)
@WebMvcTest(HotelskaSobaController.class)
public class HotelskaSobaControllerTest {
	
	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis());
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private List<HotelskaSoba> sobe = new ArrayList<>();
	private List<HotelskaSobaDTO> sobeDTO = new ArrayList<>();
	private List<Rezervacije> rezervacije = new ArrayList<>();
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private HotelskaSoba soba2 = new HotelskaSoba(2l, 2, 2, 2, 400, hotel1, null);
	private HotelskaSobaDTO soba1DTO = new HotelskaSobaDTO(1l, 1, 1, 1, 200, hotel1, null);
	private HotelskaSobaDTO soba2DTO = new HotelskaSobaDTO(2l, 2, 2, 2, 400, hotel1, null);
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_HOTELA, 1l, true, "a", null, null, null, null, null, null, 0);
	private Rezervacije r = new Rezervacije(1l, datumOd, datumDo, 200, 2, soba1, k, hotel1);
	private CenaNocenja cena1 = new CenaNocenja(1l, 20, datumOd, datumDo, soba1);
	private HotelskaSobaInfoDTO sobaInfo1 = new HotelskaSobaInfoDTO(1l, 1, 1, 1, 200, hotel1, null, 20f, 3f);
	private HotelskaSobaInfoDTO sobaInfo2 = new HotelskaSobaInfoDTO(2l, 2, 2, 2, 400, hotel1, null, 30f, 4f);
	private List<HotelskaSobaInfoDTO> sobeInfo = new ArrayList<>();
	private Pretraga pretraga = new Pretraga("a", datumOd, datumDo, 1, 1);

	
	@MockBean
	private HotelskaSobaService hss;
	
	@MockBean
	private OcenaService os;
	
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
		sobe.add(soba1);
		sobe.add(soba2);
		rezervacije.add(r);
		sobeDTO.add(soba1DTO);
		sobeDTO.add(soba2DTO);
		sobeInfo.add(sobaInfo1);
		sobeInfo.add(sobaInfo2);
	}
	
	@Test
	public void getRoomsSuccess() throws Exception {
		when(hss.getRooms()).thenReturn(sobe);
		MvcResult result = this.mockMvc.perform(get("/sobe/admin/all")).andExpect(status().isOk()).andReturn();
		List<HotelskaSobaDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<HotelskaSobaDTO>>() {});
		assertThat(rets.size(), is(2));
		for (HotelskaSoba hs : sobe) {
			sobeDTO.add(new HotelskaSobaDTO(hs));
		}
		assertThat(sobeDTO.equals(rets));
		verify(hss, times(1)).getRooms();
		verifyNoMoreInteractions(hss);
	}
	
	@Test
	public void getRoomsInfoSuccess() throws Exception {
		when(hss.getRooms()).thenReturn(sobe);
		when(cns.getValidPriceFromHotelRoom(1l)).thenReturn(20f);
		when(cns.getValidPriceFromHotelRoom(2l)).thenReturn(30f);
		when(os.getMeanRoomRating(1l)).thenReturn(3f);
		when(os.getMeanRoomRating(2l)).thenReturn(4f);
		MvcResult result = this.mockMvc.perform(get("/sobe/all")).andExpect(status().isOk()).andReturn();
		List<HotelskaSobaInfoDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<HotelskaSobaInfoDTO>>() {});
		assertThat(rets.size(), is(2));
		assertThat(sobeInfo.equals(rets));
		verify(hss, times(1)).getRooms();
		verify(cns, times(1)).getValidPriceFromHotelRoom(1l);
		verify(cns, times(1)).getValidPriceFromHotelRoom(2l);
		verify(os, times(1)).getMeanRoomRating(1l);
		verify(os, times(1)).getMeanRoomRating(2l);
		verifyNoMoreInteractions(os);
		verifyNoMoreInteractions(cns);
		verifyNoMoreInteractions(hss);
	}
	
	@Test
	public void getRoomsInfoFromHotelSuccess() throws Exception {
		when(hss.getRoomsFromHotel(1l)).thenReturn(sobe);
		when(cns.getValidPriceFromHotelRoom(1l)).thenReturn(20f);
		when(cns.getValidPriceFromHotelRoom(2l)).thenReturn(30f);
		when(os.getMeanRoomRating(1l)).thenReturn(3f);
		when(os.getMeanRoomRating(2l)).thenReturn(4f);
		MvcResult result = this.mockMvc.perform(get("/sobe/all/1")).andExpect(status().isOk()).andReturn();
		List<HotelskaSobaInfoDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<HotelskaSobaInfoDTO>>() {});
		assertThat(rets.size(), is(2));
		assertThat(sobeInfo.equals(rets));
		verify(hss, times(1)).getRoomsFromHotel(1l);
		verify(cns, times(1)).getValidPriceFromHotelRoom(1l);
		verify(cns, times(1)).getValidPriceFromHotelRoom(2l);
		verify(os, times(1)).getMeanRoomRating(1l);
		verify(os, times(1)).getMeanRoomRating(2l);
		verifyNoMoreInteractions(os);
		verifyNoMoreInteractions(cns);
		verifyNoMoreInteractions(hss);
	}
	
	@Test
	public void getRoomByIdSuccess() throws Exception {
		when(hss.getRoomById(1l)).thenReturn(soba1);
		MvcResult result = this.mockMvc.perform(get("/sobe/1")).andExpect(status().isOk()).andReturn();
		HotelskaSobaDTO ret = objectMapper.readValue(result.getResponse().getContentAsString(), HotelskaSobaDTO.class);
		assertThat(ret.equals(new HotelskaSobaDTO(soba1)));
		verify(hss, times(2)).getRoomById(1l);
		verifyNoMoreInteractions(hss);
	}
	
	@Test
	public void getRoomByIdFailed() throws Exception {
		when(hss.getRoomById(1l)).thenReturn(null);
		this.mockMvc.perform(get("/sobe/1")).andExpect(status().is4xxClientError()).andReturn();
		verify(hss, times(1)).getRoomById(1l);
		verifyNoMoreInteractions(hss);
	}
	
	@Test
	public void createRoomSuccess() throws Exception {
		when(hss.createRoom(Mockito.any(HotelskaSoba.class))).thenReturn(soba1DTO);
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		String s = objectMapper.writeValueAsString(soba1DTO);
		MvcResult result = this.mockMvc.perform(post("/sobe/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		HotelskaSobaDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), HotelskaSobaDTO.class);
		assertThat(dto.equals(soba1DTO));
		verify(hss, times(1)).createRoom(Mockito.any(HotelskaSoba.class));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(hss);
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void createRoomFailed() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(soba1DTO);
		this.mockMvc.perform(post("/sobe/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().is4xxClientError()).andReturn();
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void deleteRoomSuccess() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		when(hss.getRoomById(1l)).thenReturn(soba1);
		when(hss.deleteRoom(1l)).thenReturn("Obrisano");
		MvcResult result = this.mockMvc.perform(delete("/sobe/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals("Obrisano"));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(hss, times(1)).deleteRoom(1l);
		verify(hss, times(1)).getRoomById(1l);
		verifyNoMoreInteractions(ks);
		verifyNoMoreInteractions(hss);
	}
	
	@Test
	public void updateRoomSuccess() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		when(hss.updateRoom(Mockito.any(HotelskaSoba.class), Mockito.any(Long.class))).thenReturn(soba1DTO);
		when(hss.getRoomById(1l)).thenReturn(soba1);
		String s = objectMapper.writeValueAsString(soba1DTO);
		MvcResult result = this.mockMvc.perform(put("/sobe/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(soba1DTO));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(hss, times(1)).getRoomById(1l);
		verify(hss, times(1)).updateRoom(Mockito.any(HotelskaSoba.class), Mockito.any(Long.class));
		verifyNoMoreInteractions(ks);
		verifyNoMoreInteractions(hss);
	}
	
	@Test
	public void updateRoomPriceSuccess() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		when(hss.updateRoomPrice(Mockito.any(HotelskaSoba.class), Mockito.any(Long.class))).thenReturn(soba1DTO);
		when(hss.getRoomById(1l)).thenReturn(soba1);
		String s = objectMapper.writeValueAsString(soba1DTO);
		MvcResult result = this.mockMvc.perform(put("/sobe/cena/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(soba1DTO));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(hss, times(1)).getRoomById(1l);
		verify(hss, times(1)).updateRoomPrice(Mockito.any(HotelskaSoba.class), Mockito.any(Long.class));
		verifyNoMoreInteractions(ks);
		verifyNoMoreInteractions(hss);
	}

	
}
