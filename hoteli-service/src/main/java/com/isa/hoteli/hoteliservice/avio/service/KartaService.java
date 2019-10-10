package com.isa.hoteli.hoteliservice.avio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.avio.converter.KartaConverter;
import com.isa.hoteli.hoteliservice.avio.converter.KorisnikConverter;
import com.isa.hoteli.hoteliservice.avio.dto.KartaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.avio.dto.SlanjePozivniceZaRezervacijuDTO;
import com.isa.hoteli.hoteliservice.avio.exception.ConcurrentException;
import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.repository.KartaRepository;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.repository.LetRepository;
import com.isa.hoteli.hoteliservice.service.RezervacijeService;

@Service
public class KartaService
{
	@Autowired
	private KartaRepository kartaRepo;
	
	@Autowired
	private KartaConverter kartaConv;
	
	@Autowired
	private KorisnikRepository korisnikRepo;
	
	@Autowired
	private KorisnikConverter korisnikConv;
	
	@Autowired
	private LetRepository letRepo;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private RezervacijeService rezService;
	
	
	@Transactional(readOnly = true)
	public KartaDTO findById(Long id)
	{
		Optional<Karta> karta = kartaRepo.findById(id);
		
		if(karta.isPresent())
			return kartaConv.convertToDTO(karta.get());
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public Karta traziById(Long id)
	{
		Karta karta = kartaRepo.getOne(id);
		
		if(karta != null)
			return karta;
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public List<KartaDTO> findAll()
	{
		Optional<List<Karta>> list = Optional.of(kartaRepo.findAll());
		
		List<KartaDTO> listDto = new ArrayList<KartaDTO>();
		
		if(list.isPresent())
		{
			for(Karta karta : list.get())
				listDto.add(kartaConv.convertToDTO(karta));
			
			return listDto;
		}
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public List<Karta> traziSve()
	{
		return kartaRepo.findAll();
	}
	
	@Transactional(readOnly = false)
	public KartaDTO saveOne(KartaDTO dto)
	{
		Optional<Karta> karta = kartaRepo.findById(dto.getIdKarte());
						
		if(karta.isPresent())
			return null;
		else
		{			
			dto.setOcena(0);
			kartaRepo.save(kartaConv.convertFromDTO(dto));
			return dto;
		}
	}
	
	@Transactional(readOnly = false)
	public Karta saveOne(Karta dto)
	{
		Karta karta = kartaRepo.getOne(dto.getIdKarte());
						
		if(karta != null)
			return null;
		else
		{			
			dto.setOcena(0);
			kartaRepo.save(dto);
			return dto;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public KartaDTO updateOne(Long id, KartaDTO dto)
	{
		Optional<Karta> karta = kartaRepo.findById(id);
		
		if(karta.isPresent())
		{
			karta.get().setIdKarte(kartaConv.convertFromDTO(dto).getIdKarte());
			karta.get().setCena(kartaConv.convertFromDTO(dto).getCena());
			karta.get().setOcena(kartaConv.convertFromDTO(dto).getOcena());
			karta.get().setBrzaRezervacija(kartaConv.convertFromDTO(dto).isBrzaRezervacija());
			karta.get().setPopust(kartaConv.convertFromDTO(dto).getPopust());
			karta.get().setBrojPasosa(kartaConv.convertFromDTO(dto).getBrojPasosa());
			karta.get().setIdHotelRezervacije(kartaConv.convertFromDTO(dto).getIdHotelRezervacije());
			karta.get().setKorisnikKojiSaljePozivnicu(kartaConv.convertFromDTO(dto).getKorisnikKojiSaljePozivnicu());
			karta.get().setLet(kartaConv.convertFromDTO(dto).getLet());
			karta.get().setVremeRezervisanja(kartaConv.convertFromDTO(dto).getVremeRezervisanja());
			karta.get().setKorisnik(kartaConv.convertFromDTO(dto).getKorisnik());
			
			kartaRepo.save(karta.get());
			
			return kartaConv.convertToDTO(karta.get());
		}
		else
			return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Karta updateOne(Long id, Karta dto)
	{
		Karta karta = kartaRepo.getOne(id);
		
		if(karta != null)
		{
			karta.setIdKarte(dto.getIdKarte());
			karta.setCena(dto.getCena());
			karta.setOcena(dto.getOcena());
			karta.setBrzaRezervacija(dto.isBrzaRezervacija());
			karta.setPopust(dto.getPopust());
			karta.setLet(dto.getLet());
			karta.setVremeRezervisanja(dto.getVremeRezervisanja());
			karta.setKorisnik(dto.getKorisnik());
			
			kartaRepo.save(karta);
			
			return karta;
		}
		else
			return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean deleteOne(Long id)
	{
		Karta karta = kartaRepo.getOne(id);
		
		if(karta != null)
		{
			kartaRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}



	////////////////////////
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Boolean brzaRezervacijaJedneKarte(Long idKorisnika, Long idKarte)
	{
		System.out.println("USAO U SERVIS");
		Optional<Karta> karta = kartaRepo.findById(idKarte);
		System.out.println(karta.get().getCena());
		
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		
		System.out.println(korisnik.get().getEmail());
		
		LocalDateTime date = LocalDateTime.now();
		
		if(karta.isPresent() && korisnik.isPresent())
		{
			if(karta.get().getKorisnik().getId() == 1 && karta.get().getVremeRezervisanja().getYear() < 2002) //ako nije rezervisana
			{
				Long id = 9999L;
				//rezervisacemo je
				
				karta.get().setVremeRezervisanja(date);
				karta.get().setKorisnik(korisnik.get());
				karta.get().setBrojPasosa(korisnik.get().getBrojPasosa());
				karta.get().getLet().setBrojOsoba(karta.get().getLet().getBrojOsoba()+1);
				karta.get().getLet().setUkupanPrihod(karta.get().getLet().getUkupanPrihod() + karta.get().getCena() - (karta.get().getCena() * karta.get().getPopust()/100));
//				try {
//					mailService.sendNotificaitionAsync(korisnik.get(), null, "RESERVATION");
//				} catch (MailException | InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				kartaRepo.save(karta.get());
			}
			else
				return false;
		}
		else
			return false;
		
		return true;
	}
	
	public Boolean brzaRezervacijaJedneKarteTwin(Long idKorisnika, Long idKarte)
	{
		System.out.println("USAO U SERVIS");
		Karta karta = kartaRepo.getOne(idKarte);
		System.out.println(karta.getCena());
		
		Korisnik korisnik = korisnikRepo.getOne(idKorisnika);
		
		System.out.println(korisnik.getEmail());
		
		LocalDateTime date = LocalDateTime.now();
		
		if(karta != null && korisnik != null)
		{
				Long id = 9999L;
				//rezervisacemo je
				
				karta.setVremeRezervisanja(date);
				karta.setKorisnik(korisnik);
				karta.setBrojPasosa(korisnik.getBrojPasosa());
				karta.getLet().setBrojOsoba(karta.getLet().getBrojOsoba()+1);
				karta.getLet().setUkupanPrihod(karta.getLet().getUkupanPrihod() + karta.getCena() - (karta.getCena() * karta.getPopust()/100));
//				try {
//					mailService.sendNotificaitionAsync(korisnik, null, "RESERVATION");
//				} catch (MailException | InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				try {
					kartaRepo.save(karta);
				}
				catch (HibernateOptimisticLockingFailureException e) {
					System.out.println("USAO U EKSEPSN");
					e.printStackTrace();
				}
		}
		else
			return false;
		
		return true;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean postaviKartuNaBrzuRezervaciju(Long idKarte, Integer popust)
	{
		Optional<Karta> karta = kartaRepo.findById(idKarte);
		
		karta.get().setBrzaRezervacija(true);
		karta.get().setPopust(popust);
		
		kartaRepo.save(karta.get());
		
		return true;
	}

	@Transactional(readOnly = true)
	public List<KartaDTO> getAllBrzaRezervacijaKarte() 
	{
		List<Karta> karte = kartaRepo.findAll();
		
		List<KartaDTO> retList = new ArrayList<KartaDTO>();
		
		for(Karta karta : karte)
		{
			if(karta.isBrzaRezervacija() && karta.getKorisnik().getId() == 1)
			{
				retList.add(kartaConv.convertToDTO(karta));
			}
		}
		
		return retList;
	}

	/*
	 * Rezervacija vise karata 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String rezervisiViseKarata(Long idKorisnika, SlanjePozivniceZaRezervacijuDTO pozivnica)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		
		List<KorisnikDTO> prijatelji = pozivnica.getListaPrijatelja();
		List<KartaDTO> karte = pozivnica.getListaKarata();
		List<String> brojeviPasosa = pozivnica.getBrojeviPasosa();
		
		//ako postoji neka osoba da nije u prijateljima
		for(KorisnikDTO dto : prijatelji)
		{
			//nez zasto ovakav uslov al radi tako da ne diraj nista :D
			if(korisnik.get().getPrijateljiKorisnika().contains(korisnikConv.convertFromDTO(dto)))
				return "NOT_FRIEND_ERR";
		}
			
		
		//za te prijatelje koje dobijemo ovde (npr rezervisali smo 5 karata a pozvali 2 prijatelja, preostale 3 ce ici na nas)
		//jedan prijatelj = jedna rezervacija

		
		//izbacujemo duplikate iz prijatelja (nisam uspeo u reactu)
		Set<KorisnikDTO> dist = new LinkedHashSet<KorisnikDTO>(); 
		dist.addAll(prijatelji); 
		prijatelji.clear(); 
		prijatelji.addAll(dist);
		
		LocalDateTime date = LocalDateTime.now();
		
		for(KartaDTO karta : karte)
		{
			if(prijatelji.isEmpty())
				break;
				
				for(KorisnikDTO prijatelj : prijatelji)
				{
						if(karta.getKorisnik().getId() == 1)
						{
							karta.setKorisnik(prijatelj);
							karta.setBrojPasosa(prijatelj.getBrojPasosa());
							karta.setKorisnikKojiSaljePozivnicu(korisnikConv.convertToDTO(korisnik.get()));
							//necemo ovde rezervisati vreme, to cemo tek kad prijatelj prihvati rezervaciju
							//karta.setVremeRezervisanja(date); 
							
							try {
								kartaRepo.save(kartaConv.convertFromDTO(karta));
							}
							catch (HibernateOptimisticLockingFailureException | StaleObjectStateException e) {
								e.printStackTrace();
							}
							
							prijatelji.remove(prijatelj);
							if(prijatelji.isEmpty())
							{
								break;
							}
						}
				}
		}
		
		
		//sad pravimo novu listu karata koje su preostale, dakle koje nisu otisle kao pozivnice
		
		List<KartaDTO> novaListaKarata = new ArrayList<KartaDTO>();
		
		for(KartaDTO karta : karte)
		{
			if(karta.getKorisnik().getId() == 1)
			{
				novaListaKarata.add(karta);
			}
		}
		
		boolean flag = false;
		//Jedna ide na nas, ostale idu na prosledjene brojeve pasosa
			for(KartaDTO dto : novaListaKarata)
			{
				Karta card = kartaConv.convertFromDTO(dto);
				if(!flag)
				{
					//postavljamo broj zauzetih mesta za konkretan let
					Optional<Let> let = letRepo.findById(dto.getLet().getIdLeta());
					let.get().setBrojOsoba(let.get().getBrojOsoba() + karte.size());
					
					
					//prvi broj pasosa ide na korisnika
					card.setBrojPasosa(korisnik.get().getBrojPasosa());
					card.setKorisnik(korisnik.get()); 
					card.setVremeRezervisanja(date);
					
					
					card.setPopust(korisnik.get().getBodovi() * 3);
						
						try {
							kartaRepo.save(card);
							letRepo.save(let.get());
						}
						catch (HibernateOptimisticLockingFailureException | StaleObjectStateException e) {
							e.printStackTrace();
						}
					
					flag = true;
				}	
				else
				{
					Optional<Let> let = letRepo.findById(dto.getLet().getIdLeta());
					int distanca = let.get().getDuzinaPutovanja();
					float popust = 0;
					if(distanca > 0 && distanca <= 200)
						popust = 5;
					else if(distanca > 200 && distanca <=500)
						popust = 15;
					else if(distanca > 500 && distanca <= 1000)
						popust = 25;
					else if(distanca > 1000 && distanca < 2000)
						popust = 40;
					else
						popust = 50;
					
					card.setPopust(popust);
					card.setKorisnik(korisnik.get()); 
					card.setVremeRezervisanja(date);
					if(korisnik.get().getBodovi() < 20) {
						korisnik.get().setBodovi(korisnik.get().getBodovi() + 2);
					}
					
					for(String pasos : brojeviPasosa)
					{
						card.setBrojPasosa(pasos);
						brojeviPasosa.remove(pasos);
						break;
					}
					
					try {
						kartaRepo.save(card);
						korisnikRepo.save(korisnik.get());
					}
					catch (HibernateOptimisticLockingFailureException | StaleObjectStateException e) {
						e.printStackTrace();
					}
				}
				
				
			}
			
//			try {
//				mailService.sendNotificaitionAsync(korisnik.get(), null, "RESERVATION");
//			} catch (MailException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			return "REZERVISANE";
	}
	
	
	////////////////////////////////////
	////////////////////////////////////
	////////////////////////////////////
	////////////////////////////////////
	/*
	 * ZA POTREBE TESTIRANJA
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String rezervisiViseKarataTwin(Long idKorisnika, SlanjePozivniceZaRezervacijuDTO pozivnica)
	{
		Korisnik korisnik = korisnikRepo.getOne(idKorisnika);
		
		List<KorisnikDTO> prijatelji = pozivnica.getListaPrijatelja();
		List<KartaDTO> karte = pozivnica.getListaKarata();
		List<String> brojeviPasosa = pozivnica.getBrojeviPasosa();
		
		//ako postoji neka osoba da nije u prijateljima
		for(KorisnikDTO dto : prijatelji)
		{
			//nez zasto ovakav uslov al radi tako da ne diraj nista :D
//			if(korisnik.getPrijateljiKorisnika().contains(korisnikConv.convertFromDTO(dto)))
//				return "NOT_FRIEND_ERR";
		}
			
		
		//za te prijatelje koje dobijemo ovde (npr rezervisali smo 5 karata a pozvali 2 prijatelja, preostale 3 ce ici na nas)
		//jedan prijatelj = jedna rezervacija

		
		//izbacujemo duplikate iz prijatelja (nisam uspeo u reactu)
		Set<KorisnikDTO> dist = new LinkedHashSet<KorisnikDTO>(); 
		dist.addAll(prijatelji); 
		prijatelji.clear(); 
		prijatelji.addAll(dist);
		
		LocalDateTime date = LocalDateTime.now();
		
		for(KartaDTO karta : karte)
		{
			if(prijatelji.isEmpty())
				break;
				
				for(KorisnikDTO prijatelj : prijatelji)
				{
						if(karta.getKorisnik().getId() == 1)
						{
							karta.setKorisnik(prijatelj);
							karta.setBrojPasosa(prijatelj.getBrojPasosa());
							karta.setKorisnikKojiSaljePozivnicu(korisnikConv.convertToDTO(korisnik));
							//necemo ovde rezervisati vreme, to cemo tek kad prijatelj prihvati rezervaciju
							//karta.setVremeRezervisanja(date); 
							kartaRepo.save(kartaConv.convertFromDTO(karta));
							
							//slanje mail-a sa linkom
							try {
								mailService.sendNotificaitionAsync(korisnik, korisnikConv.convertFromDTO(prijatelj), "FR_INV");
							} catch (MailException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							prijatelji.remove(prijatelj);
							if(prijatelji.isEmpty())
							{
								break;
							}
						}
				}
		}
		
		
		//sad pravimo novu listu karata koje su preostale, dakle koje nisu otisle kao pozivnice
		
		List<KartaDTO> novaListaKarata = new ArrayList<KartaDTO>();
		
		for(KartaDTO karta : karte)
		{
			if(karta.getKorisnik().getId() == 1)
			{
				novaListaKarata.add(karta);
			}
		}
		
		boolean flag = false;
		//Jedna ide na nas, ostale idu na prosledjene brojeve pasosa
			for(KartaDTO dto : novaListaKarata)
			{
				Karta card = kartaConv.convertFromDTO(dto);
				if(!flag)
				{
					//postavljamo broj zauzetih mesta za konkretan let
					Let let = letRepo.getOne(dto.getLet().getIdLeta());
					let.setBrojOsoba(let.getBrojOsoba() + karte.size());
					letRepo.save(let);
					
					//prvi broj pasosa ide na korisnika
					card.setBrojPasosa(korisnik.getBrojPasosa());
					card.setKorisnik(korisnik); 
					card.setVremeRezervisanja(date);
					kartaRepo.save(card);
					flag = true;
				}	
				else
				{
					card.setKorisnik(korisnik); 
					card.setVremeRezervisanja(date);
					
					for(String pasos : brojeviPasosa)
					{
						card.setBrojPasosa(pasos);
						brojeviPasosa.remove(pasos);
						break;
					}
					
					kartaRepo.save(card);
				}
				
				
			}
			
			try {
				mailService.sendNotificaitionAsync(korisnik, null, "RESERVATION");
			} catch (MailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "REZERVISANE";
	}
	
	////////////////////////////////////
	////////////////////////////////////
	////////////////////////////////////
	////////////////////////////////////
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean obrisiRezervacijuJedneKarte(Long idKorisnika, Long idKarte) 
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		Optional<Karta> karta = kartaRepo.findById(idKarte);
		
		LocalDateTime date = LocalDateTime.now().minusHours(3);
		
		if(karta.get().getIdHotelRezervacije() != null)
		{
			this.rezService.deleteReservation(karta.get().getIdHotelRezervacije());
			
			karta.get().setIdHotelRezervacije(null);
		}
		
			if(karta.get().getLet().getVremePoletanja().isAfter(date))
			{
				//moze otkazati
				karta.get().setKorisnik(korisnikRepo.findById((long) 1).get());
				karta.get().setVremeRezervisanja(LocalDateTime.of(2000, 10, 10, 10, 10));
				karta.get().getLet().setBrojOsoba(karta.get().getLet().getBrojOsoba()-1);
				karta.get().setBrojPasosa("0");
				kartaRepo.save(karta.get());
				
				return true;
			}
			else
			{
				System.out.println("IZASAO NA DRUGOM");
				return false;
			}
	}

	/*
	 * Vraca sve nerezervisane karte za jedan let
	 */
	@Transactional(readOnly = true)
	public List<KartaDTO> getAllNerezervisaneKarte(Long idLeta)
	{
		List<Karta> karte = kartaRepo.findAll();
		Optional<Let> let = letRepo.findById(idLeta);
		
		List<KartaDTO> retVal = new ArrayList<KartaDTO>();
		
		for(Karta karta : karte)
		{
			if(karta.getKorisnik().getId() == 1 && karta.getLet().equals(let.get()))
			{
				retVal.add(kartaConv.convertToDTO(karta));
			}
		}
		
		return retVal;
	}

	

	

}

