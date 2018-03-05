package com.sf12;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sf12.entity.EfikasnostIzvrsilaca;
import com.sf12.entity.Nadleznost;
import com.sf12.repositories.EfikasnostIzvrsilacaJpaRepository;
import com.sf12.repositories.NadleznostJpaRepository;

@SpringBootApplication
public class UppActivitiApplication {

	@Autowired
	private NadleznostJpaRepository nadleznostJpaRepository;
	
	@Autowired
	private EfikasnostIzvrsilacaJpaRepository eJpa;
	
	public static void main(String[] args) {
		SpringApplication.run(UppActivitiApplication.class, args);
	}

	@Bean
	InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {

		return new InitializingBean() {
			public void afterPropertiesSet() throws Exception {

				long groupsNum = identityService.createGroupQuery().count();
				if (groupsNum == 0) {
					Group zaposleni = identityService.newGroup("zaposleni");
					zaposleni.setName("Zaposleni");
					zaposleni.setType("security-role");
					identityService.saveGroup(zaposleni);

					Group sefovi = identityService.newGroup("sefovi");
					sefovi.setName("Sefovi");
					sefovi.setType("security-role");
					identityService.saveGroup(sefovi);
					
					Group dszp = identityService.newGroup("direktoriSluzbeZajednickihPoslova");
					dszp.setName("DirektoriSluzbeZajednickihPoslova");
					dszp.setType("security-role");
					identityService.saveGroup(dszp);
					
					Group dsp = identityService.newGroup("direktoriSektoraPopravke");
					dsp.setName("DirektoriSektoraPopravke");
					dsp.setType("security-role");
					identityService.saveGroup(dsp);
					
					Group dsodob = identityService.newGroup("direktoriSektoraOdobrenje");
					dsodob.setName("DirektoriSektoraOdobrenje");
					dsodob.setType("security-role");
					identityService.saveGroup(dsodob);
					
					Group dsodnabavka = identityService.newGroup("direktoriSektoraNabavke");
					dsodnabavka.setName("DirektoriSektoraNabavke");
					dsodnabavka.setType("security-role");
					identityService.saveGroup(dsodnabavka);
					
					Group izvrsioci = identityService.newGroup("izvrsioci");
					izvrsioci.setName("Izvrsioci");
					izvrsioci.setType("security-role");
					identityService.saveGroup(izvrsioci);

				}
				long usersNum = identityService.createUserQuery().count();
				if (usersNum == 0) {
					User direktorSektora1 = identityService.newUser("fk");
					direktorSektora1.setPassword("fk");
					direktorSektora1.setFirstName("Filip");
					direktorSektora1.setEmail("uppactivitiapplication@gmail.com");
					direktorSektora1.setLastName("Kuzmanovic");
					identityService.saveUser(direktorSektora1);
					identityService.createMembership("fk", "direktoriSektoraPopravke");
					
					User direktorSektora2 = identityService.newUser("cr");
					direktorSektora2.setPassword("cr");
					direktorSektora2.setFirstName("Cristiano");
					direktorSektora2.setEmail("uppactivitiapplication@gmail.com");
					direktorSektora2.setLastName("Ronaldo");
					identityService.saveUser(direktorSektora2);
					identityService.createMembership("cr", "direktoriSektoraOdobrenje");
					
					User direktorSektora3 = identityService.newUser("lm");
					direktorSektora3.setPassword("lm");
					direktorSektora3.setFirstName("Lionel");
					direktorSektora3.setEmail("uppactivitiapplication@gmail.com");
					direktorSektora3.setLastName("Messi");
					identityService.saveUser(direktorSektora3);
					identityService.createMembership("lm", "direktoriSektoraNabavke");
					
					User zaposleni1 = identityService.newUser("sc");
					zaposleni1.setPassword("sc");
					zaposleni1.setFirstName("Stephen");
					zaposleni1.setEmail("uppactivitiapplication@gmail.com");
					zaposleni1.setLastName("Curry");
					identityService.saveUser(zaposleni1);
					identityService.createMembership("sc", "zaposleni");
					
					User zaposleni2 = identityService.newUser("lj");
					zaposleni2.setPassword("lj");
					zaposleni2.setFirstName("LeBron");
					zaposleni2.setEmail("uppactivitiapplication@gmail.com");
					zaposleni2.setLastName("James");
					identityService.saveUser(zaposleni2);
					identityService.createMembership("lj", "zaposleni");
					
					User zaposleni3 = identityService.newUser("vo");
					zaposleni3.setPassword("vo");
					zaposleni3.setFirstName("Victor");
					zaposleni3.setEmail("uppactivitiapplication@gmail.com");
					zaposleni3.setLastName("Oladipo");
					identityService.saveUser(zaposleni3);
					identityService.createMembership("vo", "zaposleni");
					
					User sef = identityService.newUser("ss");
					sef.setPassword("ss");
					sef.setFirstName("Sef");
					sef.setEmail("uppactivitiapplication@gmail.com");
					sef.setLastName("Sefic");
					identityService.saveUser(sef);
					identityService.createMembership("ss", "sefovi");
					
					User sef2 = identityService.newUser("ww");
					sef2.setPassword("ww");
					sef2.setFirstName("Weeee");
					sef2.setEmail("uppactivitiapplication@gmail.com");
					sef2.setLastName("Weeee");
					identityService.saveUser(sef2);
					identityService.createMembership("ww", "sefovi");
					
					User dslzp1 = identityService.newUser("bl");
					dslzp1.setPassword("bl");
					dslzp1.setFirstName("Branko");
					dslzp1.setEmail("uppactivitiapplication@gmail.com");
					dslzp1.setLastName("Lazic");
					identityService.saveUser(dslzp1);
					identityService.createMembership("bl", "direktoriSluzbeZajednickihPoslova");
					
					User dslzp2 = identityService.newUser("mb");
					dslzp2.setPassword("mb");
					dslzp2.setFirstName("Milko");
					dslzp2.setEmail("uppactivitiapplication@gmail.com");
					dslzp2.setLastName("Bjelica");
					identityService.saveUser(dslzp2);
					identityService.createMembership("mb", "direktoriSluzbeZajednickihPoslova");
					
					User izv1 = identityService.newUser("ndj");
					izv1.setPassword("ndj");
					izv1.setFirstName("Novak");
					izv1.setEmail("uppactivitiapplication@gmail.com");
					izv1.setLastName("Djokovic");
					identityService.saveUser(izv1);
					identityService.createMembership("ndj", "izvrsioci");
					
					User izv2 = identityService.newUser("rn");
					izv2.setPassword("rn");
					izv2.setFirstName("Rafael");
					izv2.setEmail("uppactivitiapplication@gmail.com");
					izv2.setLastName("Nadal");
					identityService.saveUser(izv2);
					identityService.createMembership("rn", "izvrsioci");
					
					User izv3 = identityService.newUser("jt");
					izv3.setPassword("jt");
					izv3.setFirstName("Janko");
					izv3.setEmail("uppactivitiapplication@gmail.com");
					izv3.setLastName("Tipsarevic");
					identityService.saveUser(izv3);
					identityService.createMembership("jt", "izvrsioci");
					
					Nadleznost nad1 = new Nadleznost();
					nad1.setNadredjeni(sef.getId());
					nad1.setZaposleni(zaposleni1.getId());
					nadleznostJpaRepository.save(nad1);
					
					Nadleznost nad45 = new Nadleznost();
					nad45.setNadredjeni(sef2.getId());
					nad45.setZaposleni(zaposleni1.getId());
					nadleznostJpaRepository.save(nad45);
					
					Nadleznost nad2 = new Nadleznost();
					nad2.setNadredjeni(sef.getId());
					nad2.setZaposleni(zaposleni2.getId());
					nadleznostJpaRepository.save(nad2);
					
					Nadleznost nad3 = new Nadleznost();
					nad3.setNadredjeni(sef2.getId());
					nad3.setZaposleni(zaposleni3.getId());
					nadleznostJpaRepository.save(nad3);
					
					Nadleznost nad4 = new Nadleznost();
					nad4.setNadredjeni(direktorSektora1.getId());
					nad4.setZaposleni(izv1.getId());
					nadleznostJpaRepository.save(nad4);
					
					Nadleznost nad5 = new Nadleznost();
					nad5.setNadredjeni(direktorSektora2.getId());
					nad5.setZaposleni(izv2.getId());
					nadleznostJpaRepository.save(nad5);
					
					Nadleznost nad6 = new Nadleznost();
					nad6.setNadredjeni(direktorSektora3.getId());
					nad6.setZaposleni(izv3.getId());
					nadleznostJpaRepository.save(nad6);
					
					EfikasnostIzvrsilaca ei = new EfikasnostIzvrsilaca();
					ei.setBrNeuspenihZadataka((long) 0);
					ei.setBrUspenihZadataka((long) 0);
					ei.setIzvrsilac(izv1.getId());
					eJpa.save(ei);
					
					EfikasnostIzvrsilaca ei2 = new EfikasnostIzvrsilaca();
					ei2.setBrNeuspenihZadataka((long) 0);
					ei2.setBrUspenihZadataka((long) 0);
					ei2.setIzvrsilac(izv2.getId());
					eJpa.save(ei2);
					
					EfikasnostIzvrsilaca ei3 = new EfikasnostIzvrsilaca();
					ei3.setBrNeuspenihZadataka((long) 0);
					ei3.setBrUspenihZadataka((long) 0);
					ei3.setIzvrsilac(izv3.getId());
					eJpa.save(ei3);
				}
			}
		};
	}
}
