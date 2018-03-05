package com.sf12.controllers;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sf12.entity.EfikasnostIzvrsilaca;
import com.sf12.entity.Nadleznost;
import com.sf12.entity.TipZahteva;
import com.sf12.repositories.EfikasnostIzvrsilacaJpaRepository;
import com.sf12.repositories.NadleznostJpaRepository;

@Controller
@RequestMapping(value="/app")
public class SharedController {

	@Autowired
	private NadleznostJpaRepository nadleznostJpa;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private EfikasnostIzvrsilacaJpaRepository eJpaRepository;
	
	@GetMapping(value="reqTypes")
	public ResponseEntity<?> getReqTypes() {
		List<TipZahteva> enumList = new ArrayList<>(EnumSet.allOf(TipZahteva.class));
		return new ResponseEntity<List<TipZahteva>>(enumList, HttpStatus.OK);
	}
	
	@GetMapping(value="ex-eff")
	public ResponseEntity<?> efikasnostIzvrsilaca() {
		
		List<EfikasnostIzvrsilaca> ei = eJpaRepository.findAll();
		return new ResponseEntity<List<EfikasnostIzvrsilaca>>(ei, HttpStatus.OK);
	}
	
	@GetMapping(value="ex-list")
	public ResponseEntity<?> listaRaspolozivihIzvrsilaca() {
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		
		List<Nadleznost> izvrs = nadleznostJpa.findByNadredjeni(user.getUsername());
		List<org.activiti.engine.identity.User> i = new ArrayList<>();
		for (Nadleznost nad: izvrs) {
			i.add(identityService.createUserQuery().userId(nad.getZaposleni()).singleResult());
		}
		
		return new ResponseEntity<>(i, HttpStatus.OK);
	}
}
