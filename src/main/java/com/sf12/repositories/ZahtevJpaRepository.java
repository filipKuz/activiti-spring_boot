package com.sf12.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sf12.entity.Zahtev;

public interface ZahtevJpaRepository extends JpaRepository<Zahtev, Long> {
	
	Zahtev findByIdProcesa(Long id); 
}
