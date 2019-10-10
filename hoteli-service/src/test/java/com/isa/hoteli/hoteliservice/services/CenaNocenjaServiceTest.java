package com.isa.hoteli.hoteliservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.repository.CenaNocenjaRepository;
import com.isa.hoteli.hoteliservice.service.CenaNocenjaService;

@RunWith(SpringRunner.class)
public class CenaNocenjaServiceTest {
	
	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis()); 
	private Date datum = new Date(System.currentTimeMillis());
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private List<CenaNocenja> cene = new ArrayList<>();
	private CenaNocenja cena1 = new CenaNocenja(1l, 20, datumOd, datumDo, soba1);
	private CenaNocenja cena2 = new CenaNocenja(1l, 40, datumOd, datumDo, soba1);
	
	@Mock
	private CenaNocenjaRepository cnr;
	
	@InjectMocks
	private CenaNocenjaService cns;
	
	@Before
	public void setUp() {
		cene.add(cena1);
		cene.add(cena2);
	}
	
	@Test
	public void getAllSuccess() {
		when(cnr.findAll()).thenReturn(cene);
		List<CenaNocenja> prices = cns.getPrices();
		assertEquals(cene, prices);
		verify(cnr, times(1)).findAll();
		verifyNoMoreInteractions(cnr);
	}
	
	@Test
	public void getAllFromHotelRoomSuccess() {
		when(cnr.getAllFromHotelRoom(1l)).thenReturn(cene);
		List<CenaNocenja> prices = cns.getPricesFromHotelRoom(1l);
		assertEquals(cene, prices);
		verify(cnr, times(1)).getAllFromHotelRoom(1l);
		verifyNoMoreInteractions(cnr);
	}
	
	@Test
	public void getValidPriceFromHotelRoomFailed() {
		when(cnr.getAllFromHotelRoom(1l)).thenReturn(cene);
		when(cnr.getValidFromHotelRoom(1l, datum)).thenReturn(cena1);
		float f = cns.getValidPriceFromHotelRoom(1l);
		Assert.assertEquals(-1f, f, 0.0f);
		verify(cnr, times(1)).getAllFromHotelRoom(1l);
	}
	
	@Test
	public void getHotelByIdSuccess() {
		when(cnr.getOne(1l)).thenReturn(cena1);
		CenaNocenja cena = cns.getPriceById(1l);
		assertEquals(cena1, cena);
		verify(cnr, times(1)).getOne(1l);
		verifyNoMoreInteractions(cnr);
	}
	
	@Test
	public void createCenaSuccess() {
		when(cnr.save(cena1)).thenReturn(cena1);
		when(cnr.findKonfliktCeneNocenja(1l, datumOd, datumDo)).thenReturn(new ArrayList<>());
		CenaNocenjaDTO cena = cns.createPrice(cena1);
		assertThat(cena.equals(new CenaNocenjaDTO(cena1)));
		verify(cnr, times(1)).save(cena1);
		verify(cnr, times(1)).findKonfliktCeneNocenja(1l, datumOd, datumDo);
		verifyNoMoreInteractions(cnr);
	}
	
	@Test
	public void createCenaFailed() {
		when(cnr.findKonfliktCeneNocenja(1l, datumOd, datumDo)).thenReturn(cene);
		CenaNocenjaDTO cena = cns.createPrice(cena1);
		assertNull(cena);
		verify(cnr, times(1)).findKonfliktCeneNocenja(1l, datumOd, datumDo);
		verifyNoMoreInteractions(cnr);
	}
	
	@Test
	public void updateCenaSuccess() {
		when(cnr.getOne(1l)).thenReturn(cena1);
		when(cnr.save(cena1)).thenReturn(cena1);
		CenaNocenjaDTO cena = cns.updatePrice(cena1, 1l);
		assertThat(cena.equals(new CenaNocenjaDTO(cena1)));
		verify(cnr, times(1)).getOne(1l);
		verify(cnr, times(1)).save(cena1);
		verifyNoMoreInteractions(cnr);
	}
	
	@Test
	public void updateCenaFailed() {
		when(cnr.getOne(1l)).thenReturn(null);
		cns.updatePrice(cena1, 1l);
		verify(cnr, times(1)).getOne(1l);
		verifyNoMoreInteractions(cnr);
	}
	
	@Test
	public void deleteCenaSuccess() {
		doNothing().when(cnr).deleteById(1l);
		String s = cns.deletePrice(1l);
		assertEquals("Uspesno obrisana cena sa id: 1", s);
		verify(cnr, times(1)).deleteById(1l);
		verifyNoMoreInteractions(cnr);
	}
	
}
