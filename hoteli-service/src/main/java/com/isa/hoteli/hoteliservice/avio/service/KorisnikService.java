package com.isa.hoteli.hoteliservice.avio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.hoteli.hoteliservice.avio.converter.KartaConverter;
import com.isa.hoteli.hoteliservice.avio.converter.KorisnikConverter;
import com.isa.hoteli.hoteliservice.avio.dto.KartaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.repository.KartaRepository;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.repository.LetRepository;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@Service
public class KorisnikService
{
	@Autowired
	private KorisnikRepository korisnikRepo;
	
	@Autowired
	private KorisnikConverter korisnikConv;
	
	@Autowired
	private LetRepository letRepo;
	
	@Autowired
	private KartaRepository kartaRepo;
	
	@Autowired
	private KartaConverter kartaConv;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private JwtTokenUtils jwtTokenProvider;
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String posaljiZahtev(Long idKorisnika, String email)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		Optional<Korisnik> prijatelj = korisnikRepo.findUserByEmailOptional(email);
		
		if(!prijatelj.isPresent())
			return "EMAIL_ERR";
		
		if(korisnik.get().getZahteviKorisnika().contains(prijatelj.get()))
			return "EXISTS_ERR";
		
		if(korisnik.get().getPrijateljiKorisnika().contains(prijatelj.get()))
			return "EXISTS_ERR";
		
		else if(korisnik.get().getEmail().equals(prijatelj.get().getEmail()))
			return "EXISTS_ERR";
		
		List<Korisnik> insert = korisnik.get().getZahteviKorisnika();
		insert.add(prijatelj.get());
		
		korisnik.get().setZahteviKorisnika(insert);
		korisnikRepo.save(korisnik.get());
		
		
		try {
			mailService.sendNotificaitionAsync(korisnik.get(), prijatelj.get(), "FR_REQ");
		} catch (MailException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "SUCCESS";
	}

	/*
	 * Prihvatanje zahteva korisnika za prijateljstvo (BACA CONCURENTMODIFICATIONEXCEPTION)
	 */
	//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String prihvatiZahtev(Long idTrenutni, Long idPosiljalac) 
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idTrenutni);
		Optional<Korisnik> prijateljKojiSalje = korisnikRepo.findById(idPosiljalac);
		
