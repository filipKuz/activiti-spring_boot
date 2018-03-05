package com.sf12.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sf12.entity.EfikasnostIzvrsilaca;
import java.lang.String;
import java.util.List;

public interface EfikasnostIzvrsilacaJpaRepository extends JpaRepository<EfikasnostIzvrsilaca, Long> {
	List<EfikasnostIzvrsilaca> findByIzvrsilac(String izvrsilac);
}
