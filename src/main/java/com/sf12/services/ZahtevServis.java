package com.sf12.services;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.context.Context;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sf12.entity.Nadleznost;
import com.sf12.entity.TipZahteva;
import com.sf12.entity.Zahtev;
import com.sf12.repositories.NadleznostJpaRepository;
import com.sf12.repositories.ZahtevJpaRepository;

@Component
@Service
public class ZahtevServis {

	@Autowired
	private ZahtevJpaRepository zahtevJpaRepository;

	@Autowired
	private NadleznostJpaRepository nadleznostJpa;

	@Autowired
	private IdentityService identityService;

	public void izmenaZahteva(String procesId, String obrazlozenje, String izmena) {
		Zahtev z = zahtevJpaRepository.findByIdProcesa(Long.valueOf(procesId));
		z.setOdobreno(true);
		z.setKomentar(izmena);
		z.setIzmena(obrazlozenje);
		zahtevJpaRepository.save(z);
	}

	public void odobriZahtev(String procesId) {
		Zahtev z = zahtevJpaRepository.findByIdProcesa(Long.valueOf(procesId));
		z.setOdobreno(true);
		zahtevJpaRepository.save(z);
	}

	public String sefMail(String id) {
		String email = identityService.createUserQuery().userId(id).singleResult().getEmail();
		return email;
	}

	public String nadleznost() {
		User user;
		try {
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		List<String> nad = new ArrayList<>();
		for (Nadleznost n : nadleznostJpa.findByZaposleni(user.getUsername())) {
			nad.add(n.getNadredjeni());
		}
		return StringUtils.join(nad, ",");
	}

	public void overavanjeZahteva() {
		Long id = Long.valueOf(Context.getExecutionContext().getProcessInstance().getId());
		Zahtev z = zahtevJpaRepository.findByIdProcesa(id);
		z.setOvereno(true);
		zahtevJpaRepository.save(z);
	}

	public Zahtev evidentiranjeZahteva(TipZahteva tipZahteva, String komentar, boolean hitno, String rokIzvrsavanja) {

		User user;
		try {
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		Zahtev z = new Zahtev();
		long idProc = Long.valueOf(Context.getExecutionContext().getProcessInstance().getId());
		z.setIdProcesa(idProc);
		z.setKomentar(komentar);
		z.setTipZahteva(tipZahteva);
		z.setHitno(hitno);
		z.setRokIzvrsavanja(rokIzvrsavanja);
		String email = identityService.createUserQuery().userId(user.getUsername()).singleResult().getEmail();
		z.setZaposleniEmail(email);

		zahtevJpaRepository.save(z);
		return z;

	}

	public Zahtev noviRok(String rokIzvrsavanja) {

		long idProc = Long.valueOf(Context.getExecutionContext().getProcessInstance().getId());
		Zahtev z = zahtevJpaRepository.findByIdProcesa(idProc);
		z.setRokIzvrsavanja(rokIzvrsavanja);
		zahtevJpaRepository.save(z);
		return z;
	}

	public Zahtev findByIdProcesa(Long id) {
		return zahtevJpaRepository.findByIdProcesa(id);
	}

}
