package com.isa.hoteli.hoteliservice.avio.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.avio.converter.AvioKompanijaConverter;
import com.isa.hoteli.hoteliservice.avio.dto.AvioKompanijaDTO;
import com.isa.hoteli.hoteliservice.avio.dto.BrojKarataDnevnoDTO;
import com.isa.hoteli.hoteliservice.avio.model.AvioKompanija;
import com.isa.hoteli.hoteliservice.avio.model.Destinacija;
import com.isa.hoteli.hoteliservice.avio.model.Karta;
import com.isa.hoteli.hoteliservice.avio.model.Klasa;
import com.isa.hoteli.hoteliservice.avio.model.Let;
import com.isa.hoteli.hoteliservice.avio.repository.AvioKompanijaRepository;
import com.isa.hoteli.hoteliservice.avio.repository.DestinacijaRepository;
import com.isa.hoteli.hoteliservice.avio.repository.KartaRepository;
import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.model.Hotel;

@Service
public class AvioKompanijaService 
{
	@Autowired
	AvioKompanijaRepository avioRepo;
	
	@Autowired
	AvioKompanijaConverter avioConv;
	
	@Autowired
	private DestinacijaRepository destRepo;
	
	@Autowired
	private LetService letService;
	
	@Autowired
	private KartaRepository kartaRepo;
	
	
	public AvioKompanijaDTO traziById(Long id)
	{
		Optional<AvioKompanija> avio = avioRepo.findById(id);
		
		if(avio.isPresent())
			return avioConv.convertToDTO(avio.get());
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public AvioKompanija findById(Long id)
	{
		AvioKompanija avio = avioRepo.getOne(id);
		
		if(avio != null)
			return avio;
		else
			return null;
	}
	
	@Transactional(readOnly = true)
	public List<AvioKompanijaDTO> sortAvioKompanije(int value)
	{
		List<AvioKompanija> kompanije = new ArrayList<AvioKompanija>();
		List<AvioKompanijaDTO> retVal = new ArrayList<AvioKompanijaDTO>();
		
		if(value == 1)
			kompanije = avioRepo.sortByNaziv(); 
		else
			kompanije = avioRepo.sortByGrad();
		
		for(AvioKompanija komp : kompanije)
			retVal.add(avioConv.convertToDTO(komp));
		
		return retVal;
	}
	
	@Transactional(readOnly = true)
	public List<AvioKompanijaDTO> findAll()
	{
		List<AvioKompanija> list = avioRepo.findAll();
		
		List<AvioKompanijaDTO> listDto = new ArrayList<AvioKompanijaDTO>();
		
			for(AvioKompanija avio : list)
				listDto.add(avioConv.convertToDTO(avio));
			
			return listDto;
	}
	
	@Transactional(readOnly = true)
	public List<AvioKompanija> traziSve()
	{
		return avioRepo.findAll();
	}
	
	@Transactional(readOnly = false)
	public AvioKompanija saveOne(AvioKompanija avioKom)
	{
		AvioKompanija avio = avioRepo.getOne(avioKom.getIdAvioKompanije());
						
		
		if(avio != null)
			return null;
		else
		{				
			return avioRepo.save(avioKom);
		}
	}
	
	@Transactional(readOnly = false)//treba mi ovo, nemoj dirati
	public AvioKompanija createAvio(AvioKompanija av) {
		return avioRepo.save(av);
	}
	
//	public boolean addDefaultDestination(Long id)
//	{
//		Optional<AvioKompanija> avio = avioRepo.findById(id);
//		Optional<Destinacija> dest = destRepo.findById((long) 1);
//		
//		List<Destinacija> lista = new ArrayList<Destinacija>();
//		lista.add(dest.get());
//		
//		avio.get().setDestinacijeNaKojimaPosluje(lista);
//		avioRepo.save(avio.get());
//		
//		return true;
//	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public AvioKompanijaDTO updateOne(Long id, AvioKompanijaDTO dto)
	{
		AvioKompanija avio = avioRepo.getOne(id);
		
		if(avio != null)
		{
			//avio.setIdAvioKompanije(avioConv.convertFromDTO(dto).getIdAvioKompanije());
			avio.setNaziv(avioConv.convertFromDTO(dto).getNaziv());
			avio.setAdresa(avioConv.convertFromDTO(dto).getAdresa());
			avio.setOpis(avioConv.convertFromDTO(dto).getOpis());
			
//			if(!avio.getDestinacijeNaKojimaPosluje().isEmpty())
				avio.setDestinacijeNaKojimaPosluje(avioConv.convertFromDTO(dto).getDestinacijeNaKojimaPosluje());
			
			avioRepo.save(avio);
			
			return avioConv.convertToDTO(avio);
		}
		else
			return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public AvioKompanija updateOne(Long id, AvioKompanija dto)
	{
		AvioKompanija avio = avioRepo.getOne(id);
		
		if(avio != null)
		{
			//avio.setIdAvioKompanije(avioConv.convertFromDTO(dto).getIdAvioKompanije());
			avio.setNaziv(dto.getNaziv());
			avio.setAdresa(dto.getAdresa());
			avio.setOpis(dto.getOpis());
			
//			if(!avio.getDestinacijeNaKojimaPosluje().isEmpty())
				avio.setDestinacijeNaKojimaPosluje(dto.getDestinacijeNaKojimaPosluje());
			
			avioRepo.save(avio);
			
			return avio;
		}
		else
			return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean deleteOne(Long id)
	{
		AvioKompanija avio = avioRepo.getOne(id);
		
		if(avio != null)
		{
			List<Let> letovi = avio.getLetovi();
			
			//zakomentarisano zbog testova
//			for(Let let : letovi)
//			{
//				letService.deleteOne(let.getIdLeta());
//			}
			
			avioRepo.deleteById(id);
			return true;
		}
		else
			return false;
	}
	
	
	
	/*
	 * OSTALE OPERACIJE
	 */
	

	@Transactional(readOnly = true)
	public Float getSrednjaOcenaAvioKompanije(Long id)
	{
		
		Float avg = avioRepo.findAverageRating(id);
		
		if(avg != null)
		{
			return avg;
		}
		
		return null;
	}

	@Transactional(readOnly = true)
	public List<BrojKarataDnevnoDTO> getBrojProdatihKarataDnevno(Long id)
	{
		List<BrojKarataDnevnoDTO> returnList = new ArrayList<BrojKarataDnevnoDTO>();
		
		LocalDate date = LocalDate.now();
		
		//treba izvuci broj karata za taj i taj dan
		List<Karta> karte = kartaRepo.findAll();
		
		//ide 4 dana unazad i danasnji dan
		int suma = 0;
		for(int i=5; i>0; i--)
		{
			BrojKarataDnevnoDTO obj = new BrojKarataDnevnoDTO();
			LocalDate datum = date.minusDays(i-1);
			
			for(Karta karta : karte)
			{
				if(karta.getVremeRezervisanja().getYear() == datum.getYear() && karta.getVremeRezervisanja().getMonth() == datum.getMonth() && 
						karta.getVremeRezervisanja().getDayOfYear() == datum.getDayOfYear() && karta.getLet().getAviokompanija().getIdAvioKompanije() == id)
				{
					suma++;
				}
			}
			
			obj.setDatum(datum);
			obj.setBrojKarata(suma);
			returnList.add(obj);
			suma = 0;
		}
		
		return returnList;
	}
	
	@Transactional(readOnly = true)
	public List<BrojKarataDnevnoDTO> getBrojProdatihKarataNedeljno(Long id)
	{
		List<BrojKarataDnevnoDTO> returnList = new ArrayList<BrojKarataDnevnoDTO>();
		
		LocalDate date = LocalDate.now();
		
		List<Karta> karte = kartaRepo.findAll();
		
		int suma = 0;
		for(int i=5; i>0; i--)
		{
			BrojKarataDnevnoDTO obj = new BrojKarataDnevnoDTO();
			LocalDate datum = date.minusDays((i-1)*7);
			
			LocalDateTime datumTime = datum.atStartOfDay();
			
			for(Karta karta : karte)
			{
				if(karta.getVremeRezervisanja().getYear() == datum.getYear() && karta.getLet().getAviokompanija().getIdAvioKompanije() == id)
				{
					System.out.println("USO UIU IF");
					if(datumTime.isAfter(karta.getVremeRezervisanja()) && karta.getVremeRezervisanja().isAfter(datumTime.minusDays(7)))
					{
						System.out.println("USOOSU U NEKI DRUGI HF");
						suma++;
					}
				}
			}
			
			obj.setDatum(datum);
			obj.setBrojKarata(suma);
			returnList.add(obj);
			suma = 0;
		}
		
		return returnList;
	}
	
	@Transactional(readOnly = true)
	public List<BrojKarataDnevnoDTO> getBrojProdatihKarataMesecno(Long id)
	{
		List<BrojKarataDnevnoDTO> returnList = new ArrayList<BrojKarataDnevnoDTO>();
		
		LocalDate date = LocalDate.now();
		
		List<Karta> karte = kartaRepo.findAll();
		
		int suma = 0;
		for(int i=5; i>0; i--)
		{
			BrojKarataDnevnoDTO obj = new BrojKarataDnevnoDTO();
			LocalDate datum = date.minusMonths(i-1);
			
			for(Karta karta : karte)
			{
				if(karta.getVremeRezervisanja().getYear() == datum.getYear() && karta.getVremeRezervisanja().getMonth() == datum.getMonth()
						&& karta.getLet().getAviokompanija().getIdAvioKompanije() == id)
				{
					suma++;
				}
			}
			
			obj.setDatum(datum);
			obj.setBrojKarata(suma);
			returnList.add(obj);
			suma = 0;
		}
		
		return returnList;
	}

	@Transactional(readOnly = true)
	public Float getPrihodZaOdredjeniPeriod(Long id, LocalDate datumOd, LocalDate datumDo)
	{
		float prihod = 0;
		//prebacujemo u localdatetime
		LocalDateTime datum1 = datumOd.atStartOfDay();
		LocalDateTime datum2 = datumDo.atStartOfDay();
		
		List<Karta> karte = kartaRepo.findAll();
		
		for(Karta karta : karte)
		{
			if(karta.getLet().getAviokompanija().getIdAvioKompanije() == id)
			{
				if(karta.getVremeRezervisanja().isAfter(datum1) && karta.getVremeRezervisanja().isBefore(datum2))
				{
					prihod += karta.getCena();
				}
			}
			else
				continue;
		}
		
		return prihod;
	}

	
	
}
