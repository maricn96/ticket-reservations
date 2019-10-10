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
import com.isa.hoteli.hoteliservice.controller.RezervacijeController;
import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.dto.RezervacijeDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Posecenost;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;
import com.isa.hoteli.hoteliservice.service.RezervacijeService;

@RunWith(SpringRunner.class)
@WebMvcTest(RezervacijeController.class)
public class RezervacijaControllerTest {
	
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.KORISNIK, null, true, "a", null, null, null, null, null, null, 0);
	private Korisnik k1 = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_HOTELA, 1l, true, "a", null, null, null, null, null, null, 0);

	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis());
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private Rezervacije r1 = new Rezervacije(1l, datumOd, datumDo, 200, 2, soba1, k, hotel1);
	private Rezervacije r2 = new Rezervacije(1l, datumOd, datumDo, 200, 2, soba1, k, hotel1);
	private RezervacijeDTO r1DTO = new RezervacijeDTO(1l, datumOd, datumDo, 200, 2, soba1, k, hotel1);
	private RezervacijeDTO r2DTO = new RezervacijeDTO(1l, datumOd, datumDo, 200, 2, soba1, k, hotel1);
	private List<Rezervacije> rezervacije = new ArrayList<>();
	private List<RezervacijeDTO> rezervacijeDTO = new ArrayList<>();
	private Posecenost posecenost = new Posecenost(1l, datumOd);
	
	@MockBean
	private RezervacijeService rs;
	
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
		rezervacije.add(r1);
		rezervacije.add(r2);
		rezervacijeDTO.add(r1DTO);
		rezervacijeDTO.add(r2DTO);
	}

	@Test
	public void getReservationsSuccess() throws Exception {
		when(rs.getRezervations()).thenReturn(rezervacije);
		MvcResult result = this.mockMvc.perform(get("/rezervacija/all")).andExpect(status().isOk()).andReturn();
		List<RezervacijeDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<RezervacijeDTO>>() {});
		assertThat(rets.size(), is(2));
		for (Rezervacije r : rezervacije) {
			rezervacijeDTO.add(new RezervacijeDTO(r));
		}
		assertThat(rezervacijeDTO.equals(rets));
		verify(rs, times(1)).getRezervations();
		verifyNoMoreInteractions(rs);
	}
	
	@Test
	public void getReservationsFromHotelRoomsSuccess() throws Exception {
		when(rs.getReservationsFromHotelRoom(1l)).thenReturn(rezervacije);
		MvcResult result = this.mockMvc.perform(get("/rezervacija/all/1")).andExpect(status().isOk()).andReturn();
		List<RezervacijeDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<RezervacijeDTO>>() {});
		assertThat(rets.size(), is(2));
		for (Rezervacije r : rezervacije) {
			rezervacijeDTO.add(new RezervacijeDTO(r));
		}
		assertThat(rezervacijeDTO.equals(rets));
		verify(rs, times(1)).getReservationsFromHotelRoom(1l);
		verifyNoMoreInteractions(rs);
	}
	
	@Test
	public void getReservationsFromUserSuccess() throws Exception {
		when(rs.getReservationsFromUser(1l)).thenReturn(rezervacije);
		MvcResult result = this.mockMvc.perform(get("/rezervacija/user/1")).andExpect(status().isOk()).andReturn();
		List<RezervacijeDTO> rets = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<RezervacijeDTO>>() {});
		assertThat(rets.size(), is(2));
		for (Rezervacije r : rezervacije) {
			rezervacijeDTO.add(new RezervacijeDTO(r));
		}
		assertThat(rezervacijeDTO.equals(rets));
		verify(rs, times(1)).getReservationsFromUser(1l);
		verifyNoMoreInteractions(rs);
	}
	
	@Test
	public void getReservationsByIdSuccess() throws Exception {
		when(rs.getReservationById(1l)).thenReturn(r1);
		MvcResult result = this.mockMvc.perform(get("/rezervacija/1")).andExpect(status().isOk()).andReturn();
		RezervacijeDTO ret = objectMapper.readValue(result.getResponse().getContentAsString(), RezervacijeDTO.class);
		assertThat(ret.equals(new RezervacijeDTO(r1)));
		verify(rs, times(2)).getReservationById(1l);
		verifyNoMoreInteractions(rs);
	}
	
	@Test
	public void getReservationsByIdFailed() throws Exception {
		when(rs.getReservationById(1l)).thenReturn(null);
		this.mockMvc.perform(get("/rezervacija/1")).andExpect(status().is4xxClientError()).andReturn();
		verify(rs, times(1)).getReservationById(1l);
		verifyNoMoreInteractions(rs);
	}
	
	@Test
	public void createReservationSuccess() throws Exception {
		when(rs.createReservation(Mockito.any(Rezervacije.class))).thenReturn(r1DTO);
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		String s = objectMapper.writeValueAsString(r1DTO);
		MvcResult result = this.mockMvc.perform(post("/rezervacija/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		RezervacijeDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), RezervacijeDTO.class);
		assertThat(dto.equals(r1DTO));
		verify(rs, times(1)).createReservation(Mockito.any(Rezervacije.class));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(rs);
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void createReservationFailed() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(r1DTO);
		this.mockMvc.perform(post("/rezervacija/").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().is4xxClientError()).andReturn();
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void deleteReservationSuccess() throws Exception {
		when(rs.deleteReservation(1l)).thenReturn("Obrisano");
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(k);
		MvcResult result = this.mockMvc.perform(delete("/rezervacija/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals("Obrisano"));
		verify(rs, times(1)).deleteReservation(1l);
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verifyNoMoreInteractions(rs);
		verifyNoMoreInteractions(ks);
	}
	
	@Test
	public void updateReservationSuccess() throws Exception {
		when(rs.updateReservation(Mockito.any(Rezervacije.class), Mockito.any(Long.class))).thenReturn(r1DTO);
		String s = objectMapper.writeValueAsString(r1DTO);
		MvcResult result = this.mockMvc.perform(put("/rezervacija/1").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(r1DTO));
		verify(rs, times(1)).updateReservation(Mockito.any(Rezervacije.class), Mockito.any(Long.class));
		verifyNoMoreInteractions(rs);
	}
	
	@Test
	public void posecenostDnevnaFailed() throws Exception {
		when(ks.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(posecenost);
		MvcResult result = this.mockMvc.perform(post("/rezervacija/posecenost/dnevna").contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(200));
		verify(ks, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));		verifyNoMoreInteractions(rs);
		verifyNoMoreInteractions(ks);
	}
}
