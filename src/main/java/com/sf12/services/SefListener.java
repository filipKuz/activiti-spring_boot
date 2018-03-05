package com.sf12.services;

import org.activiti.engine.IdentityService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SefListener implements ExecutionListener{

	@Override
	public void notify(DelegateExecution arg0) throws Exception {
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			arg0.setVariable("odobrioSef", user.getUsername());
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
