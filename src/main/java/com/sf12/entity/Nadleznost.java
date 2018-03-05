package com.sf12.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Nadleznost {

	@Id
	@GeneratedValue
	private Long nadleznostId;
	
	@Column(nullable=false)
	private String zaposleni;
	
	@Column(nullable=false)
	private String nadredjeni;

	public Long getNadleznostId() {
		return nadleznostId;
	}

	public void setNadleznostId(Long nadleznostId) {
		this.nadleznostId = nadleznostId;
	}

	public String getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(String zaposleni) {
		this.zaposleni = zaposleni;
	}

	public String getNadredjeni() {
		return nadredjeni;
	}

	public void setNadredjeni(String nadredjeni) {
		this.nadredjeni = nadredjeni;
	}
}
