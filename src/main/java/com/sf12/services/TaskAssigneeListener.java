package com.sf12.services;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class TaskAssigneeListener implements ExecutionListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void notify(DelegateExecution arg0) throws Exception {
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			arg0.setVariable("taskAssignee", user.getUsername());
			System.out.println(user.getUsername());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