//		if(!prijateljKojiSalje.isPresent())
//			return "EMAIL_ERR";
		
		if(korisnik.get().getPrijateljiKorisnika().contains(prijateljKojiSalje.get()))
			return "EXISTS_ERR";
		
		
		List<Korisnik> insert = korisnik.get().getPrijateljiKorisnika();
		insert.add(prijateljKojiSalje.get());
		
		korisnik.get().setPrijateljiKorisnika(insert);
		korisnikRepo.save(korisnik.get());
		
		//brisanje iz liste zahteva
		
		List<Korisnik> zahteviKorisnika = korisnik.get().getZahteviKorisnika();
		
		List<Korisnik> sviZahtevi = korisnik.get().getZahteviKorisnika();
		
		for(Korisnik prijateljZaht : zahteviKorisnika)
		{
			System.out.println(prijateljZaht.getIme());
			if(prijateljZaht.getEmail().equals(prijateljKojiSalje.get().getEmail()))
			{
				sviZahtevi.remove(prijateljZaht);
				korisnik.get().setZahteviKorisnika(new ArrayList<>());
				korisnikRepo.save(korisnik.get());
			}
		}
		
		return "SUCCESS";
	}
	
	//moze i jednostavniji nacin al model nije ok
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String odbijZahtev(Long idTrenutni, Long idPosiljalac) 
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idTrenutni);
		Optional<Korisnik> prijateljKojiSalje = korisnikRepo.findById(idPosiljalac);
		
		//brisanje iz liste zahteva
		

		List<Korisnik> prijateljiKorisnika = korisnik.get().getZahteviKorisnika();
		
		List<Korisnik> sviPrijatelji = korisnik.get().getZahteviKorisnika();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika)
		{
			if(prijateljZaht.getEmail().equals(prijateljKojiSalje.get().getEmail()))
			{
				System.out.println("USAO U IF 1");
				sviPrijatelji.remove(prijateljZaht);
				korisnik.get().setZahteviKorisnika(sviPrijatelji);
				korisnikRepo.save(korisnik.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		List<Korisnik> prijateljiKorisnika2 = korisnik.get().getKorisniciZaht();
		
		List<Korisnik> sviPrijatelji2 = korisnik.get().getKorisniciZaht();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika2)
		{
			if(prijateljZaht.getEmail().equals(korisnik.get().getEmail()))
			{
				System.out.println("USAO U IF 2");
				sviPrijatelji2.remove(prijateljZaht);
				korisnik.get().setKorisniciZaht(sviPrijatelji2);
				korisnikRepo.save(korisnik.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		
		List<Korisnik> prijateljiKorisnika3 = prijateljKojiSalje.get().getZahteviKorisnika();
		
		List<Korisnik> sviPrijatelji3 = prijateljKojiSalje.get().getZahteviKorisnika();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika3)
		{
			if(prijateljZaht.getEmail().equals(prijateljKojiSalje.get().getEmail()))
			{
				System.out.println("USAO U IF 3");
				sviPrijatelji3.remove(prijateljZaht);
				prijateljKojiSalje.get().setZahteviKorisnika(sviPrijatelji3);
				korisnikRepo.save(prijateljKojiSalje.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		List<Korisnik> prijateljiKorisnika4 = prijateljKojiSalje.get().getKorisniciZaht();
		
		List<Korisnik> sviPrijatelji4 = prijateljKojiSalje.get().getKorisniciZaht();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika4)
		{
			if(prijateljZaht.getEmail().equals(korisnik.get().getEmail()))
			{
				System.out.println("USAO U IF 4");
				sviPrijatelji4.remove(prijateljZaht);
				prijateljKojiSalje.get().setKorisniciZaht(sviPrijatelji4);
				korisnikRepo.save(prijateljKojiSalje.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		return "SUCCESS";
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String obrisiPrijatelja(Long idTrenutni, Long idZaBrisanje)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idTrenutni);
		Optional<Korisnik> prijateljKojiSalje = korisnikRepo.findById(idZaBrisanje);
		
		//samo if 1 i 3 trebaju, ovo se moze brisati
		
		List<Korisnik> prijateljiKorisnika = korisnik.get().getPrijateljiKorisnika();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika)
		{
			if(prijateljZaht.getEmail().equals(prijateljKojiSalje.get().getEmail()))
			{
				System.out.println("USAO U IF 1");
				prijateljiKorisnika.remove(prijateljZaht);
				korisnik.get().setPrijateljiKorisnika(prijateljiKorisnika);
				korisnikRepo.save(korisnik.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		List<Korisnik> prijateljiKorisnika2 = korisnik.get().getKorisnici();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika2)
		{
			if(prijateljZaht.getEmail().equals(korisnik.get().getEmail()))
			{
				System.out.println("USAO U IF 2");
				prijateljiKorisnika2.remove(prijateljZaht);
				korisnik.get().setKorisnici(prijateljiKorisnika2);
				korisnikRepo.save(korisnik.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		
		List<Korisnik> prijateljiKorisnika3 = prijateljKojiSalje.get().getPrijateljiKorisnika();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika3)
		{
			if(prijateljZaht.getEmail().equals(korisnik.get().getEmail()))
			{
				System.out.println("USAO U IF 3");
				prijateljiKorisnika3.remove(prijateljZaht);
				prijateljKojiSalje.get().setPrijateljiKorisnika(prijateljiKorisnika3);
				korisnikRepo.save(prijateljKojiSalje.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		List<Korisnik> prijateljiKorisnika4 = prijateljKojiSalje.get().getKorisnici();
		
		for(Korisnik prijateljZaht : prijateljiKorisnika4)
		{
			if(prijateljZaht.getEmail().equals(korisnik.get().getEmail()))
			{
				System.out.println("USAO U IF 4");
				prijateljiKorisnika4.remove(prijateljZaht);
				prijateljKojiSalje.get().setKorisnici(prijateljiKorisnika4);
				korisnikRepo.save(prijateljKojiSalje.get());
				
				return "SUCCESS";
			}
			else
				continue;
		}
		
		return "ERROR";
		
	}
	
	/*
	 * OCENA KORISNIKA NAKON ZAVRSENOG LETA
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String oceniLet(Long idKorisnika, Long idKarte, Integer ocena)
	{
		Optional<Karta> karta = kartaRepo.findById(idKarte);

		Let let = karta.get().getLet();
		
		LocalDateTime date = LocalDateTime.now();
		
		if(let.getVremeSletanja().isBefore(date))
		{
			karta.get().setOcena(ocena);
			kartaRepo.save(karta.get());
			
			//uzimamo sve ocene
			List<Integer> ocene = new ArrayList<Integer>();
			for(Karta kartaTemp : let.getKarteLeta())
			{
				if(kartaTemp.getOcena() > 0) //default je 0
				{
					ocene.add(kartaTemp.getOcena());
				}
			}
			
			float prosecna = 0;
			float suma = 0;
			float uk = 0;
			//racunamo prosecnu vrednost od njih
			for(int i=0; i<ocene.size(); i++)
			{
				suma += ocene.get(i);
				uk = i+1;
			}
			
			prosecna = suma/uk;
			System.out.println("PROSECNA: " + zaokruzi(prosecna,2));
			let.setProsecnaOcena(zaokruzi(prosecna,2));
			letRepo.save(let);
			
		}
			
		else
			return "FLIGHT_IS_ON";
		
			
		return "SUCCESS";
	}
	
	private float zaokruzi(float broj, int preciznost) {
	    return (float) (Math.round(broj * Math.pow(10, preciznost)) / Math.pow(10, preciznost));
	}
	
	
	@Transactional(readOnly = true)
	public List<Korisnik> getUsers()
	{
		return korisnikRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Korisnik getUserById(Long id)
	{
		return korisnikRepo.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public Korisnik getUserByEmail(String email)
	{
		return korisnikRepo.getUserByEmail(email);
	}
	
	@Transactional(readOnly = false)
	public KorisnikDTO createKorisnika(Korisnik kor)
	{
		if(korisnikRepo.getUserByEmail(kor.getEmail()) == null)
			return new KorisnikDTO(korisnikRepo.save(kor));
		
		return null;
	}
	
	@Transactional(readOnly = false)
	public KorisnikDTO createUser(Korisnik obj) {
		if(korisnikRepo.getUserByEmail(obj.getEmail())==null) {
			return new KorisnikDTO(korisnikRepo.save(obj));
		}
		return null;//korisnik sa istim emailom vec postoji
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String deleteUser(Long id) {
		korisnikRepo.deleteById(id);
		return "Uspesno obrisan korisnik sa id: " + id;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public KorisnikDTO updateKorisnika(Korisnik obj, Long id)
	{
		Optional<Korisnik> obj1 = korisnikRepo.findById(id);
		if(obj1.isPresent())
		{
			Optional<Korisnik> k = korisnikRepo.findUserByEmailOptional(obj.getEmail());
		
			if(!k.isPresent() || k.get().getId() == id)
			{
				obj1.get().setEmail(obj.getEmail());
				obj1.get().setLozinka(obj.getLozinka());
				obj1.get().setIme(obj.getIme());
				obj1.get().setPrezime(obj.getPrezime());
				obj1.get().setGrad(obj.getGrad());
				obj1.get().setTelefon(obj.getTelefon());
				korisnikRepo.save(obj1.get());
				
				return new KorisnikDTO(obj1.get());
			}
		}
		
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public KorisnikDTO updateUser(Korisnik obj, Long id) {
		Korisnik obj1 = korisnikRepo.getOne(id);
		if(obj1!=null) {
			Korisnik k = korisnikRepo.getUserByEmail(obj.getEmail());
			if(k==null || k.getId()==id) {//ako jedino taj korisnik koji se trenutno menja ima isti email
				obj1.setEmail(obj.getEmail());
				obj1.setLozinka(obj.getLozinka());
				obj1.setIme(obj.getIme());
				obj1.setPrezime(obj.getPrezime());
				obj1.setGrad(obj.getGrad());
				obj1.setTelefon(obj.getTelefon());
				korisnikRepo.save(obj1);
				return new KorisnikDTO(obj1);
			}
		}
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public KorisnikDTO updateFirstLogin(Long id, String s) {
		Optional<Korisnik> obj1 = korisnikRepo.findById(id);
		if(obj1.isPresent()) {
			obj1.get().setLozinka(s);
			obj1.get().setPrviPutLogovan(true);
			korisnikRepo.save(obj1.get());
			return new KorisnikDTO(obj1.get());
		}
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String login(String email, String lozinka)
	{
		Korisnik k = korisnikRepo.getUserByEmail(email);
		if (k == null)
			return null;

		try 
		{
			if (k.getLozinka().equals(lozinka))
			{
				String jwt = jwtTokenProvider.createToken(email);
				ObjectMapper mapper = new ObjectMapper();

				return mapper.writeValueAsString(jwt);
			}

		} catch (JsonProcessingException e) 
		{
			return null;
		}
		
		return null;
	}
	
	public Korisnik zaTokene(HttpServletRequest req) {
		String token = jwtTokenProvider.resolveToken(req);
		String email = jwtTokenProvider.getUsername(token);
		Korisnik k = this.getUserByEmail(email);
		return k;
	}
	
	/*
	 * hoteli operacije
	 */
	@Transactional(readOnly = true)
	public List<Korisnik> getHotelAdmins() {
		return korisnikRepo.findAdmineHotela();
	}
	
	@Transactional(readOnly = true)
	public List<Korisnik> getAvioAdmins() {
		return korisnikRepo.findAdmineAvio();
	}
	
	@Transactional(readOnly = true)
	public List<Korisnik> getRentAdmins() {
		return korisnikRepo.findAdmineRent();
	}
	
	/*
	 * avio operacije
	 */

	@Transactional(readOnly = true)
	public List<KartaDTO> getAllRezervisaneKarteZaTogKorisnika(Long idKorisnika)
	{
		List<Karta> karte = kartaRepo.findAll();
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		
		List<KartaDTO> karteRet = new ArrayList<KartaDTO>();
		
		for(Karta karta : karte)
		{
			if(karta.getKorisnik().equals(korisnik.get()) && karta.getVremeRezervisanja().getYear() > 2002)
			{
				karteRet.add(kartaConv.convertToDTO(karta));
			}
			else
				continue;
		}
		
		return karteRet;
	}

	/*
	 * Uzima zahteve koji su poslati OVOM korisniku (a ne koje je ovaj korisnik poslao)
	 */
	@Transactional(readOnly = true)
	public List<KorisnikDTO> getAllZahteviZaPrijateljstvo(Long idKorisnika)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		
		List<KorisnikDTO> korisniciRet = new ArrayList<KorisnikDTO>();
		
		for(Korisnik kor : korisnik.get().getKorisniciZaht())
		{
			korisniciRet.add(korisnikConv.convertToDTO(kor));
		}
		
		return korisniciRet;
	}

	@Transactional(readOnly = true)
	public List<KorisnikDTO> getaAllPrijatelji(Long idKorisnika)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
	
		List<KorisnikDTO> korisniciRet = new ArrayList<KorisnikDTO>();
		
		for(Korisnik kor : korisnik.get().getPrijateljiKorisnika())
		{
			korisniciRet.add(korisnikConv.convertToDTO(kor));
		}
		
		//mora i sa druge strane
		for(Korisnik kor : korisnik.get().getKorisnici())
		{
			korisniciRet.add(korisnikConv.convertToDTO(kor));
		}
		
		return korisniciRet;
	}

	@Transactional(readOnly = true)
	public List<KartaDTO> getAllPozivniceZaPutovanje(Long idKorisnika)
	{
		Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
		List<Karta> sveKarte = kartaRepo.findAll();
		
		List<KartaDTO> karteRet = new ArrayList<KartaDTO>();
		
		for(Karta karta : sveKarte)
		{
			//drugi deo uslova je da ne prikazuje karte kao pozivnicu za korisnika koji je sam sebi rezervisao
			if(karta.getKorisnik().getId() == korisnik.get().getId() && karta.getKorisnikKojiSaljePozivnicu().getId() != 1 && karta.getVremeRezervisanja().getYear() < 2002)
			{
				karteRet.add(kartaConv.convertToDTO(karta));
			}
		}
		
		return karteRet;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String prihvatiPozivnicu(Long idKarte)
	{
		Optional<Karta> karta = kartaRepo.findById(idKarte);
		Korisnik kor = karta.get().getKorisnik();
		
		LocalDateTime date = LocalDateTime.now();
		
		if(karta.get().getLet().getVremePoletanja().isBefore(date))
			return "ZAKASNIO";
		
		try {
			mailService.sendNotificaitionAsync(karta.get().getKorisnik(), null, "RESERVATION");
		} catch (MailException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		karta.get().setVremeRezervisanja(date);
		karta.get().setBrojPasosa(kor.getBrojPasosa());
		kartaRepo.save(karta.get());
		
		return "SUCCESS";
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String odbijPozivnicu(Long idKarte)
	{
		Optional<Korisnik> kor = korisnikRepo.findById((long) 1);
		Optional<Karta> karta = kartaRepo.findById(idKarte);
		
		karta.get().setKorisnik(kor.get());
		karta.get().setKorisnikKojiSaljePozivnicu(kor.get());
		karta.get().setBrojPasosa("0");
		karta.get().getLet().setBrojOsoba(karta.get().getLet().getBrojOsoba() - 1);
		
		kartaRepo.save(karta.get());
		
		return "SUCCESS";
	}
}
