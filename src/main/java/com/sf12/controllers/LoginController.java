package com.sf12.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sf12.services.ActivitiUserDetailService;

@Controller
public class LoginController {
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private ActivitiUserDetailService userDetail;
	
/*	
	@GetMapping(value = "/log-out")
	public void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		response.sendRedirect("/index.html");
	}
	
	 @GetMapping(value = "/login")
	    public String login(ModelMap model) {
	 
	        return "login";
	 
	    }
*/

}
