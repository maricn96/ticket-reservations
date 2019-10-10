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
import com.isa.hoteli.hoteliservice.avio.dto.DestinacijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.BrojKarataDnevnoDTO;
import com.isa.hoteli.hoteliservice.avio.model.Destinacija;
import com.isa.hoteli.hoteliservice.avio.model.Destinacija;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;
import com.isa.hoteli.hoteliservice.avio.service.DestinacijaService;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.controller.HotelController;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(DestinacijaController.class)
public class DestinacijaControllerTest 
{
	private String route = "/destination";
	
	private List<Destinacija> destinacije = new ArrayList<>();
	private Destinacija destinacija1 = new Destinacija(1l, "a", "a", null, null, null, null, null);
	private Destinacija destinacija2 = new Destinacija(2l, "b", "b", null, null, null, null, null);

	private MockHttpServletRequest request = new MockHttpServletRequest();
	
	private Let let1 = new Let(1l, 1l, LocalDateTime.now(), LocalDateTime.now(), 10, 10, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null, null);
	private List<Let> letovi = new ArrayList<>();
	
	//DTO
	private List<DestinacijaDTO> destinacijeDto = new ArrayList<>();
	private DestinacijaDTO destinacija1Dto = new DestinacijaDTO(1l, "a", "a");
	private DestinacijaDTO destinacija2Dto = new DestinacijaDTO(2l, "b", "b");

	@MockBean
	private DestinacijaService destinacijaService;
	
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
		letovi.add(let1);
		destinacije.add(destinacija1);
		destinacije.add(destinacija2);
		
		destinacijeDto.add(destinacija1Dto);
		destinacijeDto.add(destinacija2Dto);
		request.addParameter("parameterName", "someValue");
		
	}
	
	
	@Test
	public void getDestinacijaSuccess() throws Exception
	{
		when(destinacijaService.findById(1l)).thenReturn(destinacija1Dto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().isOk()).
				andReturn();
		DestinacijaDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), DestinacijaDTO.class);
		assertEquals(dto, destinacija1Dto);
		verify(destinacijaService, times(1)).findById(1l);
		verifyNoMoreInteractions(destinacijaService);
	}
	
	@Test
	public void getDestinacijaFailed() throws Exception
	{
		when(destinacijaService.findById(1l)).thenReturn(null);
		this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(destinacijaService, times(1)).findById(1l);
		verifyNoMoreInteractions(destinacijaService);
	}
	
	@Test
	public void getAllDestinacijeSuccess() throws Exception
	{
		when(destinacijaService.findAll()).thenReturn(destinacijeDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getall")).
				andExpect(status().isOk()).
				andReturn();
		List<DestinacijaDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<DestinacijaDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(Destinacija dest : destinacije)
		{
			destinacijeDto.add(new DestinacijaDTO(dest));
		}
		assertThat(destinacijeDto.equals(dtos));
		verify(destinacijaService, times(1)).findAll();
		verifyNoMoreInteractions(destinacijaService);
	}
	
	@Test
	public void getAllDestinacijeByAvioKompanijaSuccess() throws Exception
	{
		when(destinacijaService.getAllDestinacijeByAvioKompanija(1l)).thenReturn(destinacijeDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getalldestsbycompany/1")).
				andExpect(status().isOk()).
				andReturn();
		List<DestinacijaDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<DestinacijaDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(Destinacija dest : destinacije)
		{
			destinacijeDto.add(new DestinacijaDTO(dest));
		}
		assertThat(destinacijeDto.equals(dtos));
		verify(destinacijaService, times(1)).getAllDestinacijeByAvioKompanija(1l);
		verifyNoMoreInteractions(destinacijaService);
	}
	
	@Test
	public void addDestinacijaSuccess() throws Exception
	{
		when(destinacijaService.saveOne(Mockito.any(DestinacijaDTO.class))).thenReturn(destinacija1Dto);
		String s = objectMapper.writeValueAsString(destinacija1Dto);
		MvcResult result = this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		DestinacijaDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), DestinacijaDTO.class);
		assertEquals(dto, destinacija1Dto);
		verify(destinacijaService, times(1)).saveOne(Mockito.any(DestinacijaDTO.class));
		verifyNoMoreInteractions(destinacijaService);
	}
	
	@Test
	public void addDestinacijaFailed() throws Exception
	{
		String s = objectMapper.writeValueAsString(destinacija1Dto);
		this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(destinacijaService, times(1)).saveOne(Mockito.any(DestinacijaDTO.class));
		verifyNoMoreInteractions(destinacijaService);
	}
	
	@Test
	public void updateDestinacijaSuccess() throws Exception
	{
		when(destinacijaService.updateOne(Mockito.any(Long.class), Mockito.any(DestinacijaDTO.class))).thenReturn(destinacija1Dto);
		String s = objectMapper.writeValueAsString(destinacija1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		assertThat(result.equals(destinacija1Dto));
		verify(destinacijaService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(DestinacijaDTO.class));
		verifyNoMoreInteractions(destinacijaService);
		
	}
	
	@Test
	public void updateDestinacijaFailed() throws Exception
	{
		when(destinacijaService.updateOne(Mockito.any(Long.class), Mockito.any(DestinacijaDTO.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(destinacija1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		assertThat(result.equals(destinacija1Dto));
		verify(destinacijaService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(DestinacijaDTO.class));
		verifyNoMoreInteractions(destinacijaService);
		
	}
	
	@Test
	public void deleteDestinacijaSuccess() throws Exception
	{
		when(destinacijaService.deleteOne(1l)).thenReturn(true);
		MvcResult result = this.mockMvc.perform(delete(this.route + "/delete/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(true));
		verify(destinacijaService, times(1)).deleteOne(1l);
		verifyNoMoreInteractions(destinacijaService);
	}
	
	
}

