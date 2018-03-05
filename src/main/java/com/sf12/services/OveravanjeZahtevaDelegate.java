package com.sf12.services;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sf12.entity.Zahtev;
import com.sf12.repositories.ZahtevJpaRepository;

public class OveravanjeZahtevaDelegate implements JavaDelegate {

	@Autowired
	private ZahtevJpaRepository zahtevJpa;
	
	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		
	}
	
	
	public void changeReq(Long id) {
		
	}

}
