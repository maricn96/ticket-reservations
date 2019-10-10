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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.dto.HotelskaSobaDTO;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.model.HotelskaSoba;
import com.isa.hoteli.hoteliservice.model.Rezervacije;
import com.isa.hoteli.hoteliservice.repository.CenaNocenjaRepository;
import com.isa.hoteli.hoteliservice.repository.HotelskaSobaRepository;
import com.isa.hoteli.hoteliservice.repository.RezervacijeRepository;
import com.isa.hoteli.hoteliservice.service.HotelskaSobaService;

@RunWith(SpringRunner.class)
public class HotelskaSobaServiceTest {

	private Date datumOd = new Date(System.currentTimeMillis());
	private Date datumDo = new Date(System.currentTimeMillis());
	private Hotel hotel1 = new Hotel(1l, "a", "a", "a", "a", 1f, 1f);
	private List<HotelskaSoba> sobe = new ArrayList<>();
	private List<Rezervacije> rezervacije = new ArrayList<>();
	private HotelskaSoba soba1 = new HotelskaSoba(1l, 1, 1, 1, 200, hotel1, null);
	private HotelskaSoba soba2 = new HotelskaSoba(2l, 2, 2, 2, 400, hotel1, null);
	private Korisnik k = new Korisnik(1l, "a", "a", "a", "a", "a", "a", true, Rola.MASTER_ADMIN, null, true, "a", null, null, null, null, null, null, 0);
	private Rezervacije r = new Rezervacije(1l, datumOd, datumDo, 200, 2, soba1, k, hotel1);
	private CenaNocenja cena1 = new CenaNocenja(1l, 20, datumOd, datumDo, soba1);


	
	@Mock
	private HotelskaSobaRepository hsr;
	
	@Mock 
	private CenaNocenjaRepository cnr;
	
	@Mock
	private RezervacijeRepository rr;
	
	@InjectMocks
	private HotelskaSobaService hss;
	
	@Before
	public void setUp() {
		sobe.add(soba1);
		sobe.add(soba2);
		rezervacije.add(r);
	}
	
