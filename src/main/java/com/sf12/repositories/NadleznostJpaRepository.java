package com.sf12.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sf12.entity.Nadleznost;

public interface NadleznostJpaRepository extends JpaRepository<Nadleznost, Long> {

	List<Nadleznost> findByZaposleni(String zaposleni);
	
	List<Nadleznost> findByNadredjeni(String nadredjeni);
}
