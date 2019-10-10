package com.isa.hoteli.hoteliservice.avio.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.avio.converter.AvioKompanijaConverter;
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.BrojKarataDnevnoDTO;
import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;
import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.repository.KorisnikRepository;
import com.isa.hoteli.hoteliservice.avio.service.AvioKompanijaService;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.model.RentACar;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/aviocompany")
public class AvioKompanijaController
{
	@Autowired
	private AvioKompanijaService avioService;
	
	@Autowired
	private KorisnikService korServ;
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	
	@GetMapping("/getone/{id}")
	public ResponseEntity<AvioKompanijaDTO> getAvioKompanija(@PathVariable Long id, HttpServletRequest req)
	{
		System.out.println("getAvioKompanija()");
		
//		Korisnik k = korServ.zaTokene(req);
//		if(k != null && k.getRola().equals(Rola.ADMIN_AVIO_KOMPANIJE) && k.getZaduzenZaId() == id)
//		{
			AvioKompanijaDTO avioDto = avioService.traziById(id);
			return (avioDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<AvioKompanijaDTO>(avioDto, HttpStatus.OK);
//		}
//		
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<AvioKompanijaDTO>> getAllAvioKompanije()
	{
		System.out.println("getAllAvioKompanije()");
		
		List<AvioKompanijaDTO> listDto = avioService.findAll();
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<AvioKompanijaDTO>>(listDto, HttpStatus.OK);
	}
	
	//sortiranje po gradu ili nazivu
	@GetMapping("/sort/{value}")
	public ResponseEntity<List<AvioKompanijaDTO>> sortAvioKompanije(@PathVariable("value") int value)
	{
		System.out.println("sortAvioKompanije()");
		
		List<AvioKompanijaDTO> listDto = avioService.sortAvioKompanije(value);
		
		return (listDto == null) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<List<AvioKompanijaDTO>>(listDto, HttpStatus.OK);
	}
	
	
	/*@PostMapping("/add/")
	public ResponseEntity<AvioKompanijaDTO> addAvioKompanija(@RequestBody AvioKompanijaDTO dto)
	{
		System.out.println("addAvioKompanija()");
		
		return (avioService.saveOne(dto) == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<AvioKompanijaDTO>(dto, HttpStatus.CREATED);
	}*/
	
//	/*
//	 * Dodajemo difoltnu destinaciju na taj i taj aerodrom
//	 */
//	@PutMapping("/adddefaultdest/{companyid}")
//	public ResponseEntity<Boolean> addDifoltnaDestinacija(@PathVariable("companyid") Long id)
//	{
//		System.out.println("addDifoltnaDestinacija()");
//		
//		return (!avioService.addDefaultDestination(id)) ? new ResponseEntity<>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
//	}
	/*
	 * ADMIN
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<AvioKompanijaDTO> updateAvioKompanija(@PathVariable("id") String id, @RequestBody AvioKompanijaDTO dto, HttpServletRequest req)
	{
		System.out.println("updateAvioKompanija()");
		
		Korisnik k = korServ.zaTokene(req);
		if(k != null && k.getRola().equals(Rola.ADMIN_AVIO_KOMPANIJE) && k.getZaduzenZaId() == Long.parseLong(id, 10))
		{
			AvioKompanijaDTO avio = avioService.updateOne(Long.parseLong(id, 10), dto);
			return (avio == null) ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<AvioKompanijaDTO>(avio, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteAvioKompanija(@PathVariable("id") Long id)
	{
		System.out.println("deleteAvioKompanija()");
		
		return (!avioService.deleteOne(id)) ? new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST) : new ResponseEntity<Boolean>(true, HttpStatus.OK); 
	}

	
	/*
	 * OSTALE OPERACIJE
	 */
	
	/*
	 * Trazi prosecnu ocenu za jednu aviokompaniju na osnovu prosecnih ocena letova
	 */
	@GetMapping("/getavgrating/{id}")
	public ResponseEntity<Float> getSrednjaOcenaAvioKompanije(@PathVariable("id") Long id)
	{
		System.out.println("getSrednjaOcenaAvioKompanije()");
		
		Float avg = avioService.getSrednjaOcenaAvioKompanije(id);
		
		return(avg == null) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<Float>(avg, HttpStatus.OK);
	}
	
	/*
	 * Vraca listu objekata za grafik za 5 dana - broj prodatih karata
	 * Prima id kompanije za koju trazimo broj prodatih karata
	 */
	@GetMapping("/getsoldcardsbyday/{id}")
	public ResponseEntity<List<BrojKarataDnevnoDTO>> getBrojProdatihKarataDnevno(@PathVariable("id") Long id)
	{
		System.out.println("getBrojProdatihKarataDnevno()");
		
		List<BrojKarataDnevnoDTO> karte = avioService.getBrojProdatihKarataDnevno(id);
		
		return(karte == null) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<List<BrojKarataDnevnoDTO>>(karte, HttpStatus.OK);
	}
	
	/*
	 * prethodnih 7 dana bez danasnjeg dana 
	 */
	@GetMapping("/getsoldcardsbyweek/{id}")
	public ResponseEntity<List<BrojKarataDnevnoDTO>> getBrojProdatihKarataNedeljno(@PathVariable("id") Long id)
	{
		System.out.println("getBrojProdatihKarataNedeljno()");
		
		List<BrojKarataDnevnoDTO> karte = avioService.getBrojProdatihKarataNedeljno(id);
		
		return(karte == null) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<List<BrojKarataDnevnoDTO>>(karte, HttpStatus.OK);
	}
	
	@GetMapping("/getsoldcardsbymonth/{id}")
	public ResponseEntity<List<BrojKarataDnevnoDTO>> getBrojProdatihKarataMesecno(@PathVariable("id") Long id)
	{
		System.out.println("getBrojProdatihKarataMesecno()");
		
		List<BrojKarataDnevnoDTO> karte = avioService.getBrojProdatihKarataMesecno(id);
		
		return(karte == null) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<List<BrojKarataDnevnoDTO>>(karte, HttpStatus.OK);
	}
	
	@GetMapping("/getincomebydate/{id}/{date1}/{date2}")
	public ResponseEntity<Float> getPrihodZaOdredjeniPeriod(@PathVariable("id") Long id, @PathVariable("date1") String datumOd, @PathVariable("date2") String datumDo)
	{
		System.out.println("getPrihodZaOdredjeniPeriod()");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		LocalDate datum1 = LocalDate.parse(datumOd, formatter);
		LocalDate datum2 = LocalDate.parse(datumDo, formatter);
		
		Float prihod = avioService.getPrihodZaOdredjeniPeriod(id, datum1, datum2);
		
		return(prihod == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<Float>(prihod, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	public ResponseEntity<AvioKompanija> createAvioAdmin(@PathVariable Long id, @RequestBody AvioKompanija avioKompanija, HttpServletRequest req){
		Korisnik korisnik = korServ.zaTokene(req);
		if(korisnik!=null && korisnik.getRola().equals(Rola.MASTER_ADMIN)) {
			AvioKompanija avio = avioService.createAvio(avioKompanija);
			Korisnik k = korServ.getUserById(id);
			if(avio!=null) {
				k.setZaduzenZaId(avio.getIdAvioKompanije());
				korisnikRepository.save(k);
				return new ResponseEntity<>(avio, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
