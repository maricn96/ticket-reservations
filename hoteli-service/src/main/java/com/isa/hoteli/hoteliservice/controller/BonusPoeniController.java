package com.isa.hoteli.hoteliservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.hoteli.hoteliservice.avio.model.Korisnik;
import com.isa.hoteli.hoteliservice.avio.model.Rola;
import com.isa.hoteli.hoteliservice.avio.service.KorisnikService;
import com.isa.hoteli.hoteliservice.dto.CenaNocenjaDTO;
import com.isa.hoteli.hoteliservice.model.BonusPoeni;
import com.isa.hoteli.hoteliservice.model.CenaNocenja;
import com.isa.hoteli.hoteliservice.service.BonusPoeniService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/bonus")
public class BonusPoeniController {
	
	@Autowired
	private BonusPoeniService bonusPoeniService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	
	@RequestMapping(value="/", method = RequestMethod.PUT)
	public ResponseEntity<BonusPoeni> updateBonusById(@RequestBody BonusPoeni dto, HttpServletRequest req){
		Korisnik k = korisnikService.zaTokene(req);
		if(k!=null && k.getRola().equals(Rola.MASTER_ADMIN)) {
			BonusPoeni returnTip = bonusPoeniService.updateBonus(dto, 1l);
			if(returnTip!=null) {
				return new ResponseEntity<>(returnTip, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
