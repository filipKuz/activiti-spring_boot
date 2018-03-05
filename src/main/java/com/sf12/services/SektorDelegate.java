package com.sf12.services;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SektorDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
	
		String tipZahteva  = execution.getVariable("tipZahteva").toString();
		if (tipZahteva.equals("POPRAVKA")) {
			execution.setVariable("sektor", "direktoriSektoraPopravke");
		}else if (tipZahteva.equals("ODOBRENJE_BORAVKA_NA_RADNOM_MESTU_POSLE_RADNOG_VREMENA")) {
			execution.setVariable("sektor", "direktoriSektoraOdobrenje");
		}else if (tipZahteva.equals("NABAVKA_KANCELARIJSKOG_MATERIJALA")) {
			execution.setVariable("sektor", "direktoriSektoraNabavke");
		}
	}

}
