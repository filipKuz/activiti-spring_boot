package com.sf12.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EfikasnostIzvrsilaca {
	
	@Id
	@GeneratedValue
	private Long eiId;
	
	@Column(nullable=false)
	private String izvrsilac;
	
	@Column(nullable=false)
	private Long brUspenihZadataka;
	
	@Column(nullable=false)
	private Long brNeuspenihZadataka;

	public Long getEiId() {
		return eiId;
	}

	public void setEiId(Long eiId) {
		this.eiId = eiId;
	}

	public String getIzvrsilac() {
		return izvrsilac;
	}

	public void setIzvrsilac(String izvrsilac) {
		this.izvrsilac = izvrsilac;
	}

	public Long getBrUspenihZadataka() {
		return brUspenihZadataka;
	}

	public void setBrUspenihZadataka(Long brUspenihZadataka) {
		this.brUspenihZadataka = brUspenihZadataka;
	}

	public Long getBrNeuspenihZadataka() {
		return brNeuspenihZadataka;
	}

	public void setBrNeuspenihZadataka(Long brNeuspenihZadataka) {
		this.brNeuspenihZadataka = brNeuspenihZadataka;
	}
}
