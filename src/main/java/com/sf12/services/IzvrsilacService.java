package com.sf12.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sf12.entity.EfikasnostIzvrsilaca;
import com.sf12.repositories.EfikasnostIzvrsilacaJpaRepository;

@Component
@Service
public class IzvrsilacService {

	@Autowired
	private	EfikasnostIzvrsilacaJpaRepository efikasnostIzvrsilacaJpa;
	
	
	public void evidentirajUspeh(String izv) {
		List<String> items = Arrays.asList(izv.split("\\s*,\\s*"));
		for(String i: items) {
			EfikasnostIzvrsilaca ei =  efikasnostIzvrsilacaJpa.findByIzvrsilac(i).get(0);
			ei.setBrUspenihZadataka(ei.getBrUspenihZadataka() + 1);
			efikasnostIzvrsilacaJpa.save(ei);
		}
	}
	
	public void evidentirajNeuspeh(String izv) {
		List<String> items = Arrays.asList(izv.split("\\s*,\\s*"));
		for(String i: items) {
			EfikasnostIzvrsilaca ei =  efikasnostIzvrsilacaJpa.findByIzvrsilac(i).get(0);
			ei.setBrNeuspenihZadataka(ei.getBrNeuspenihZadataka() + 1);
			efikasnostIzvrsilacaJpa.save(ei);
		}
	}
	
}
