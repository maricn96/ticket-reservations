package com.isa.hoteli.hoteliservice.avio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.avio.dto.KartaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.KorisnikDTO;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.model.Login;
import com.isa.hoteli.hoteliservice.security.JwtTokenUtils;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/korisnik")
public class KorisnikController
{
	@Autowired
	private KorisnikService korService;
	
	@Autowired
	JwtTokenUtils jwtTokenUtils;
	
	@GetMapping("/all")
	public ResponseEntity<List<KorisnikDTO>> getKorisnici(HttpServletRequest req)
	{
//		Korisnik k = korService.zaTokene(req);
//		
//		if(k != null && k.getRola().equals(Rola.MASTER_ADMIN))
//		{
			List<KorisnikDTO> dto = new ArrayList<KorisnikDTO>();
			List<Korisnik> lista = korService.getUsers();
			
			for(Korisnik item : lista)
			{
				dto.add(new KorisnikDTO(item));
			}
			return new ResponseEntity<List<KorisnikDTO>>(dto, HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> getUserById(@PathVariable("id") Long id)
	{
		if(korService.getUserById(id)!=null) {
			KorisnikDTO dto = new KorisnikDTO(korService.getUserById(id));
			return new ResponseEntity<KorisnikDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/all/{email}", method = RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> getUserByEmail(@PathVariable("email") String email)
	{
		
		System.out.println("preuzmiRolu()");
		
		if(korService.getUserByEmail(email)!=null) {
			KorisnikDTO dto = new KorisnikDTO(korService.getUserByEmail(email));
			return new ResponseEntity<KorisnikDTO>(dto, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<KorisnikDTO> createUser(@RequestBody KorisnikDTO dto)
	{
		Korisnik obj = new Korisnik(dto);
		obj.setRola(Rola.KORISNIK);
		KorisnikDTO returnType = korService.createKorisnika(obj);
		if(returnType!=null) {
			return new ResponseEntity<>(returnType, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/admin", method = RequestMethod.POST)
	public ResponseEntity<KorisnikDTO> createAdmin(@RequestBody KorisnikDTO dto, HttpServletRequest req){
		Korisnik k = korService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.MASTER_ADMIN)) {
			Korisnik obj = new Korisnik(dto);
			obj.setAktiviran(true);
			KorisnikDTO returnType = korService.createUser(obj);
			if(returnType!=null) {
				return new ResponseEntity<>(returnType, HttpStatus.OK);
			}
		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id)
	{
		return new ResponseEntity<String>(korService.deleteUser(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<KorisnikDTO> updateUserById(@PathVariable("id") Long id, @RequestBody KorisnikDTO dto)
	{
		Korisnik obj = new Korisnik(dto);
		KorisnikDTO returnTip = korService.updateKorisnika(obj, id);
		if(returnTip!=null) {
			return new ResponseEntity<>(returnTip, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/firstLogin/{id}", method = RequestMethod.PUT)
	public ResponseEntity<KorisnikDTO> updateLoginById(@PathVariable("id") Long id, @RequestBody KorisnikDTO dto)
	{
		String s = dto.getLozinka();
		KorisnikDTO returnTip = korService.updateFirstLogin(id, s);
		if(returnTip!=null) {
			return new ResponseEntity<>(returnTip, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody Login login)
	{
		
		System.out.println("login()");
		
		String email = login.getEmail();
		String lozinka = login.getLozinka();
		String jwt = korService.login(email, lozinka);
		return (jwt!=null) ? new ResponseEntity<String>(jwt, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	/*
	 * OSTALE OPERACIJE
	 */
	
	
	/*
	 * U zaglavlju prima id korisnika koji salje zahtev, telo sadrzi email korisnika kome se salje zahtev
	 */
	@PostMapping("/invitefriend/{id}")
	public ResponseEntity<String> posaljiZahtev(@PathVariable("id") Long idKorisnika, @RequestBody String email)
	{
		System.out.println("posaljiZahtev()");
		System.out.println(email);
		
		String emaill = email.split(":")[1];
		emaill = emaill.replace('"', ' ');
		emaill = emaill.replace('}', ' ');
		
		String retVal = korService.posaljiZahtev(idKorisnika, emaill.trim());
		
		return (!retVal.equals("SUCCESS")) ? new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST) : new ResponseEntity<String>(retVal, HttpStatus.OK);
	}
	
	/*
	 * PRIHVATANJE ZAHTEVA
	 * Parametar1 = id korisnika na cijoj smo acc info stranici, parametar2 = id korisnika koji salje zahtev
	 */
	@PostMapping("/acceptrequest/{currentuserid}/{senderuserid}")
	public ResponseEntity<String> prihvatiZahtev(@PathVariable("currentuserid") Long idTrenutni, @PathVariable("senderuserid") Long idPosiljalac)
	{
		System.out.println("prihvatiZahtev()");
		
		String retVal = korService.prihvatiZahtev(idTrenutni, idPosiljalac);
		
		return (!retVal.equals("SUCCESS")) ? new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST) : new ResponseEntity<String>(retVal, HttpStatus.OK);
	}
	
	/*
	 * ODBIJANJE ZAHTEVA
	 * Parametar1 = id korisnika na cijoj smo acc info stranici, parametar2 = id korisnika koji salje zahtev
	 */
	@PostMapping("/refuserequest/{currentuserid}/{senderuserid}")
	public ResponseEntity<String> odbijZahtev(@PathVariable("currentuserid") Long idTrenutni, @PathVariable("senderuserid") Long idPosiljalac)
	{
		System.out.println("odbijZahtev()");
		
		String retVal = korService.odbijZahtev(idTrenutni, idPosiljalac);
		
		return (!retVal.equals("SUCCESS")) ? new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST) : new ResponseEntity<String>(retVal, HttpStatus.OK);
	}
	
	/*
	 * BRISANJE PRIJATELJA
	 * Parametar1 = id korisnika na cijoj smo acc info stranici, parametar2 = id korisnika koji se brise
	 */
	@PostMapping("/deletefriend/{currentuserid}/{senderuserid}")
	public ResponseEntity<String> obrisiPrijatelja(@PathVariable("currentuserid") Long idTrenutni, @PathVariable("senderuserid") Long idZaBrisanje)
	{
		System.out.println("obrisiPrijatelja()");
		
		String retVal = korService.obrisiPrijatelja(idTrenutni, idZaBrisanje);
		
		return (!retVal.equals("SUCCESS")) ? new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST) : new ResponseEntity<String>(retVal, HttpStatus.OK);
	}
	
	
	/*
	 * Nakon zavrsenog leta korisnik ocenjuje let (preko karte koju je kupio)
	 * Metoda prima id korisnika koji je kupio kartu kao i id karte, jer moze da postoji mogucnost
	 * da je jedan korisnik kupio vise karata, kao i ocenu (nije moglo preko body-ja)
	 */
	@PutMapping("/rateflight/{userid}/{ticketid}/{rate}")
	public ResponseEntity<String> oceniLet(@PathVariable("userid") Long idKorisnika, @PathVariable("ticketid") Long idKarte, @PathVariable("rate") Integer rate)
	{
		System.out.println("oceniLet()");
	
		String retVal = korService.oceniLet(idKorisnika, idKarte, rate);
		
		return (retVal.equals("FLIGHT_IS_ON")) ? new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST) : new ResponseEntity<String>(retVal, HttpStatus.CREATED);
	}
	
	/*
	 * Vraca sve rezervisane karte za prosledjenog korisnika
	 */
	@GetMapping("/getallreservations/{userid}")
	public ResponseEntity<List<KartaDTO>> getAllRezervisaneKarteZaTogKorisnika(@PathVariable("userid") Long idKorisnika)
	{
		System.out.println("getAllRezervisaneKarteZaTogKorisnika()");
		
		List<KartaDTO> listDto = korService.getAllRezervisaneKarteZaTogKorisnika(idKorisnika);
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<KartaDTO>>(listDto, HttpStatus.OK);
	}
	
	
	/*
	 * Vraca sve zahteve za prijateljstvo koje je dobio od drugih korisnika (za datog korisnika)
	 */
	@GetMapping("/getallrequests/{userid}")
	public ResponseEntity<List<KorisnikDTO>> getAllZahteviZaPrijateljstvo(@PathVariable("userid") Long idKorisnika)
	{
		System.out.println("getAllZahteviZaPrijateljstvo()");
		
		List<KorisnikDTO> listDto = korService.getAllZahteviZaPrijateljstvo(idKorisnika);
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<KorisnikDTO>>(listDto, HttpStatus.OK);
	}
	
	/*
	 * Vraca sve prijatelje datog korisnika
	 */
	@GetMapping("/getallfriends/{userid}")
	public ResponseEntity<List<KorisnikDTO>> getaAllPrijatelji(@PathVariable("userid") Long idKorisnika)
	{
		System.out.println("getaAllPrijatelji()");
		
		List<KorisnikDTO> listDto = korService.getaAllPrijatelji(idKorisnika);
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<KorisnikDTO>>(listDto, HttpStatus.OK);
	}
	
	
	/*
	 * Vraca sve pozivnice za rezervaciju karte u avionu (za datog korisnika)
	 */
	@GetMapping("/getallinvitations/{userid}")
	public ResponseEntity<List<KartaDTO>> getAllPozivniceZaPutovanje(@PathVariable("userid") Long idKorisnika)
	{
		System.out.println("getAllPozivniceZaPutovanje()");
		
		List<KartaDTO> listDto = korService.getAllPozivniceZaPutovanje(idKorisnika);
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<KartaDTO>>(listDto, HttpStatus.OK);
	}
	
	
	/*
	 * PRIHVATANJE POZIVNICE ZA LET
	 * id karte koju prihvatamo
	 */
	@PostMapping("/acceptinvrequest/{ticketid}")
	public ResponseEntity<String> prihvatiPozivnicu(@PathVariable("ticketid") Long idKarte)
	{
		System.out.println("prihvatiPozivnicu()");
		
		String retVal = korService.prihvatiPozivnicu(idKarte);
		
		return (!retVal.equals("SUCCESS")) ? new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST) : new ResponseEntity<String>(retVal, HttpStatus.OK);
	}
	
	/*
	 * ODBIJANJE POZIVNICE ZA LET	
	 * Parametar1 = id korisnika na cijoj smo acc info stranici, parametar2 = id korisnika koji salje zahtev
	 */
	@PostMapping("/refuseinvrequest/{ticketid}")
	public ResponseEntity<String> odbijPozivnicu(@PathVariable("ticketid") Long idKarte)
	{
		System.out.println("odbijPozivnicu()");
		
		String retVal = korService.odbijPozivnicu(idKarte);
		
		return (!retVal.equals("SUCCESS")) ? new ResponseEntity<String>(retVal, HttpStatus.BAD_REQUEST) : new ResponseEntity<String>(retVal, HttpStatus.OK);
	}
	
	/*
	 * Markove 
	 * */
	
	@RequestMapping(value="/allHotelAdmins", method = RequestMethod.GET)
	public ResponseEntity<List<KorisnikDTO>> getHotelAdmins(HttpServletRequest req){
		Korisnik k = korService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.MASTER_ADMIN)) {
			List<KorisnikDTO> dto = new ArrayList<>();
			List<Korisnik> lista = korService.getHotelAdmins();
			for (Korisnik item : lista) {
				dto.add(new KorisnikDTO(item));
			}
			return new ResponseEntity<List<KorisnikDTO>>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/allAvioAdmins", method = RequestMethod.GET)
	public ResponseEntity<List<KorisnikDTO>> getAvioAdmins(HttpServletRequest req){
		Korisnik k = korService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.MASTER_ADMIN)) {
			List<KorisnikDTO> dto = new ArrayList<>();
			List<Korisnik> lista = korService.getAvioAdmins();
			for (Korisnik item : lista) {
				dto.add(new KorisnikDTO(item));
			}
			return new ResponseEntity<List<KorisnikDTO>>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	
	@RequestMapping(value="/allRentAdmins", method = RequestMethod.GET)
	public ResponseEntity<List<KorisnikDTO>> getRentAdmins(HttpServletRequest req){
		Korisnik k = korService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.MASTER_ADMIN)) {
			List<KorisnikDTO> dto = new ArrayList<>();
			List<Korisnik> lista = korService.getRentAdmins();
			for (Korisnik item : lista) {
				dto.add(new KorisnikDTO(item));
			}
			return new ResponseEntity<List<KorisnikDTO>>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
