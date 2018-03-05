package com.sf12.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Zahtev {

	@Id
	@GeneratedValue
	private Long zahtevId;

	@Column(nullable = false)
	private TipZahteva tipZahteva;

	@Column(nullable = false)
	private Long idProcesa;

	@Column(nullable = false)
	private String komentar;
	
	@Column(nullable = false)
	private String zaposleniEmail;

	private boolean hitno;
	
	private String izmena;
	
	@Column(columnDefinition = "tinyint(1) default 0")
	private boolean overeno;
	
	@Column(nullable=false)
	private String rokIzvrsavanja;
	
	@Column(columnDefinition = "tinyint(1) default 0")
	private boolean odobreno;
	
	
	
	public Zahtev() {
	}

	public Long getZahtevId() {
		return zahtevId;
	}

	public void setZahtevId(Long zahtevId) {
		this.zahtevId = zahtevId;
	}

	public TipZahteva getTipZahteva() {
		return tipZahteva;
	}

	public void setTipZahteva(TipZahteva tipZahteva) {
		this.tipZahteva = tipZahteva;
	}

	public Long getIdProcesa() {
		return idProcesa;
	}

	public void setIdProcesa(Long idProcesa) {
		this.idProcesa = idProcesa;
	}

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public String getZaposleniEmail() {
		return zaposleniEmail;
	}

	public void setZaposleniEmail(String zaposleniEmail) {
		this.zaposleniEmail = zaposleniEmail;
	}

	public boolean isHitno() {
		return hitno;
	}

	public void setHitno(boolean hitno) {
		this.hitno = hitno;
	}

	public String getRokIzvrsavanja() {
		return rokIzvrsavanja;
	}

	public void setRokIzvrsavanja(String rokIzvrsavanja) {
		this.rokIzvrsavanja = rokIzvrsavanja;
	}

	public boolean isOdobreno() {
		return odobreno;
	}

	public void setOdobreno(boolean odobreno) {
		this.odobreno = odobreno;
	}

	public String getIzmena() {
		return izmena;
	}

	public void setIzmena(String izmena) {
		this.izmena = izmena;
	}

	public boolean isOvereno() {
		return overeno;
	}

	public void setOvereno(boolean overeno) {
		this.overeno = overeno;
	}

	@Override
	public String toString() {
		return "Zahtev [zahtevId=" + zahtevId + ", tipZahteva=" + tipZahteva + ", idProcesa=" + idProcesa
				+ ", komentar=" + komentar + ", zaposleniEmail=" + zaposleniEmail + ", hitno=" + hitno + ", izmena="
				+ izmena + ", overeno=" + overeno + ", rokIzvrsavanja=" + rokIzvrsavanja + ", odobreno=" + odobreno + "]";
	}
	
	

}
