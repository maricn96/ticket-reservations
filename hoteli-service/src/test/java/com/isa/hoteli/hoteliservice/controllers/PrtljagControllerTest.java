package com.isa.hoteli.hoteliservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.controller.AvioKompanijaController;
import com.isa.hoteli.hoteliservice.avio.controller.DestinacijaController;
import com.isa.hoteli.hoteliservice.avio.controller.PrtljagController;
import com.isa.hoteli.hoteliservice.avio.dto.PrtljagDTO;
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.BrojKarataDnevnoDTO;
import com.isa.hoteli.hoteliservice.avio.model.Prtljag;
import com.isa.hoteli.hoteliservice.avio.model.Prtljag;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;
import com.isa.hoteli.hoteliservice.avio.service.PrtljagService;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.controller.HotelController;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(PrtljagController.class)
public class PrtljagControllerTest 
{
	private String route = "/luggage";
	
	private List<Prtljag> prtljazi = new ArrayList<>();
	private Prtljag prtljag1 = new Prtljag(1l, 1, "a", null);
	private Prtljag prtljag2 = new Prtljag(1l, 2, "b", null);
	private Korisnik korisnik = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_AVIO_KOMPANIJE, 1l, true, "a", null, null, null, null, null, null, 0);

	private MockHttpServletRequest request = new MockHttpServletRequest();

	
	//DTO
	private List<PrtljagDTO> prtljaziDto = new ArrayList<>();
	private PrtljagDTO prtljag1Dto = new PrtljagDTO(1l, 1, "a");
	private PrtljagDTO prtljag2Dto = new PrtljagDTO(2l, 2, "b");

	@MockBean
	private PrtljagService prtljagService;
	
	@MockBean
	private JwtTokenUtils jwt;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		prtljazi.add(prtljag1);
		prtljazi.add(prtljag2);
		
		prtljaziDto.add(prtljag1Dto);
		prtljaziDto.add(prtljag2Dto);
		request.addParameter("parameterName", "someValue");
		
	}
	
	
	@Test
	public void getPrtljagSuccess() throws Exception
	{
		when(prtljagService.findById(1l)).thenReturn(prtljag1Dto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().isOk()).
				andReturn();
		PrtljagDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), PrtljagDTO.class);
		assertEquals(dto, prtljag1Dto);
		verify(prtljagService, times(1)).findById(1l);
		verifyNoMoreInteractions(prtljagService);
	}
	
	@Test
	public void getPrtljagFailed() throws Exception
	{
		when(prtljagService.findById(1l)).thenReturn(null);
		this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(prtljagService, times(1)).findById(1l);
		verifyNoMoreInteractions(prtljagService);
	}
	
	@Test
	public void getAllPrtljagSuccess() throws Exception
	{
		when(prtljagService.findAll()).thenReturn(prtljaziDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getall")).
				andExpect(status().isOk()).
				andReturn();
		List<PrtljagDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<PrtljagDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(Prtljag dest : prtljazi)
		{
			prtljaziDto.add(new PrtljagDTO(dest));
		}
		assertThat(prtljaziDto.equals(dtos));
		verify(prtljagService, times(1)).findAll();
		verifyNoMoreInteractions(prtljagService);
	}
	
	@Test
	public void addPrtljagSuccess() throws Exception
	{
		when(prtljagService.saveOne(Mockito.any(PrtljagDTO.class))).thenReturn(prtljag1Dto);
		String s = objectMapper.writeValueAsString(prtljag1Dto);
		MvcResult result = this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		PrtljagDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), PrtljagDTO.class);
		assertEquals(dto, prtljag1Dto);
		verify(prtljagService, times(1)).saveOne(Mockito.any(PrtljagDTO.class));
		verifyNoMoreInteractions(prtljagService);
	}
	
	@Test
	public void addPrtljagFailed() throws Exception
	{
		String s = objectMapper.writeValueAsString(prtljag1Dto);
		this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(prtljagService, times(1)).saveOne(Mockito.any(PrtljagDTO.class));
		verifyNoMoreInteractions(prtljagService);
	}
	
	@Test
	public void updatePrtljagSuccess() throws Exception
	{
		when(prtljagService.updateOne(Mockito.any(Long.class), Mockito.any(PrtljagDTO.class))).thenReturn(prtljag1Dto);
		String s = objectMapper.writeValueAsString(prtljag1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		assertThat(result.equals(prtljag1Dto));
		verify(prtljagService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(PrtljagDTO.class));
		verifyNoMoreInteractions(prtljagService);
		
	}
	
	@Test
	public void updatePrtljagFailed() throws Exception
	{
		when(prtljagService.updateOne(Mockito.any(Long.class), Mockito.any(PrtljagDTO.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(prtljag1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		assertThat(result.equals(prtljag1Dto));
		verify(prtljagService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(PrtljagDTO.class));
		verifyNoMoreInteractions(prtljagService);
		
	}
	
	@Test
	public void deletePrtljagSuccess() throws Exception
	{
		when(prtljagService.deleteOne(1l)).thenReturn(true);
		MvcResult result = this.mockMvc.perform(delete(this.route + "/delete/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(true));
		verify(prtljagService, times(1)).deleteOne(1l);
		verifyNoMoreInteractions(prtljagService);
	}

}