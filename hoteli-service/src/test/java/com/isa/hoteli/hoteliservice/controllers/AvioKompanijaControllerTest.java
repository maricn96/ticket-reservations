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
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.BrojKarataDnevnoDTO;
import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;
import com.isa.hoteli.hoteliservice.avio.model.Destinacija;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.controller.HotelController;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(AvioKompanijaController.class)
public class AvioKompanijaControllerTest 
{
	private String route = "/aviocompany";
	
	private List<AvioKompanija> kompanije = new ArrayList<>();
	private AvioKompanija kompanija1 = new AvioKompanija(1l, "a", "a", "a");
	private AvioKompanija kompanija2 = new AvioKompanija(2l, "b", "b", "b");
	private Float srednjaOcena = 2f;
	private Float prihod = 100f;
	private Korisnik korisnik = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.ADMIN_AVIO_KOMPANIJE, 1l, true, "a", null, null, null, null, null, null, 0);
	private Korisnik korisnik2 = new Korisnik(2l, "a", "a", "a", "a", "a", "a", true, Rola.MASTER_ADMIN, 1l, true, "a", null, null, null, null, null, null, 0);
	
	private LocalDate datumOd = LocalDate.now();
	private LocalDate datumDo = LocalDate.now();
	
	private BrojKarataDnevnoDTO brojKarata = new BrojKarataDnevnoDTO(LocalDate.now(), 10);
	private List<BrojKarataDnevnoDTO> karte = new ArrayList<BrojKarataDnevnoDTO>();
	
	private MockHttpServletRequest request = new MockHttpServletRequest();
	
	private Let let1 = new Let(1l, 1l, LocalDateTime.now(), LocalDateTime.now(), 10, 10, 5, "a", 10, 10, 10, 10, null, null, null, null, null, null, null, null);
	private List<Let> letovi = new ArrayList<>();
	
	//DTO
	private List<AvioKompanijaDTO> kompanijeDto = new ArrayList<>();
	private AvioKompanijaDTO kompanija1Dto = new AvioKompanijaDTO(1l, "a", "a", "a");
	private AvioKompanijaDTO kompanija2Dto = new AvioKompanijaDTO(2l, "b", "b", "b");
	
	//treba mokovati svakakva cudesa iz kontrolera da ne baca failed to load app context
	
	@MockBean
	private AvioKompanijaService avioService;
	
	@MockBean
	private KorisnikService korisnikService;
	
	@MockBean
	private JwtTokenUtils jwt;
	
	@MockBean
	private KorisnikRepository korisnikRepo;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		letovi.add(let1);
		karte.add(brojKarata);
		kompanija1.setLetovi(letovi);
		kompanije.add(kompanija1);
		kompanije.add(kompanija2);
		
		kompanijeDto.add(kompanija1Dto);
		kompanijeDto.add(kompanija2Dto);
		request.addParameter("parameterName", "someValue");
		
	}
	
	
//	@Test
//	public void getAvioKompanijaSuccess() throws Exception
//	{
//		when(avioService.findById(1l)).thenReturn(kompanija1);
//		MvcResult result = this.mockMvc.
//				perform(get(this.route + "/getone/1")).
//				andExpect(status().isOk()).
//				andReturn();
//		AvioKompanija dto = objectMapper.readValue(result
//				.getResponse()
//				.getContentAsString(), AvioKompanija.class);
//		assertEquals(dto, kompanija1);
//		verify(avioService, times(1)).findById(1l);
//		verifyNoMoreInteractions(avioService);
//	}
	
