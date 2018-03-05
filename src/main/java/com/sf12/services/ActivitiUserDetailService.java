package com.sf12.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ActivitiUserDetailService implements UserDetailsService {
	
	@Autowired
	private IdentityService identityService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		
		
		List<User> users = identityService.createUserQuery().userId(username).list();
		if (users.size() == 0) {
			throw new UsernameNotFoundException("Invalid username");
		}
		User user = users.get(0);
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), true, true, true, true, grantedAuthorities);
	}
	
}
