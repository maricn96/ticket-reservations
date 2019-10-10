package com.isa.hoteli.hoteliservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.controller.KlasaController;
import com.isa.hoteli.hoteliservice.avio.dto.KlasaDTO;
import com.isa.hoteli.hoteliservice.avio.model.Klasa;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.service.KlasaService;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(KlasaController.class)
public class KlasaControllerTest 
{
	private String route = "/class";
	
	private List<Klasa> klase = new ArrayList<>();
	private Klasa klasa1 = new Klasa(1l, "a", null);
	private Klasa klasa2 = new Klasa(2l, "b", null);

	private MockHttpServletRequest request = new MockHttpServletRequest();
	
	private Let let1 = new Let(1l, 1l, LocalDateTime.now(), LocalDateTime.now(), 10, 10, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null, null);
	private List<Let> letovi = new ArrayList<>();
	
	//DTO
	private List<KlasaDTO> klaseDto = new ArrayList<>();
	private KlasaDTO klasa1Dto = new KlasaDTO(1l, "a");
	private KlasaDTO klasa2Dto = new KlasaDTO(2l, "b");

	@MockBean
	private KlasaService klasaService;
	
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
		klase.add(klasa1);
		klase.add(klasa2);
		
		klaseDto.add(klasa1Dto);
		klaseDto.add(klasa2Dto);
		request.addParameter("parameterName", "someValue");
		
	}
	
	
	@Test
	public void getKlasaSuccess() throws Exception
	{
		when(klasaService.findById(1l)).thenReturn(klasa1Dto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().isOk()).
				andReturn();
		KlasaDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), KlasaDTO.class);
		assertEquals(dto, klasa1Dto);
		verify(klasaService, times(1)).findById(1l);
		verifyNoMoreInteractions(klasaService);
	}
	
	@Test
	public void getKlasaFailed() throws Exception
	{
		when(klasaService.findById(1l)).thenReturn(null);
		this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(klasaService, times(1)).findById(1l);
		verifyNoMoreInteractions(klasaService);
	}
	
	@Test
	public void getAllKlaseSuccess() throws Exception
	{
		when(klasaService.findAll()).thenReturn(klaseDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getall")).
				andExpect(status().isOk()).
				andReturn();
		List<KlasaDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<KlasaDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(Klasa dest : klase)
		{
			klaseDto.add(new KlasaDTO(dest));
		}
		assertThat(klaseDto.equals(dtos));
		verify(klasaService, times(1)).findAll();
		verifyNoMoreInteractions(klasaService);
	}
	
	@Test
	public void addKlasaSuccess() throws Exception
	{
		when(klasaService.saveOne(Mockito.any(KlasaDTO.class))).thenReturn(klasa1Dto);
		String s = objectMapper.writeValueAsString(klasa1Dto);
		MvcResult result = this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		KlasaDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), KlasaDTO.class);
		assertEquals(dto, klasa1Dto);
		verify(klasaService, times(1)).saveOne(Mockito.any(KlasaDTO.class));
		verifyNoMoreInteractions(klasaService);
	}
	
	@Test
	public void addKlasaFailed() throws Exception
	{
		String s = objectMapper.writeValueAsString(klasa1Dto);
		this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(klasaService, times(1)).saveOne(Mockito.any(KlasaDTO.class));
		verifyNoMoreInteractions(klasaService);
	}
	
	@Test
	public void updateKlasaSuccess() throws Exception
	{
		when(klasaService.updateOne(Mockito.any(Long.class), Mockito.any(KlasaDTO.class))).thenReturn(klasa1Dto);
		String s = objectMapper.writeValueAsString(klasa1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		assertThat(result.equals(klasa1Dto));
		verify(klasaService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(KlasaDTO.class));
		verifyNoMoreInteractions(klasaService);
		
	}
	
	@Test
	public void updateKlasaFailed() throws Exception
	{
		when(klasaService.updateOne(Mockito.any(Long.class), Mockito.any(KlasaDTO.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(klasa1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		assertThat(result.equals(klasa1Dto));
		verify(klasaService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(KlasaDTO.class));
		verifyNoMoreInteractions(klasaService);
		
	}
	
	@Test
	public void deleteKlasaSuccess() throws Exception
	{
		when(klasaService.deleteOne(1l)).thenReturn(true);
		MvcResult result = this.mockMvc.perform(delete(this.route + "/delete/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(true));
		verify(klasaService, times(1)).deleteOne(1l);
		verifyNoMoreInteractions(klasaService);
	}

}