//	@Test
//	public void getAvioKompanijaFailed() throws Exception
//	{
//		when(avioService.findById(1l)).thenReturn(null);
//		this.mockMvc.
//				perform(get(this.route + "/getone/1")).
//				andExpect(status().is4xxClientError()).
//				andReturn();
//		verify(avioService, times(1)).findById(1l);
//		verifyNoMoreInteractions(avioService);
//	}
	
	@Test
	public void getAllAvioKompanijeSuccess() throws Exception
	{
		when(avioService.findAll()).thenReturn(kompanijeDto);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getall")).
				andExpect(status().isOk()).
				andReturn();
		List<AvioKompanijaDTO> dtos = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<AvioKompanijaDTO>>() {});
		assertEquals(dtos.size(), 2);
		for(AvioKompanija avio : kompanije)
		{
			kompanijeDto.add(new AvioKompanijaDTO(avio));
		}
		assertThat(kompanijeDto.equals(dtos));
		verify(avioService, times(1)).findAll();
		verifyNoMoreInteractions(avioService);
	}
	
	@Test
	public void updateAvioKompanijaSuccess() throws Exception
	{
		when(korisnikService.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(korisnik);
		when(avioService.updateOne(Mockito.any(Long.class), Mockito.any(AvioKompanijaDTO.class))).thenReturn(kompanija1Dto);
		String s = objectMapper.writeValueAsString(kompanija1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().isCreated()).
				andReturn();
		assertThat(result.equals(kompanija1Dto));
		verify(korisnikService, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(avioService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(AvioKompanijaDTO.class));
		verifyNoMoreInteractions(korisnikService);
		verifyNoMoreInteractions(avioService);
		
	}
	
	@Test
	public void updateAvioKompanijaFailed() throws Exception
	{
		when(korisnikService.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(korisnik);
		when(avioService.updateOne(Mockito.any(Long.class), Mockito.any(AvioKompanijaDTO.class))).thenReturn(null);
		String s = objectMapper.writeValueAsString(kompanija1Dto);
		MvcResult result = this.mockMvc.
				perform(put(this.route + "/update/1").contentType(MediaType.APPLICATION_JSON).content(s)).
				andExpect(status().is4xxClientError()).
				andReturn();
		assertThat(result.equals(kompanija1Dto));
		verify(korisnikService, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(avioService, times(1)).updateOne(Mockito.any(Long.class), Mockito.any(AvioKompanijaDTO.class));
		verifyNoMoreInteractions(korisnikService);
		verifyNoMoreInteractions(avioService);
		
	}
	
	@Test
	public void deleteAvioKompanijaSuccess() throws Exception
	{
		when(avioService.deleteOne(1l)).thenReturn(true);
		MvcResult result = this.mockMvc.perform(delete(this.route + "/delete/1")).andExpect(status().isOk()).andReturn();
		assertThat(result.equals(true));
		verify(avioService, times(1)).deleteOne(1l);
		verifyNoMoreInteractions(avioService);
		
	}
	
	@Test
	public void getBrojProdatihKarataDnevnoSuccess() throws Exception
	{
		when(avioService.getBrojProdatihKarataDnevno(1l)).thenReturn(karte);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getsoldcardsbyday/1")).
				andExpect(status().isOk()).
				andReturn();
		List<BrojKarataDnevnoDTO> vrednost = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<BrojKarataDnevnoDTO>>() {});
		assertEquals(vrednost, karte);
		verify(avioService, times(1)).getBrojProdatihKarataDnevno(1l);
		verifyNoMoreInteractions(avioService);
	}
	
	@Test
	public void createAvioAdminSuccess() throws Exception
	{
		when(korisnikService.zaTokene(Mockito.any(HttpServletRequest.class))).thenReturn(korisnik2);
		when(avioService.createAvio(kompanija1)).thenReturn(kompanija1);
		when(korisnikService.getUserById(2l)).thenReturn(korisnik);
		when(korisnikRepo.save(korisnik)).thenReturn(korisnik);
		String s = objectMapper.writeValueAsString(kompanija1);
		MvcResult result = this.mockMvc.
				perform(post(this.route + "/2")
						.contentType(MediaType.APPLICATION_JSON)
						.content(s))
						.andExpect(status().isOk())
						.andReturn();
		AvioKompanija dto = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), AvioKompanija.class);
		assertEquals(dto, kompanija1);
		verify(korisnikService, times(1)).zaTokene(Mockito.any(HttpServletRequest.class));
		verify(avioService, times(1)).createAvio(kompanija1);
		verify(korisnikService, times(1)).getUserById(2l);
		verify(korisnikRepo, times(1)).save(korisnik);
		verifyNoMoreInteractions(avioService);
		verifyNoMoreInteractions(korisnikService);
		verifyNoMoreInteractions(korisnikRepo);
	}
	
	@Test
	public void getBrojProdatihKarataNedeljnoSuccess() throws Exception
	{
		when(avioService.getBrojProdatihKarataNedeljno(1l)).thenReturn(karte);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getsoldcardsbyweek/1")).
				andExpect(status().isOk()).
				andReturn();
		List<BrojKarataDnevnoDTO> vrednost = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<BrojKarataDnevnoDTO>>() {});
		assertEquals(vrednost, karte);
		verify(avioService, times(1)).getBrojProdatihKarataNedeljno(1l);
		verifyNoMoreInteractions(avioService);
	}
	
	@Test
	public void getBrojProdatihKarataMesecnoSuccess() throws Exception
	{
		when(avioService.getBrojProdatihKarataMesecno(1l)).thenReturn(karte);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getsoldcardsbymonth/1")).
				andExpect(status().isOk()).
				andReturn();
		List<BrojKarataDnevnoDTO> vrednost = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), new TypeReference<List<BrojKarataDnevnoDTO>>() {});
		assertEquals(vrednost, karte);
		verify(avioService, times(1)).getBrojProdatihKarataMesecno(1l);
		verifyNoMoreInteractions(avioService);
	}
	
	
	
	
	
	//nije mi jasno sto ne radi ako sam ubacio let koji ima srednju ocenu
	@Test
	public void getSrednjaOcenaAviokompanijeSuccess() throws Exception
	{
		when(avioService.getSrednjaOcenaAvioKompanije(1l)).thenReturn(srednjaOcena);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getavgrating/1")).
				andExpect(status().isOk()).
				andReturn();
		Float vrednost = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), Float.class);
		assertNotNull(vrednost);
		verify(avioService, times(1)).getSrednjaOcenaAvioKompanije(1l);
		verifyNoMoreInteractions(avioService);
	}
	
	@Test
	public void getPrihodZaOdredjeniPeriodSuccess() throws Exception
	{
		
		when(avioService.getPrihodZaOdredjeniPeriod(1l, datumOd, datumDo)).thenReturn(prihod);
		System.out.println(prihod);
		MvcResult result = this.mockMvc.
				perform(get(this.route + "/getincomebydate/1/" + datumOd + "/" + datumDo)).
				andExpect(status().isOk()).
				andReturn();
		Float vrednost = objectMapper.readValue(result
				.getResponse()
				.getContentAsString(), Float.class);
		assertEquals(vrednost, prihod);
		verify(avioService, times(1)).getPrihodZaOdredjeniPeriod(1l, datumOd, datumDo);
		verifyNoMoreInteractions(avioService);
	}


	
}