	@Test
	public void getAllSuccess() {
		when(hsr.findAll()).thenReturn(sobe);
		List<HotelskaSoba> rooms = hss.getRooms();
		assertEquals(sobe, rooms);
		verify(hsr, times(1)).findAll();
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void getAllFromHotelSuccess() {
		when(hsr.getAllFromHotel(1l)).thenReturn(sobe);
		List<HotelskaSoba> rooms = hss.getRoomsFromHotel(1l);
		assertEquals(sobe, rooms);
		verify(hsr, times(1)).getAllFromHotel(1l);
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void getAllFreeFromHotelSuccess() {
		when(hsr.getAllFromHotel(1l)).thenReturn(sobe);
		when(rr.findKonfliktRezervacije(1l, datumOd, datumDo)).thenReturn(new ArrayList<>());
		when(rr.findKonfliktRezervacije(2l, datumOd, datumDo)).thenReturn(new ArrayList<>());;
		List<HotelskaSoba> rooms = hss.getFreeRoomsFromHotel(1l, datumOd, datumDo);
		assertEquals(sobe, rooms);
		verify(hsr, times(1)).getAllFromHotel(1l);
		verify(rr, times(1)).findKonfliktRezervacije(1l, datumOd, datumDo);
		verify(rr, times(1)).findKonfliktRezervacije(2l, datumOd, datumDo);
		verifyNoMoreInteractions(rr);
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void getRoomByIdSuccess() {
		when(hsr.getOne(1l)).thenReturn(soba1);
		HotelskaSoba room = hss.getRoomById(1l);
		assertEquals(soba1, room);
		verify(hsr, times(1)).getOne(1l);
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void createRoomSuccess() {
		when(hsr.save(soba1)).thenReturn(soba1);
		when(hsr.getRoomWithNumber(1l, soba1.getBrojSobe())).thenReturn(null);
		HotelskaSobaDTO room = hss.createRoom(soba1);
		assertThat(room.equals(new HotelskaSobaDTO(soba1)));
		verify(hsr, times(1)).save(soba1);
		verify(hsr, times(1)).getRoomWithNumber(1l, soba1.getBrojSobe());
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void createRoomFailed() {
		when(hsr.getRoomWithNumber(1l, soba1.getBrojSobe())).thenReturn(soba1);
		HotelskaSobaDTO room = hss.createRoom(soba1);
		assertNull(room);
		verify(hsr, times(1)).getRoomWithNumber(1l, soba1.getBrojSobe());
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void deleteRoomSuccess() {
		doNothing().when(hsr).deleteById(1l);
		String s = hss.deleteRoom(1l);
		assertEquals("Uspesno obrisana soba sa id: 1", s);
		verify(hsr, times(1)).deleteById(1l);
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void updateRoomPriceSuccess() {
		when(hsr.getOne(1l)).thenReturn(soba1);
		when(hsr.save(soba1)).thenReturn(soba1);
		HotelskaSobaDTO room = hss.updateRoomPrice(soba1, 1l);
		assertThat(room.equals(new HotelskaSobaDTO(soba1)));
		verify(hsr, times(1)).getOne(1l);
		verify(hsr, times(1)).save(soba1);
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void updateRoomPriceFailed() {
		when(hsr.getOne(1l)).thenReturn(null);
		HotelskaSobaDTO room = hss.updateRoomPrice(soba1, 1l);
		assertNull(room);
		verify(hsr, times(1)).getOne(1l);
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void getAllReservedFromHotelSuccess() {
		when(hsr.getAllFromHotel(1l)).thenReturn(sobe);
		when(rr.findKonfliktRezervacije(1l, datumOd, datumDo)).thenReturn(rezervacije);
		when(rr.findKonfliktRezervacije(2l, datumOd, datumDo)).thenReturn(rezervacije);
		List<HotelskaSoba> rooms = hss.getAllReservedRoomsFromHotel(1l, datumOd, datumDo);
		assertEquals(sobe, rooms);
		verify(hsr, times(1)).getAllFromHotel(1l);
		verify(rr, times(1)).findKonfliktRezervacije(1l, datumOd, datumDo);
		verify(rr, times(1)).findKonfliktRezervacije(2l, datumOd, datumDo);
		verifyNoMoreInteractions(rr);
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void getAllFreeRoomsFromHotelWithDiscountSuccess() {
		when(hsr.getAllFromHotel(1l)).thenReturn(sobe);
		when(rr.findKonfliktRezervacije(1l, datumOd, datumDo)).thenReturn(new ArrayList<>());
		when(rr.findKonfliktRezervacije(2l, datumOd, datumDo)).thenReturn(new ArrayList<>());
		when(cnr.getValidFromHotelRoom(1l, datumOd)).thenReturn(cena1);
		when(cnr.getValidFromHotelRoom(2l, datumOd)).thenReturn(cena1);
		List<HotelskaSoba> rooms = hss.getAllFreeRoomsFromHotelWithDiscount(1l, datumOd, datumDo);
		assertEquals(sobe, rooms);
		verify(hsr, times(1)).getAllFromHotel(1l);
		verify(rr, times(1)).findKonfliktRezervacije(1l, datumOd, datumDo);
		verify(rr, times(1)).findKonfliktRezervacije(2l, datumOd, datumDo);
		verify(cnr, times(2)).getValidFromHotelRoom(1l, datumOd);
		verify(cnr, times(2)).getValidFromHotelRoom(2l, datumOd);
		verifyNoMoreInteractions(cnr);
		verifyNoMoreInteractions(rr);
		verifyNoMoreInteractions(hsr);
	}
	
	@Test
	public void getAllFreeRoomsFromHotelWithDiscountFailed() {
		when(hsr.getAllFromHotel(1l)).thenReturn(sobe);
		when(rr.findKonfliktRezervacije(1l, datumOd, datumDo)).thenReturn(rezervacije);
		when(rr.findKonfliktRezervacije(2l, datumOd, datumDo)).thenReturn(rezervacije);
		List<HotelskaSoba> rooms = hss.getAllFreeRoomsFromHotelWithDiscount(1l, datumOd, datumDo);
		assertEquals(new ArrayList<>(), rooms);
		verify(hsr, times(1)).getAllFromHotel(1l);
		verify(rr, times(1)).findKonfliktRezervacije(1l, datumOd, datumDo);
		verify(rr, times(1)).findKonfliktRezervacije(2l, datumOd, datumDo);
		verifyNoMoreInteractions(rr);
		verifyNoMoreInteractions(hsr);
	}
	
}
