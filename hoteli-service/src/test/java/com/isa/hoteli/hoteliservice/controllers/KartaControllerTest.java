package com.isa.hoteli.hoteliservice.controllers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
import com.isa.hoteli.hoteliservice.avio.controller.KartaController;
import com.isa.hoteli.hoteliservice.avio.dto.KartaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.avio.dto.SlanjePozivniceZaRezervacijuDTO;
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.BrojKarataDnevnoDTO;
import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;
import com.isa.hoteli.hoteliservice.avio.service.KartaService;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.avio.service.MailService;
import com.isa.hoteli.hoteliservice.controller.HotelController;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(KartaController.class)
public class KartaControllerTest 
{
	private String route = "/ticket";
	
	private LocalDateTime vremeRez = LocalDateTime.now();
	
	private List<Karta> karte = new ArrayList<>();
	private Korisnik korisnik = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_AVIO_KOMPANIJE, 1l, true, "a", null, null, null, null, null, null, 0);

	private Karta karta1 = new Karta(1l, 10, 5, false, 0, "a", null, null, vremeRez, korisnik, null, 0);
	private Karta karta2 = new Karta(2l, 20, 1, false, 0, "b", null, null, vremeRez, korisnik, null, 0);

	private MockHttpServletRequest request = new MockHttpServletRequest();
	
	private Let let1 = new Let(1l, 1l, LocalDateTime.now(), LocalDateTime.now(), 10, 10, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null, null);
	private List<Let> letovi = new ArrayList<>();
	
	private Boolean brzaRez = true;
	private Integer popust = 5;

	
	//DTO
	private Korisnik korisnikDto = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_AVIO_KOMPANIJE, 1l, true, "a", null, null, null, null, null, null, 0);
	private List<KartaDTO> karteDto = new ArrayList<>();
	private KartaDTO karta1Dto = new KartaDTO(1l, 10, 5, false, 0, "a", null, null, vremeRez, null, null, 0);
	private KartaDTO karta2Dto = new KartaDTO(karta2);
	
	
	//ZA REZERVISANJE
	private String rezervisane = "REZERVISANE";
	private KorisnikDTO korisnikUser = new KorisnikDTO(3l, "r", "r", "r", "r", "r", "r", true, Rola.KORISNIK, 1l, false, "a", null, null, 0);
	private KorisnikDTO korisnikFriend = new KorisnikDTO(4l, "rr", "rr", "rr", "rr", "rr", "rr", true, Rola.KORISNIK, 1l, false, "a", null, null, 0);
	private SlanjePozivniceZaRezervacijuDTO pozivnica = new SlanjePozivniceZaRezervacijuDTO();
	private String pasos1 = "aaa";
	private List<String> brojeviPasosa = new ArrayList<>();
	private List<KorisnikDTO> listaPrijatelja = new ArrayList<>();
	


	@MockBean
	private KartaService kartaService;
	
	@MockBean
	private KorisnikService korisnikService;
	
	@MockBean
	private MailService mailService;
	
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
		karte.add(karta1);
		karte.add(karta2);
		
		karteDto.add(karta1Dto);
		karteDto.add(karta2Dto);
		
		listaPrijatelja.add(korisnikFriend);
		brojeviPasosa.add(pasos1);
		pozivnica.setBrojeviPasosa(brojeviPasosa);
		pozivnica.setListaKarata(karteDto);
		pozivnica.setListaPrijatelja(listaPrijatelja);
		
		request.addParameter("parameterName", "someValue");
		
	}
	
	
	@Test
	public void getKartaSuccess() throws Exception
	{
		when(kartaService.findById(1l)).thenReturn(karta1Dto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().isOk()).
				andReturn();
		KartaDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), KartaDTO.class);
		assertEquals(dto, karta1Dto);
		verify(kartaService, times(1)).findById(1l);
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void getKartaFailed() throws Exception
	{
		when(kartaService.findById(1l)).thenReturn(null);
		this.mockMvc.
				perform(get(this.route + "/getone/1")).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(kartaService, times(1)).findById(1l);
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void getAllKarteSuccess() throws Exception
	{
		when(kartaService.findAll()).thenReturn(karteDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getall")).
				andExpect(status().isOk()).
				andReturn();
		List<KartaDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<KartaDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(Karta dest : karte)
		{
			karteDto.add(new KartaDTO(dest));
		}
		assertThat(karteDto.equals(dtos));
		verify(kartaService, times(1)).findAll();
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void getAllNerezervisaneKarteSuccess() throws Exception
	{
		when(kartaService.getAllNerezervisaneKarte(1l)).thenReturn(karteDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getfree/1")).
				andExpect(status().isOk()).
				andReturn();
		List<KartaDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<KartaDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(Karta kartaa : karte)
		{
			karteDto.add(new KartaDTO(kartaa));
		}
		assertThat(karteDto.equals(dtos));
		verify(kartaService, times(1)).getAllNerezervisaneKarte(1l);
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void addKartaSuccess() throws Exception
	{
		when(kartaService.saveOne(Mockito.any(KartaDTO.class))).thenReturn(karta1Dto);
		String s = objectMapper.writeValueAsString(karta1Dto);
		MvcResult result = this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		KartaDTO dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), KartaDTO.class);
		assertEquals(dto, karta1Dto);
		verify(kartaService, times(1)).saveOne(Mockito.any(KartaDTO.class));
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void addKartaFailed() throws Exception
	{
		String s = objectMapper.writeValueAsString(karta1Dto);
		this.mockMvc.
				perform(post(this.route + "/add/").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		verify(kartaService, times(1)).saveOne(Mockito.any(KartaDTO.class));
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void updateKartaSuccess() throws Exception
	{
		when(kartaService.updateOne(Mockito.any(Long.class), Mockito.any(KartaDTO.class))).thenReturn(karta1Dto);
		String s = objectMapper.writeValueAsString(karta1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		assertThat(result.equals(karta1Dto));
		verify(kartaService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(KartaDTO.class));
		verifyNoMoreInteractions(kartaService);
		
	}
	
	@Test
	public void updateKartaFailed() throws Exception
	{
		when(kartaService.updateOne(Mockito.any(Long.class), Mockito.any(KartaDTO.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(karta1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		assertThat(result.equals(karta1Dto));
		verify(kartaService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(KartaDTO.class));
		verifyNoMoreInteractions(kartaService);
		
	}
	
	@Test
	public void deleteKartaSuccess() throws Exception
	{
		when(kartaService.deleteOne(1l)).thenReturn(true);
		MvcResult result = this.mockMvc.perform(delete(this.route + "/delete/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(true));
		verify(kartaService, times(1)).deleteOne(1l);
		verifyNoMoreInteractions(kartaService);
	}
	
	

	
	@Test
	public void getAllBrzaRezervacijaKarteSuccess() throws Exception
	{
		when(kartaService.getAllBrzaRezervacijaKarte()).thenReturn(karteDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getexpress")).
				andExpect(status().isOk()).
				andReturn();
		List<KartaDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<KartaDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(Karta kartaa : karte)
		{
			karteDto.add(new KartaDTO(kartaa));
		}
		assertThat(karteDto.equals(dtos));
		verify(kartaService, times(1)).getAllBrzaRezervacijaKarte();
		verifyNoMoreInteractions(kartaService);
	}
	
	
	@Test
	public void brzaRezervacijaJedneKarteSuccess() throws Exception
	{
		when(kartaService.brzaRezervacijaJedneKarte(2l, 2l)).thenReturn(brzaRez);

		String s = objectMapper.writeValueAsString(brzaRez);
		MvcResult result = this.mockMvc.
				perform(post(this.route + "/expressreservation/2/2")).
				andExpect(status().isCreated()).
				andReturn();
		Boolean dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), Boolean.class);
		assertEquals(dto, brzaRez);
		verify(kartaService, times(1)).brzaRezervacijaJedneKarte(2l, 2l);
		verifyNoMoreInteractions(kartaService);
	}
	
	@Test
	public void postaviKartuNaBrzuRezervacijuSuccess() throws Exception
	{
		when(kartaService.postaviKartuNaBrzuRezervaciju(Mockito.any(Long.class), Mockito.any(Integer.class))).thenReturn(brzaRez);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/settoexpress/1/" + this.popust)).
				andExpect(status().isCreated()).
				andReturn();
		assertThat(result.equals(true));
		verify(kartaService, times(1)).postaviKartuNaBrzuRezervaciju(Mockito.any(Long.class), Mockito.any(Integer.class));
		verifyNoMoreInteractions(kartaService);
		
	}
	
//	@Test
//	public void rezervisiViseKarataSuccess() throws Exception
//	{
//		when(kartaService.rezervisiViseKarata(3l, this.pozivnica)).thenReturn(this.rezervisane);
//		String s = objectMapper.writeValueAsString(pozivnica);
//		MvcResult result = this.mockMvc.
//				perform(post(this.route + "/reservemore/3").contentType(MediaType.APPLICATION_JSON).content(s)).
//				andExpect(status().isCreated()).
//				andReturn();
//		String dto = objectMapper.readValue(result
//				.getResponse()
//				.getContentAsString(), String.class);
//		assertEquals(dto, rezervisane);
//		verify(kartaService, times(1)).rezervisiViseKarata(3l, this.pozivnica);
//		verifyNoMoreInteractions(kartaService);
//	}
	
//	@Test
//	public void rezervisiViseKarataFailed() throws Exception
//	{
//		when(kartaService.rezervisiViseKarata(3l, this.pozivnica)).thenReturn(null);
//		String s = objectMapper.writeValueAsString(pozivnica);
//		MvcResult result = this.mockMvc.
//				perform(post(this.route + "/reservemore/3").contentType(MediaType.APPLICATION_JSON).content(s)).
//				andExpect(status().is4xxClientError()).
//				andReturn();
//		String dto = objectMapper.readValue(result
//				.getResponse()
//				.getContentAsString(), String.class);
//		assertThat(result.equals(pozivnica));
//		verify(kartaService, times(1)).rezervisiViseKarata(3l, this.pozivnica);
//		verifyNoMoreInteractions(kartaService);
//	}
	
}