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
import com.isa.hoteli.hoteliservice.avio.controller.DodatnaUslugaLetaController;
import com.isa.hoteli.hoteliservice.avio.dto.DodatnaUslugaLetaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.BrojKarataDnevnoDTO;
import com.isa.hoteli.hoteliservice.avio.model.DodatnaUslugaLeta;
import com.isa.hoteli.hoteliservice.avio.model.DodatnaUslugaLeta;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;
import com.isa.hoteli.hoteliservice.avio.service.DestinacijaService;
import com.isa.hoteli.hoteliservice.avio.service.DodatnaUslugaLetaService;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.controller.HotelController;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(DodatnaUslugaLetaController.class)
public class DodatnaUslugaLetaControllerTest 
{
	private String route = "/service";
	
	private List<DodatnaUslugaLeta> usluge = new ArrayList<>();
	private DodatnaUslugaLeta usluga1 = new DodatnaUslugaLeta(1l, "a", null);
	private DodatnaUslugaLeta usluga2 = new DodatnaUslugaLeta(2l, "b", null);

	private MockHttpServletRequest request = new MockHttpServletRequest();

	//DTO
	private List<DodatnaUslugaLetaDTO> uslugeDto = new ArrayList<>();
	private DodatnaUslugaLetaDTO usluga1Dto = new DodatnaUslugaLetaDTO(1l, "a");
	private DodatnaUslugaLetaDTO usluga2Dto = new DodatnaUslugaLetaDTO(2l, "a");

	@MockBean
	private DodatnaUslugaLetaService uslugaService;
	
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
		usluge.add(usluga1);
		usluge.add(usluga2);
		
		uslugeDto.add(usluga1Dto);
		uslugeDto.add(usluga2Dto);
		request.addParameter("parameterName", "someValue");
	}
	
	
	@Test
	public void getDodatnaUslugaSuccess() throws Exception
	{
		when(uslugaService.findById(1l)).thenReturn(usluga1Dto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().isOk()).
				andReturn();
		DodatnaUslugaLetaDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), DodatnaUslugaLetaDTO.class);
		assertEquals(dto, usluga1Dto);
		verify(uslugaService, times(1)).findById(1l);
		verifyNoMoreInteractions(uslugaService);
	}
	
	@Test
	public void getDodatnaUslugaFailed() throws Exception
	{
		when(uslugaService.findById(1l)).thenReturn(null);
		this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(uslugaService, times(1)).findById(1l);
		verifyNoMoreInteractions(uslugaService);
	}
	
	@Test
	public void getAllDodatneUslugeSuccess() throws Exception
	{
		when(uslugaService.findAll()).thenReturn(uslugeDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getall")).
				andExpect(status().isOk()).
				andReturn();
		List<DodatnaUslugaLetaDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<DodatnaUslugaLetaDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(DodatnaUslugaLeta dest : usluge)
		{
			uslugeDto.add(new DodatnaUslugaLetaDTO(dest));
		}
		assertThat(uslugeDto.equals(dtos));
		verify(uslugaService, times(1)).findAll();
		verifyNoMoreInteractions(uslugaService);
	}
	
	@Test
	public void addDodatnaUslugaSuccess() throws Exception
	{
		when(uslugaService.saveOne(Mockito.any(DodatnaUslugaLetaDTO.class))).thenReturn(usluga1Dto);
		String s = objectMapper.writeValueAsString(usluga1Dto);
		MvcResult result = this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		DodatnaUslugaLetaDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), DodatnaUslugaLetaDTO.class);
		assertEquals(dto, usluga1Dto);
		verify(uslugaService, times(1)).saveOne(Mockito.any(DodatnaUslugaLetaDTO.class));
		verifyNoMoreInteractions(uslugaService);
	}
	
	@Test
	public void addDodatnaUslugaFailed() throws Exception
	{
		String s = objectMapper.writeValueAsString(usluga1Dto);
		this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(uslugaService, times(1)).saveOne(Mockito.any(DodatnaUslugaLetaDTO.class));
		verifyNoMoreInteractions(uslugaService);
	}
	
	@Test
	public void updateDodatnaUslugaSuccess() throws Exception
	{
		when(uslugaService.updateOne(Mockito.any(Long.class), Mockito.any(DodatnaUslugaLetaDTO.class))).thenReturn(usluga1Dto);
		String s = objectMapper.writeValueAsString(usluga1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		assertThat(result.equals(usluga1Dto));
		verify(uslugaService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(DodatnaUslugaLetaDTO.class));
		verifyNoMoreInteractions(uslugaService);
		
	}
	
	@Test
	public void updateDodatnaUslugaFailed() throws Exception
	{
		when(uslugaService.updateOne(Mockito.any(Long.class), Mockito.any(DodatnaUslugaLetaDTO.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(usluga1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		assertThat(result.equals(usluga1Dto));
		verify(uslugaService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(DodatnaUslugaLetaDTO.class));
		verifyNoMoreInteractions(uslugaService);
		
	}
	
	@Test
	public void deleteDodatnaUslugaSuccess() throws Exception
	{
		when(uslugaService.deleteOne(1l)).thenReturn(true);
		MvcResult result = this.mockMvc.perform(delete(this.route + "/delete/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(true));
		verify(uslugaService, times(1)).deleteOne(1l);
		verifyNoMoreInteractions(uslugaService);
	}
	
	
}