package com.isa.hoteli.hoteliservice.avio.exception;

import org.hibernate.StaleObjectStateException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;

public class ConcurrentException extends HibernateOptimisticLockingFailureException{
	
	public ConcurrentException(StaleObjectStateException message) {
		super(message);
	}
}
