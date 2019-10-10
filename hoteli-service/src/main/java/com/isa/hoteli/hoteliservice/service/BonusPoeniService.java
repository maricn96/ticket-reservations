package com.isa.hoteli.hoteliservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.hoteli.hoteliservice.dto.HotelDTO;
import com.isa.hoteli.hoteliservice.model.BonusPoeni;
import com.isa.hoteli.hoteliservice.model.Hotel;
import com.isa.hoteli.hoteliservice.repository.BonusPoeniRepository;

@Component
public class BonusPoeniService {
	
	@Autowired
	private BonusPoeniRepository bonusPoeniRepository;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BonusPoeni updateBonus(BonusPoeni bp, Long id) {
		BonusPoeni bonus = bonusPoeniRepository.getOne(id);
		if(bonus!=null) {
			bonus.setPopust(bp.getPopust());
			bonusPoeniRepository.save(bonus);
			return bp;
		}
		return null;
	}

}
